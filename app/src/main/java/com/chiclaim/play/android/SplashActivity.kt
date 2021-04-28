package com.chiclaim.play.android

import android.content.Intent
import android.os.Bundle
import com.chiclaim.play.android.base.BaseActivity
import com.chiclaim.play.android.login.LoginActivity

/**
 * desc:
 *
 * Created by kumu@2dfire.com on 2021/4/26.
 */
class SplashActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startActivity(Intent(this, LoginActivity::class.java))

    }
}