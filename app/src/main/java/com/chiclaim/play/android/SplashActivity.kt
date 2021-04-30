package com.chiclaim.play.android

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.chiclaim.play.android.base.BaseActivity
import com.chiclaim.play.android.user.login.LoginActivity

/**
 * 闪屏页面
 *
 * @author chiclaim@google.com
 */
class SplashActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startActivity(Intent(this, LoginActivity::class.java))
        Log.e("LiveDataAdapterFactory","SplashActivity")
        finish()
    }
}