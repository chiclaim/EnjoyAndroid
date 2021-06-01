package com.chiclaim.play.android.funcs.user.login

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import com.chiclaim.play.android.base.BaseViewModel
import com.chiclaim.play.android.bean.bo.RespBO
import com.chiclaim.play.android.bean.bo.UserBO
import com.chiclaim.play.android.bean.ro.LoginRO
import com.chiclaim.play.android.repos.UserRepos

/**
 * @author chiclaim@google.com
 */
class LoginViewModel : BaseViewModel() {

    private val userRepos = UserRepos()

    val usernameField = ObservableField<String>()
    val passwordField = ObservableField<String>()

    fun requestLogin(username: String, password: String): LiveData<RespBO<UserBO>> {
        return userRepos.login(LoginRO(username, password))
    }

}