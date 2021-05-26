package com.chiclaim.play.android.funcs.article

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chiclaim.play.android.R
import com.chiclaim.play.android.base.BaseListFragment
import com.chiclaim.play.android.bean.ro.ArticleListRO
import com.chiclaim.play.android.databinding.FragmentArticleListBinding
import com.chiclaim.play.android.exception.codeMessage
import com.chiclaim.play.android.funcs.article.adapter.ArticleAdapter
import com.chiclaim.play.android.utils.ToastUtil


/**
 * 文章列表
 * @author by chiclaim@google.com
 */
class ArticleListFragment : BaseListFragment<FragmentArticleListBinding>() {

    companion object {
        fun create(categoryId: Int): ArticleListFragment {
            return ArticleListFragment().apply {
                val bundle = Bundle()
                bundle.putInt("categoryId", categoryId)
                this.arguments = bundle
            }
        }
    }

    private lateinit var articleListViewModel: ArticleListViewModel

    private var categoryId: Int = -1


    private val adapter: ArticleAdapter by lazy {
        ArticleAdapter()
    }

    override fun init() {

        arguments?.let {
            categoryId = it.getInt("categoryId")
        }
        Log.e("ArticleListFragment", "$categoryId-------------------init ${hashCode()}")

        articleListViewModel = fragmentProvider.viewModel(ArticleListViewModel::class.java)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(requireContext())
        layoutManager.orientation = RecyclerView.VERTICAL
        requireDataBinding().recycleView.layoutManager = layoutManager
        requireDataBinding().recycleView.adapter = adapter
        adapter.submitList(emptyList())

        articleListViewModel.fetchArticleList(ArticleListRO(0, categoryId))


        articleListViewModel.articleListLiveData.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        articleListViewModel.articleListFailedLiveData.observe(viewLifecycleOwner) {
            ToastUtil.showShort(requireContext(), it.codeMessage())
        }
    }


    override fun getLayoutId() = R.layout.fragment_article_list


}