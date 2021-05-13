package com.chiclaim.play.android

import android.content.Intent
import android.os.Bundle
import android.util.Log
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (LightStore.getString(CookieInterceptor.KEY_LOGIN_COOKIE).isNullOrEmpty()) {
            startActivity(Intent(this, LoginActivity::class.java))
        } else {
            startActivity(Intent(this, MainActivity::class.java))
        }
        finish()
    }
}