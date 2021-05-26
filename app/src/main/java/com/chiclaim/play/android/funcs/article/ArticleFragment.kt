package com.chiclaim.play.android.funcs.article

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.chiclaim.play.android.R
import com.chiclaim.play.android.base.BaseFragment
import com.chiclaim.play.android.databinding.FragmentHomeBinding
import com.chiclaim.play.android.exception.codeMessage
import com.chiclaim.play.android.utils.ToastUtil

class ArticleFragment : BaseFragment<FragmentHomeBinding>() {

    private lateinit var homeViewModel: ArticleViewModel

    private val fragmentList: MutableList<Fragment> by lazy {
        arrayListOf()
    }

    private val tabLabelList: MutableList<String> by lazy {
        arrayListOf()
    }

    override fun getLayoutId() = R.layout.fragment_home


    override fun init() {
        super.init()


        homeViewModel = fragmentProvider.viewModel(ArticleViewModel::class.java)

        homeViewModel.categoryData.observe(viewLifecycleOwner) {
            it.forEach { vo ->
                fragmentList.add(ArticleListFragment.create(vo.id))
                tabLabelList.add(vo.name ?: "unknown")
            }
            val articleAdapter = ArticleTabAdapter(
                childFragmentManager,
                FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
            )
            requireDataBinding().viewPager.adapter = articleAdapter
            requireDataBinding().tabLayout.setupWithViewPager(requireDataBinding().viewPager)
        }

        homeViewModel.categoryDataError.observe(viewLifecycleOwner) {
            ToastUtil.showShort(requireContext(), it.codeMessage())
        }

        homeViewModel.fetchArticleCategories()

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


}