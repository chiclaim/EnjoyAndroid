package com.chiclaim.play.android.funcs.article

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chiclaim.play.android.R
import com.chiclaim.play.android.base.BaseListFragment
import com.chiclaim.play.android.bean.ro.ArticleListRO
import com.chiclaim.play.android.bean.vo.ArticleVO
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
        fun create(categoryId: Int, categoryName: String?): ArticleListFragment {
            return ArticleListFragment().apply {
                val bundle = Bundle()
                bundle.putInt("categoryId", categoryId)
                bundle.putString("categoryName", categoryName)
                this.arguments = bundle
            }
        }
    }

    private lateinit var articleListViewModel: ArticleListViewModel

    private var categoryId: Int = -1
    private var categoryName: String? = null


    private val adapter: ArticleAdapter by lazy {
        ArticleAdapter()
    }

    override fun init() {

        arguments?.let {
            categoryId = it.getInt("categoryId")
            categoryName = it.getString("categoryName")
        }
        Log.e("ArticleListFragment", "init $categoryName---${hashCode()}")


        articleListViewModel = fragmentProvider.viewModel(ArticleListViewModel::class.java)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireDataBinding().recycleView.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(requireContext())
        layoutManager.orientation = RecyclerView.VERTICAL
        requireDataBinding().recycleView.layoutManager = layoutManager
        requireDataBinding().recycleView.adapter = adapter
        adapter.submitList(emptyList())

        articleListViewModel.articleListLiveData.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        articleListViewModel.articleListFailedLiveData.observe(viewLifecycleOwner) {
            ToastUtil.showShort(requireContext(), it.codeMessage())
        }
    }


    override fun getLayoutId() = R.layout.fragment_article_list

    override fun onResume() {
        super.onResume()
        Log.e("ArticleListFragment", "onResume $categoryName---${hashCode()}")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.e("ArticleListFragment", "onDestroyView $categoryName---${hashCode()}")
    }

    private fun getContentData(): List<ArticleVO>? {
        return articleListViewModel.articleListLiveData.value
    }

    override fun lazyLoad() {
        val contentData = getContentData()
        if (getDestroyViewStateAndReset() && contentData?.isNotEmpty() == true) {
            adapter.submitList(contentData)
        } else {
            articleListViewModel.fetchArticleList(ArticleListRO(0, categoryId))
        }
    }


}