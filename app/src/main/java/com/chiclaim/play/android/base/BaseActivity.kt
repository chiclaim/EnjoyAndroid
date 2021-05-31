package com.chiclaim.play.android.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

/**
 * Activity 基类。为了避免被直接使用，需要声明为 abstract
 *
 * @author chiclaim@google.com
 */
abstract class BaseActivity : AppCompatActivity(), ILayout {

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

}