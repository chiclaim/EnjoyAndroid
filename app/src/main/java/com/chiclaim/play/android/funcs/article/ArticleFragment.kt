package com.chiclaim.play.android.funcs.article

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.chiclaim.play.android.R
import com.chiclaim.play.android.base.BaseViewModelFragment
import com.chiclaim.play.android.databinding.FragmentHomeBinding
import com.chiclaim.play.android.exception.codeMessage
import com.chiclaim.play.android.utils.ToastUtil

class ArticleFragment : BaseViewModelFragment<FragmentHomeBinding, ArticleViewModel>() {

    private val fragmentList: MutableList<Fragment> by lazy {
        arrayListOf()
    }

    private val tabLabelList: MutableList<String> by lazy {
        arrayListOf()
    }

    override fun getLayoutId() = R.layout.fragment_home

    override fun viewModelClass() = ArticleViewModel::class.java


    override fun init(view: View, savedInstanceState: Bundle?) {
        super.init(view, savedInstanceState)
        viewModel.categoryData.observe(viewLifecycleOwner) {
            it.forEach { vo ->
                fragmentList.add(ArticleListFragment.create(vo.id, vo.name))
                tabLabelList.add(vo.name ?: "unknown")
            }
            val articleAdapter = ArticleTabAdapter(
                childFragmentManager,
                FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
            )
            requireDataBinding().viewPager.adapter = articleAdapter
            requireDataBinding().tabLayout.setupWithViewPager(requireDataBinding().viewPager)
        }

        viewModel.categoryDataError.observe(viewLifecycleOwner) {
            ToastUtil.showShort(requireContext(), it.codeMessage())
        }

    }

    inner class ArticleTabAdapter(fragmentManager: FragmentManager, behavior: Int) :
        FragmentPagerAdapter(fragmentManager, behavior) {

        override fun getCount(): Int {
            return fragmentList.size
        }

        override fun getItem(position: Int): Fragment {
            return fragmentList[position]
        }

        override fun getPageTitle(position: Int): CharSequence {
            return tabLabelList[position]
        }

    }

    override fun requestData() {
        super.requestData()
        viewModel.fetchArticleCategories()

    }


}