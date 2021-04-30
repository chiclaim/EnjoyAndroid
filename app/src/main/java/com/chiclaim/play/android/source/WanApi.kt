package com.chiclaim.play.android.source

import androidx.lifecycle.LiveData
import com.chiclaim.play.android.bean.ArticleBO
import com.chiclaim.play.android.bean.PageBO
import com.chiclaim.play.android.bean.RespBO
import com.chiclaim.play.android.bean.UserBO
import retrofit2.Call
import retrofit2.http.*

/**
 *
 *
 * @author kumu@2dfire.com
 */
interface WanApi {


    /**
     * 登录
     */
    @POST("/user/login")
    @FormUrlEncoded
    fun login(
        @Field("username") username: String,
        @Field("password") password: String): LiveData<RespBO<UserBO>>


    /**
     * 获取文章列表
     * @param pageIndex 从 0 开始
     */
    @GET("/article/list/{pageIndex}/json")
    fun getArticleList(
        @Path("pageIndex") pageIndex: Int): Call<RespBO<PageBO<ArticleBO>>>


}