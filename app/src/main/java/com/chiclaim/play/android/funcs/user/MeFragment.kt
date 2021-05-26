package com.chiclaim.play.android.funcs.user

import com.chiclaim.play.android.R
import com.chiclaim.play.android.base.BaseFragment
import com.chiclaim.play.android.databinding.FragmentTabMeBinding

/**
 *
 * @author by chiclaim@google.com
 */
class MeFragment : BaseFragment<FragmentTabMeBinding>() {


    private lateinit var homeViewModel: MeViewModel

    override fun getLayoutId() = R.layout.fragment_tab_me

    override fun init() {
        super.init()
        homeViewModel = fragmentProvider.viewModel(MeViewModel::class.java)

        homeViewModel.text.observe(viewLifecycleOwner)  {
            requireDataBinding().textMe.text = it
        }
    }

}