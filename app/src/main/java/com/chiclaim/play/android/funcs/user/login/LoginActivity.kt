package com.chiclaim.play.android.funcs.user.login

import android.content.Intent
import android.os.Bundle
import com.chiclaim.play.android.BR
import com.chiclaim.play.android.MainActivity
import com.chiclaim.play.android.R
import com.chiclaim.play.android.base.BaseViewModelActivity
import com.chiclaim.play.android.databinding.ActivityUserLoginBinding
import com.chiclaim.play.android.utils.ToastUtil

/**
 * 登录页面
 *
 * @author chiclaim@google.com
 */
class LoginActivity : BaseViewModelActivity<ActivityUserLoginBinding, LoginViewModel>() {

    override fun getLayoutId() = R.layout.activity_user_login

    override fun viewModelClass(): Class<LoginViewModel> = LoginViewModel::class.java

    override fun init(savedInstanceState: Bundle?) {
        super.init(savedInstanceState)
        bindVariables(hashMapOf(BR.viewModel to viewModel))
        requireDataBinding().btnLogin.setOnClickListener {
            val username = viewModel.usernameField.get()
            val pwd = viewModel.passwordField.get()

            if (username.isNullOrBlank()) {
                ToastUtil.showShort(this, R.string.require_username)
                return@setOnClickListener
            }

            if (pwd.isNullOrBlank()) {
                ToastUtil.showShort(this, R.string.require_password)
                return@setOnClickListener
            }

            viewModel.requestLogin(username, pwd)
                .observe(this) {
                    if (it.isSuccess()) {
                        startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                        finish()
                    } else {
                        ToastUtil.showShort(this, it.codeMessage())
                    }
                }
        }
    }
}