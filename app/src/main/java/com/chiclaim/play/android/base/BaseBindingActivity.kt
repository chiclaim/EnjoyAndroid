package com.chiclaim.play.android.base

import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

/**
 *
 * @author by chiclaim@google.com
 */
abstract class BaseBindingActivity<T : ViewDataBinding> : BaseActivity(),
    IDataBinding<T> {


    override var viewDataBinding: T? = null


    override fun initContentView() {
        viewDataBinding = DataBindingUtil.setContentView<T>(this, getLayoutId())
            ?: error("layout's root must be <layout>")
        viewDataBinding?.lifecycleOwner = this
    }

    fun bindVariables(map: Map<Int, Any>) {
        map.forEach {
            requireDataBinding().setVariable(it.key, it.value)
        }
    }

}