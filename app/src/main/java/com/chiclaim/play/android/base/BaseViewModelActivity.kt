package com.chiclaim.play.android.base

import android.os.Bundle
import androidx.databinding.ViewDataBinding

/**
 *
 * @author by chiclaim@google.com
 */
abstract class BaseViewModelActivity<T : ViewDataBinding, VM : BaseViewModel> : BaseBindingActivity<T>() {


    lateinit var viewModel: VM
        private set

    abstract fun viewModelClass(): Class<VM>

    override fun init(savedInstanceState: Bundle?) {
        super.init(savedInstanceState)
        viewModel = activityProvider.viewModel(viewModelClass())
        viewModel.bindForActivity(this)
    }

    override fun lazyLoad() {
        // 当 [BaseViewModel.reuseDataFlagAndReset] 为 false 才会被调用，避免数据的重复请求
        if (!viewModel.reuseDataFlagAndReset()) {
            loadData()
        }
    }

}