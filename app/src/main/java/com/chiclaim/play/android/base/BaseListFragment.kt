package com.chiclaim.play.android.base

import androidx.databinding.ViewDataBinding

/**
 * 列表类型界面的公共行为封装，如刷新、加载更多等
 *
 * @author by chiclaim@google.com
 */
abstract class BaseListFragment<T : ViewDataBinding, VM : BaseViewModel> :
    BaseViewModelFragment<T, VM>() {
}