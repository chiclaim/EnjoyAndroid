package com.chiclaim.play.android.user.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.chiclaim.play.android.bean.RespBO
import com.chiclaim.play.android.bean.UserBO
import com.chiclaim.play.android.bean.request.LoginRO
import com.chiclaim.play.android.repos.UserRepos

/**
 * Created by chiclaim@google.com
 */
class LoginViewModel : ViewModel() {

    fun getLoginLiveData(username: String, password: String): LiveData<RespBO<UserBO>> {
        return UserRepos.login(LoginRO(username, password))
    }


}