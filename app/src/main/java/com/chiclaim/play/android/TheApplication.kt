package com.chiclaim.play.android

import android.app.Application
import com.chiclaim.play.android.store.LightStore

/**
 * Created by chiclaim@google.com
 */
class TheApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        LightStore.init(this)
    }
}