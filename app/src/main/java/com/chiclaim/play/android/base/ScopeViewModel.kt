package com.chiclaim.play.android.base

import android.app.Application
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner

/**
 * 封装 3 个作用域的 ViewModel
 *
 * 由于 [BaseActivity] 和 [BaseActivity] 两个基类都需要，所以通过组合的方式，避免大量的重复代码
 *
 *
 * @author by chiclaim@google.com
 */
class ScopeViewModel {

    class FragmentViewModelProvider(fragment: Fragment) {
        private val fragmentProvider: ViewModelProvider by lazy {
            ViewModelProvider(fragment)
        }

        fun <T : ViewModel> viewModel(viewModelClass: Class<T>) =
            fragmentProvider.get(viewModelClass)
    }

    class ActivityViewModelProvider(activity: FragmentActivity) {
        private val activityProvider: ViewModelProvider by lazy {
            ViewModelProvider(activity)
        }

        fun <T : ViewModel> viewModel(viewModelClass: Class<T>) =
            activityProvider.get(viewModelClass)
    }

    class ApplicationViewModelProvider(application: Application) {

        private val applicationProvider: ViewModelProvider by lazy {
            val factory = ViewModelProvider.AndroidViewModelFactory.getInstance(application)
            if (application is ViewModelStoreOwner) {
                return@lazy ViewModelProvider(application, factory)
            }
            error("The $application must implement ViewModelStoreOwner")
        }

        fun <T : ViewModel> viewModel(viewModelClass: Class<T>) =
            applicationProvider.get(viewModelClass)
    }

}