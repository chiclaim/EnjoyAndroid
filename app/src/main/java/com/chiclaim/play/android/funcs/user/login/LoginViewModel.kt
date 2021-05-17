package com.chiclaim.play.android.funcs.user.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.chiclaim.play.android.bean.bo.RespBO
import com.chiclaim.play.android.bean.bo.UserBO
import com.chiclaim.play.android.bean.ro.LoginRO
import com.chiclaim.play.android.repos.UserRepos

/**
 * @author chiclaim@google.com
 */
class LoginViewModel : ViewModel() {

    fun getLoginLiveData(username: String, password: String): LiveData<RespBO<UserBO>> {
        return UserRepos.login(LoginRO(username, password))
    }


}