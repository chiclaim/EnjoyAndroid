package com.chiclaim.play.android.base

import androidx.fragment.app.Fragment

/**
 * Fragment 基类。为了避免被直接使用，需要声明为 abstract
 *
 * @author by chiclaim@google.com
 */
abstract class BaseFragment : Fragment() {

    val fragmentProvider: ScopeViewModel.FragmentViewModelProvider by lazy {
        ScopeViewModel.FragmentViewModelProvider(this)
    }

    val activityProvider: ScopeViewModel.ActivityViewModelProvider by lazy {
        ScopeViewModel.ActivityViewModelProvider(requireActivity())
    }

    val applicationProvider: ScopeViewModel.ApplicationViewModelProvider by lazy {
        ScopeViewModel.ApplicationViewModelProvider(requireActivity().application)
    }


}