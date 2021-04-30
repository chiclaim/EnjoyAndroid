package com.chiclaim.play.android

import android.app.Application
import com.chiclaim.play.android.store.LightStore
import com.chiclaim.play.android.utils.AppGlobal

/**
 * @author chiclaim@google.com
 */
class TheApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        AppGlobal.init(this)
        LightStore.init(this)
    }
}