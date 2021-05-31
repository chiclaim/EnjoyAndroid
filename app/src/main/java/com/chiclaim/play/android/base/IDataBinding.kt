package com.chiclaim.play.android.base

import androidx.databinding.ViewDataBinding

/**
 *
 * @author by chiclaim@google.com
 */
interface IDataBinding<T : ViewDataBinding> {
    var viewDataBinding: T?

    fun requireDataBinding(): T {
        if (viewDataBinding == null) error("must call after view created()")
        return viewDataBinding as T
    }

}