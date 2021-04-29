package com.chiclaim.play.android.repos

import androidx.lifecycle.LiveData
import com.chiclaim.play.android.bean.RespBO
import com.chiclaim.play.android.bean.UserBO
import com.chiclaim.play.android.bean.request.LoginRO
import com.chiclaim.play.android.exception.toApiException
import com.chiclaim.play.android.source.Api
import com.chiclaim.play.android.source.ApiCallback
import com.chiclaim.play.android.source.WanApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by chiclaim@google.com
 */
object UserRepos {

    private val wanApi = Api.create(WanApi::class.java)

    fun login(request: LoginRO): LiveData<RespBO<UserBO>> {
        return wanApi.login(request.username, request.password)
    }

}