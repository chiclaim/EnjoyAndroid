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
abstract class BaseBindingFragment<T : ViewDataBinding> : BaseFragment<T>(), IDataBinding<T> {

    override var viewDataBinding: T? = null

    override fun initContentView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = DataBindingUtil.inflate(
            inflater,
            getLayoutId(), container, false
        )
        viewDataBinding?.lifecycleOwner = this
        return viewDataBinding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewDataBinding?.unbind()
    }


}