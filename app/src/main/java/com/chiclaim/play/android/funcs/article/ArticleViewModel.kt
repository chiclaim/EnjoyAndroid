package com.chiclaim.play.android.funcs.article

import androidx.lifecycle.viewModelScope
import com.chiclaim.play.android.base.BaseViewModel
import com.chiclaim.play.android.bean.vo.ArticleCategoryVO
import com.chiclaim.play.android.exception.ApiException
import com.chiclaim.play.android.exception.toApiException
import com.chiclaim.play.android.mapper.convert2VO
import com.chiclaim.play.android.repos.ArticleRepos
import com.chiclaim.play.android.task.uiJob

class ArticleViewModel : BaseViewModel() {

    val categoryData = createLiveData<List<ArticleCategoryVO>>()
    val categoryDataError = createLiveData<ApiException>()



    fun fetchArticleCategories() {
        viewModelScope.uiJob(block = {
            val data = ArticleRepos.getArticleCategory().data
            if (data != null) {
                val list = data.convert2VO()
                postValue(categoryData, list)
            }

        }, onFailed = {
            postValue(categoryDataError, it.toApiException())
        })
    }

}