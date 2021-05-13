package com.chiclaim.play.android.retrofit

import androidx.lifecycle.LiveData
import com.chiclaim.play.android.bean.ArticleBO
import com.chiclaim.play.android.bean.PageBO
import com.chiclaim.play.android.bean.RespBO
import com.chiclaim.play.android.bean.UserBO
import retrofit2.Call
import retrofit2.http.*

/**
 * wanandroid.com open api
 *
 * @author kumu@2dfire.com
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
        @Field("password") password: String): LiveData<RespBO<UserBO>> // 搭配 LiveData


    /**
     * 获取文章列表
     * @param pageIndex 从 0 开始
     */
    @GET("/article/list/{pageIndex}/json")
    fun getArticleList(
        @Path("pageIndex") pageIndex: Int): Call<RespBO<PageBO<ArticleBO>>> // retrofit Call


    /**
     * 获取收藏的文章列表
     * @param pageIndex 从 0 开始
     */
    @GET("/lg/collect/list/{pageIndex}/json")
    suspend fun getCollectedArticleList(
        @Path("pageIndex") pageIndex: Int): RespBO<PageBO<ArticleBO>> // 搭配 Coroutine

}