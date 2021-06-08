package com.chiclaim.play.android.base

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * 列表类型界面的公共行为封装，如刷新、加载更多等
 *
 * @author by chiclaim@google.com
 */
abstract class BaseListFragment<T : ViewDataBinding, VM : BaseViewModel> :
    BaseViewModelFragment<T, VM>() {


    fun initWidgets(recyclerView: RecyclerView) {
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }
        })
    }


}