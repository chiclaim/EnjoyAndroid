package com.chiclaim.play.android.retrofit

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * @author chiclaim@google.com
 */
object Api {

    private val client = OkHttpClient.Builder()
        .addInterceptor(CookieInterceptor())
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://www.wanandroid.com")
        .client(client)
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .addCallAdapterFactory(LiveDataCallAdapterFactory())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun <T> create(clazz: Class<T>): T = retrofit.create(clazz)

}