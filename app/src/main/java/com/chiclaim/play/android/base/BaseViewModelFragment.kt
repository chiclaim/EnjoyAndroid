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
            loadData()
        }
    }

    private var resumed = false

    override fun onResume() {
        super.onResume()
        resumed = true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // ViewPager 默认会初始化当前展示的和其相邻的 Fragment，发生重建的话，会导致相邻的 Fragment bindForFragment 调用多次
        // reuseDataFlagAndReset 会认为可以重用数据，但实际上该 Fragment 是第一次展示
        // 解决：如果 onResume 没有被回调过，在 onDestroyView 中重置 ViewModel 对应的版本
        if (!resumed) viewModel.resetBindVersion()
    }

}