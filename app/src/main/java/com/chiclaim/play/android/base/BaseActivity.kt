package com.chiclaim.play.android.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

/**
 * Activity 基类。为了避免被直接使用，需要声明为 abstract
 *
 * @author chiclaim@google.com
 */
abstract class BaseActivity : AppCompatActivity(), ILayout, ILoad {

    private var isLoaded = false

    val activityProvider: ScopeViewModel.ActivityViewModelProvider by lazy {
        ScopeViewModel.ActivityViewModelProvider(this)
    }

    val applicationProvider: ScopeViewModel.ApplicationViewModelProvider by lazy {
        ScopeViewModel.ApplicationViewModelProvider(application)
    }

    /**
     * invoke after init content view
     */
    open fun init(savedInstanceState: Bundle?) {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initContentView()
        init(savedInstanceState)
    }

    open fun initContentView() {
        setContentView(getLayoutId())
    }


    override fun onResume() {
        super.onResume()
        if (!isLoaded) {
            isLoaded = true
            lazyLoad()
        }
    }

    override fun lazyLoad() {
    }

    override fun loadData() {
    }

}