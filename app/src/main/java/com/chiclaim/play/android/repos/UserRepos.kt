package com.chiclaim.play.android.repos

import androidx.lifecycle.LiveData
import com.chiclaim.play.android.bean.bo.RespBO
import com.chiclaim.play.android.bean.bo.UserBO
import com.chiclaim.play.android.bean.ro.LoginRO
import com.chiclaim.play.android.retrofit.Api
import com.chiclaim.play.android.retrofit.WanApi

/**
 * @author chiclaim@google.com
 */
object UserRepos {

    private val wanApi = Api.create(WanApi::class.java)

    fun login(request: LoginRO): LiveData<RespBO<UserBO>> {
        return wanApi.login(request.username, request.password)
    }

}