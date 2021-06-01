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
class ArticleListFragment : BaseListFragment<FragmentArticleListBinding, ArticleListViewModel>() {

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

    private var categoryId: Int = -1
    private var categoryName: String? = null


    private val adapter: ArticleAdapter by lazy {
        ArticleAdapter()
    }

    override fun getLayoutId() = R.layout.fragment_article_list

    override fun viewModelClass(): Class<ArticleListViewModel> = ArticleListViewModel::class.java


    override fun init(view: View, savedInstanceState: Bundle?) {
        super.init(view, savedInstanceState)
        arguments?.let {
            categoryId = it.getInt("categoryId")
            categoryName = it.getString("categoryName")
        }
        Log.e("ArticleListFragment", "init $categoryName---${hashCode()}")


        requireDataBinding().recycleView.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(requireContext())
        layoutManager.orientation = RecyclerView.VERTICAL
        requireDataBinding().recycleView.layoutManager = layoutManager
        requireDataBinding().recycleView.adapter = adapter
        adapter.submitList(emptyList())

        viewModel.articleListLiveData.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        viewModel.articleListFailedLiveData.observe(viewLifecycleOwner) {
            ToastUtil.showShort(requireContext(), it.codeMessage())
        }

    }

    override fun onResume() {
        super.onResume()
        Log.e("ArticleListFragment", "onResume $categoryName---${hashCode()}")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.e("ArticleListFragment", "onDestroyView $categoryName---${hashCode()}")
    }

    private fun getContentData(): List<ArticleVO>? {
        return viewModel.articleListLiveData.value
    }

    override fun requestData() {
        super.requestData()
        viewModel.fetchArticleList(ArticleListRO(0, categoryId))
    }

}