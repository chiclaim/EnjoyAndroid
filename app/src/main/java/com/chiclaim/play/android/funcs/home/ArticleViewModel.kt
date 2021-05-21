package com.chiclaim.play.android.funcs.home

import androidx.lifecycle.viewModelScope
import com.chiclaim.play.android.base.BaseViewModel
import com.chiclaim.play.android.bean.bo.ArticleCategoryBO
import com.chiclaim.play.android.bean.vo.ArticleCategoryVO
import com.chiclaim.play.android.exception.ApiException
import com.chiclaim.play.android.exception.toApiException
import com.chiclaim.play.android.mapper.convert2VO
import com.chiclaim.play.android.retrofit.Api
import com.chiclaim.play.android.retrofit.WanApi
import com.chiclaim.play.android.task.uiJob

class ArticleViewModel : BaseViewModel() {

    val categoryData = createLiveData<List<ArticleCategoryVO>>()
    val categoryDataError = createLiveData<ApiException>()


    private val wanApi = Api.create(WanApi::class.java)

    fun fetchArticleCategories() {
        viewModelScope.uiJob(block = {
            val data = wanApi.articleCategories().data
            if (data != null) {
                val list = data.convert2VO()
                postValue(categoryData, list)
            }

        }, onFailed = {
            postValue(categoryDataError, it.toApiException())
        })
    }

}