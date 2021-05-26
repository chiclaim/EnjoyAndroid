package com.chiclaim.play.android.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.chiclaim.play.android.databinding.FragmentHomeBinding

/**
 * Fragment 基类。为了避免被直接使用，需要声明为 abstract
 *
 * @author by chiclaim@google.com
 */
abstract class BaseFragment<T : ViewDataBinding> : Fragment() {

    val fragmentProvider: ScopeViewModel.FragmentViewModelProvider by lazy {
        ScopeViewModel.FragmentViewModelProvider(this)
    }

    val activityProvider: ScopeViewModel.ActivityViewModelProvider by lazy {
        ScopeViewModel.ActivityViewModelProvider(requireActivity())
    }

    val applicationProvider: ScopeViewModel.ApplicationViewModelProvider by lazy {
        ScopeViewModel.ApplicationViewModelProvider(requireActivity().application)
    }

    private var viewDataBinding: T? = null

    @LayoutRes
    abstract fun getLayoutId(): Int

    /**
     * 在 [onCreateView] 方法中，构造 viewDataBinding 之后被调用
     */
    open fun init() {

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = DataBindingUtil.inflate(
            inflater,
            getLayoutId(), container, false
        )
        init()
        return viewDataBinding?.root
    }


    fun requireDataBinding(): T {
        if (viewDataBinding == null) error("must call after onCreateView()")
        return viewDataBinding as T
    }


    override fun onDestroyView() {
        super.onDestroyView()
        viewDataBinding?.unbind()
    }


}