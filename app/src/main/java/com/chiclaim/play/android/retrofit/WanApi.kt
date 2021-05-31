package com.chiclaim.play.android.retrofit

import androidx.lifecycle.LiveData
import com.chiclaim.play.android.bean.bo.*
import retrofit2.Call
import retrofit2.http.*

/**
 * wanandroid.com open api
 *
 * @author chiclaim@google.com
 */
interface WanApi {


    /**
     * 登录接口
     * @param username 用户名
     * @param password 密码
     */
    @POST("/user/login")
    @FormUrlEncoded
    fun login(
        @Field("username") username: String,
        @Field("password") password: String
    ): LiveData<RespBO<UserBO>> // 搭配 LiveData


    /**
     * 获取文章列表
     * @param pageIndex 从 0 开始
     */
    @GET("/article/list/{pageIndex}/json")
    fun getArticleList(
        @Path("pageIndex") pageIndex: Int
    ): Call<RespBO<PageBO<ArticleBO>>> // retrofit Call


    /**
     * 获取收藏的文章列表
     * @param pageIndex 从 0 开始
     */
    @GET("/lg/collect/list/{pageIndex}/json")
    suspend fun getCollectedArticleList(
        @Path("pageIndex") pageIndex: Int
    ): RespBO<PageBO<ArticleBO>> // 搭配 Coroutine


    /**
     * 文章分类（体系）
     */
    @GET("/tree/json")
    suspend fun articleCategories(): RespBO<List<ArticleCategoryBO>>


    /**
     * 文章列表
     */
    @GET("/article/list/{pageIndex}/json")
    suspend fun articleList(
        @Path("pageIndex") pageIndex: Int,
        @Query("cid") categoryId: Int
    ): RespBO<PageBO<ArticleBO>>


}