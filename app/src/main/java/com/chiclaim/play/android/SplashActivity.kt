package com.chiclaim.play.android

import android.content.Intent
import android.os.Bundle
import com.chiclaim.play.android.base.BaseActivity
import com.chiclaim.play.android.funcs.user.login.LoginActivity
import com.chiclaim.play.android.retrofit.CookieInterceptor
import com.chiclaim.play.android.store.LightStore

/**
 * 闪屏页面
 *
 * @author chiclaim@google.com
 */
class SplashActivity : BaseActivity() {

    override fun getLayoutId() = R.layout.activity_splash

    override fun init(savedInstanceState: Bundle?) {
        super.init(savedInstanceState)
        if (LightStore.getString(CookieInterceptor.KEY_LOGIN_COOKIE).isNullOrBlank()) {
            startActivity(Intent(this, LoginActivity::class.java))
        } else {
            startActivity(Intent(this, MainActivity::class.java))
        }
        finish()
    }
}