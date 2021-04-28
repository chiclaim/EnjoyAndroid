package com.chiclaim.play.android.source

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * desc:
 *
 * Created by kumu@2dfire.com on 2021/4/26.
 */
object Api {

    private val client = OkHttpClient.Builder()
        .addInterceptor(CookieInterceptor())
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://www.wanandroid.com")
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun <T> create(clazz: Class<T>): T = retrofit.create(clazz)

}