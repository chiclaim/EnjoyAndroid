package com.chiclaim.play.android.funcs.article

import androidx.lifecycle.viewModelScope
import com.chiclaim.play.android.base.BaseViewModel
import com.chiclaim.play.android.bean.ro.ArticleListRO
import com.chiclaim.play.android.bean.vo.ArticleVO
import com.chiclaim.play.android.exception.ApiException
import com.chiclaim.play.android.exception.toApiException
import com.chiclaim.play.android.mapper.convert
import com.chiclaim.play.android.repos.ArticleRepos
import com.chiclaim.play.android.task.uiJob

/**
 *
 * @author by chiclaim@google.com
 */
class ArticleListViewModel : BaseViewModel() {

    val articleListLiveData = createLiveData<List<ArticleVO>>()
    val articleListFailedLiveData = createLiveData<ApiException>()

    fun fetchArticleList(requestParam: ArticleListRO) {
        viewModelScope.uiJob(block = {
            val pageData = ArticleRepos.getArticleList(requestParam).data
            pageData?.datas?.let {
                postValue(articleListLiveData, convert(it))
                return@uiJob
            }
        }, onFailed = {
            postValue(articleListFailedLiveData, it.toApiException())
        })
    }

}