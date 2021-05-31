package com.chiclaim.play.android.funcs.user

import android.os.Bundle
import android.view.View
import com.chiclaim.play.android.R
import com.chiclaim.play.android.base.BaseBindingFragment
import com.chiclaim.play.android.databinding.FragmentTabMeBinding

/**
 *
 * @author by chiclaim@google.com
 */
class MeFragment : BaseBindingFragment<FragmentTabMeBinding>() {


    private lateinit var homeViewModel: MeViewModel

    override fun getLayoutId() = R.layout.fragment_tab_me

    override fun init(view: View, savedInstanceState: Bundle?) {
        super.init(view, savedInstanceState)
        homeViewModel = fragmentProvider.viewModel(MeViewModel::class.java)

        homeViewModel.text.observe(viewLifecycleOwner) {
            requireDataBinding().textMe.text = it
        }
    }

    override fun lazyLoad() {
    }

}