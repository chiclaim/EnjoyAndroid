package com.chiclaim.play.android

import android.app.Application
import com.chiclaim.play.android.store.LightStore

/**
 * desc:
 *
 * Created by kumu@2dfire.com on 2021/4/27.
 */
class TheApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        LightStore.init(this)
    }
}