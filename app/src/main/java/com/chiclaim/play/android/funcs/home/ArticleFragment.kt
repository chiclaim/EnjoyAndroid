package com.chiclaim.play.android.funcs.home

import android.util.Log
import com.chiclaim.play.android.R
import com.chiclaim.play.android.base.BaseFragment
import com.chiclaim.play.android.databinding.FragmentHomeBinding

class ArticleFragment : BaseFragment() {

    private lateinit var homeViewModel: ArticleViewModel

    private val viewDataBinding: FragmentHomeBinding by lazy {
        getDataBinding()
    }

    override fun getLayoutId() = R.layout.fragment_home


    override fun init() {
        super.init()

        homeViewModel = fragmentProvider.viewModel(ArticleViewModel::class.java)

        homeViewModel.categoryData.observe(viewLifecycleOwner) {
            it.forEach { vo ->
                Log.e("ArticleFragment", "--------------${vo.name} ${viewDataBinding.tabLayout}")
            }
        }

        homeViewModel.categoryDataError.observe(viewLifecycleOwner) {
            it.printStackTrace()
        }

        homeViewModel.fetchArticleCategories()

    }


}