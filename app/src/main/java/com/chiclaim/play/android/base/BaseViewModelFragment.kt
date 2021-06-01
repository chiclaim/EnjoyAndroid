package com.chiclaim.play.android.base

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding

/**
 * 封装 ViewModel 在 Fragment 中的共性操作：
 * - ViewModel 的创建 [ScopeViewModel.FragmentViewModelProvider.viewModel] 和绑定 [BaseViewModel.bindForFragment]
 * - 防止数据重复加载的判断
 *
 * @author by chiclaim@google.com
 */
abstract class BaseViewModelFragment<T : ViewDataBinding, VM : BaseViewModel> :
    BaseBindingFragment<T>() {

    lateinit var viewModel: VM
        private set

    abstract fun viewModelClass(): Class<VM>

    override fun init(view: View, savedInstanceState: Bundle?) {
        super.init(view, savedInstanceState)
        viewModel = fragmentProvider.viewModel(viewModelClass())
        viewModel.bindForFragment(this, view)
    }


    override fun lazyLoad() {
        if (!viewModel.reuseDataFlagAndReset()) {
            requestData()
        }
    }

    /**
     * 当 [BaseViewModel.reuseDataFlagAndReset] 为 false 才会被调用，避免数据的重复请求
     *
     * @see lazyLoad
     */
    open fun requestData() {

    }


}