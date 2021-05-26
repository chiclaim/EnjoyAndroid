package com.chiclaim.play.android.repos

import com.chiclaim.play.android.bean.bo.ArticleBO
import com.chiclaim.play.android.bean.bo.ArticleCategoryBO
import com.chiclaim.play.android.bean.bo.PageBO
import com.chiclaim.play.android.bean.bo.RespBO
import com.chiclaim.play.android.bean.ro.ArticleListRO
import com.chiclaim.play.android.retrofit.Api
import com.chiclaim.play.android.retrofit.WanApi

/**
 *
 * @author by chiclaim@google.com
 */
object ArticleRepos {

    private val wanApi = Api.create(WanApi::class.java)

    suspend fun getArticleCategory(): RespBO<List<ArticleCategoryBO>> {
        return wanApi.articleCategories()
    }


    suspend fun getArticleList(requestParam: ArticleListRO): RespBO<PageBO<ArticleBO>> {
        return wanApi.articleList(requestParam.pageIndex, requestParam.categoryId)
    }


}