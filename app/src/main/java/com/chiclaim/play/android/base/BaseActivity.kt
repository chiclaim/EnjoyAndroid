package com.chiclaim.play.android.base

import androidx.appcompat.app.AppCompatActivity

/**
 * Activity 基类。为了避免被直接使用，需要声明为 abstract
 *
 * @author chiclaim@google.com
 */
abstract class BaseActivity : AppCompatActivity() {

    val activityProvider: ScopeViewModel.ActivityViewModelProvider by lazy {
        ScopeViewModel.ActivityViewModelProvider(this)
    }

    val applicationProvider: ScopeViewModel.ApplicationViewModelProvider by lazy {
        ScopeViewModel.ApplicationViewModelProvider(application)
    }

}