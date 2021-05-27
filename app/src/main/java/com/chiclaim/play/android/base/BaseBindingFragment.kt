package com.chiclaim.play.android.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

/**
 * 封装 DataBinding Fragment
 * @author by chiclaim@google.com
 */
abstract class BaseBindingFragment<T : ViewDataBinding> : BaseFragment<T>() {

    private var viewDataBinding: T? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = DataBindingUtil.inflate(
            inflater,
            getLayoutId(), container, false
        )
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