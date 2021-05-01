package com.chiclaim.play.android

import android.app.Application
import android.os.StrictMode
import android.os.StrictMode.VmPolicy
import com.chiclaim.play.android.store.LightStore
import com.chiclaim.play.android.utils.AppGlobal


/**
 * @author chiclaim@google.com
 */
class TheApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            startStrictModeVmPolicy()
            startStrictModeThreadPolicy()
        }

        AppGlobal.init(this)
        LightStore.init(this)
    }


    private fun startStrictModeVmPolicy() {
        StrictMode.setVmPolicy(VmPolicy.Builder()
                // 检测Activity内存泄露
                .detectActivityLeaks()
                // 检测未关闭的Closable对象
                .detectLeakedClosableObjects()
                // 检测SQLite对象是否关闭
                .detectLeakedSqlLiteObjects()
                .penaltyLog().build());
    }

    private fun startStrictModeThreadPolicy() {
        StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.Builder()
                // 磁盘读取操作检测
                //.detectDiskReads()
                // 检测磁盘写入操作
                //.detectDiskWrites()
                // 检测网络操作
                .detectNetwork()
                .penaltyLog().build());
    }
}