package com.chiclaim.play.android.source

import com.chiclaim.play.android.bean.ArticleDO
import com.chiclaim.play.android.bean.PageDO
import com.chiclaim.play.android.bean.RespDO
import com.chiclaim.play.android.bean.UserDO
import retrofit2.Call
import retrofit2.http.*

/**
 * desc:
 *
 * Created by kumu@2dfire.com
 */
interface WanApi {


    /**
     * 登录
     */
    @POST("/user/login")
    @FormUrlEncoded
    fun login(
        @Field("username") username: String,
        @Field("password") password: String): Call<RespDO<UserDO>>


    /**
     * 获取文章列表
     * @param pageIndex 从 0 开始
     */
    @GET("/article/list/{pageIndex}/json")
    fun getArticleList(
        @Path("pageIndex") pageIndex: Int): Call<RespDO<PageDO<ArticleDO>>>


}