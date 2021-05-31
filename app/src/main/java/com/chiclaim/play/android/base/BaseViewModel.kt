package com.chiclaim.play.android.base

import android.app.Activity
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 *
 * @author by chiclaim@google.com
 */
abstract class BaseViewModel : ViewModel() {

    companion object {
        fun <T> postValue(liveData: LiveData<T>, data: T?) {
            if (liveData !is MutableLiveData)
                throw IllegalArgumentException("$liveData must be MutableLiveData")
            liveData.postValue(data)
        }

        fun <T> setValue(liveData: LiveData<T>, data: T?) {
            if (liveData !is MutableLiveData)
                throw IllegalArgumentException("$liveData must be MutableLiveData")
            liveData.value = (data)
        }
    }

    private var pageStamp: Int = -1
    private var viewStamp: Int = -1

    private var reuseData = false

    /**
     * 绑定 Fragment 和 ViewModel 的版本关系（Fragment 中使用 ViewModel）
     * 当 Fragment 实例重建或 Fragment View 重建时
     * 对应的 ViewModel 仍然是复用的，所以 ViewModel 持有的数据，可以给重建好的页面直接使用，而不需要重新加载数据。
     * 减少用户等待时间，提高用户体验，留省流量。
     *
     * ```
     * override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
     *    super.onViewCreated(view, savedInstanceState)
     *    homeViewModel = fragmentProvider.viewModel(ArticleViewModel::class.java)
     *    homeViewModel.bindForFragment(this, view)
     *    // ignore ...
     * }
     * ```
     */
    fun bindForFragment(fragment: Fragment, view: View) {
        val fragmentCode = fragment.hashCode()
        val viewCode = view.hashCode()

        // 对应的页面发生了重建
        if (pageStamp != -1 && pageStamp != fragmentCode) {
            reuseData = true
        }

        // 对应页面的 View 发生了重建
        if (viewStamp != -1 && viewStamp != viewCode) {
            reuseData = true
        }

        pageStamp = fragmentCode
        viewStamp = viewCode
    }

    /**
     * 绑定 Activity 和 ViewModel 的版本关系（Activity 中使用 ViewModel）
     * 当 Activity 实例重建时，对应的 ViewModel 仍然是复用的，所以 ViewModel 持有的数据，可以给重建好的页面直接使用
     * 而不需要重新加载数据。减少用户等待时间，提高用户体验，留省流量。
     *
     * ```
     * override fun onCreate(savedInstanceState: Bundle?) {
     *   super.onCreate(savedInstanceState)
     *   setContentView(R.layout.activity_user_login)
     *   //...
     *   loginViewModel = activityProvider.viewModel(LoginViewModel::class.java)
     *   loginViewModel.bindForActivity(this)
     *   //...
     * }
     * ```
     *
     * @param activity ViewModel 所在的 activity
     */
    fun bindForActivity(activity: Activity) {
        val activityCode = activity.hashCode()
        // 对应的页面发生了重建
        if (pageStamp != -1 && pageStamp != activityCode) {
            reuseData = true
        }
        pageStamp = activityCode
    }

    /**
     * 是否重用数据，用于加载数据之前作判断
     *
     * ```
     * override fun lazyLoad() {
     *    val contentData = getContentData()
     *    if (articleListViewModel.getReuseFlagAndReset() && contentData?.isNotEmpty() == true) {
     *        adapter.submitList(contentData)
     *    } else {
     *        articleListViewModel.fetchArticleList(ArticleListRO(0, categoryId))
     *    }
     * }
     * ```
     */
    fun getReuseFlagAndReset(): Boolean {
        if (pageStamp == -1 && viewStamp == -1)
            throw IllegalStateException("invoke bindForFragment() or bindForActivity() first")
        val tmp = reuseData
        reuseData = false
        return tmp
    }

    fun <T> createLiveData(): LiveData<T> {
        return MutableLiveData()
    }

    fun <T> createLiveData(data: T): LiveData<T> {
        return MutableLiveData(data)
    }


}