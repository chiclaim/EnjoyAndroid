package com.chiclaim.play.android.utils

import android.content.Context
import androidx.annotation.DimenRes
import androidx.annotation.StringRes

/**
 *
 * 提供 Context 获取资源相关的方法，例如 [Context.getString] 等
 *
 * 使用前需要在 Application 中进行初始化：AppGlobal.init(context)
 *
 * 禁止在此处共享其他全局变量对象，避免内存泄漏。
 *
 * Created by chiclaim@google.com
 */
object AppGlobal {

    private lateinit var context: Context

    fun init(context: Context) {
        AppGlobal.context = context.applicationContext
    }

    fun getString(@StringRes resId: Int): String {
        return context.resources.getString(resId)
    }

    fun getString(@StringRes resId: Int, vararg formatArgs: Any): String {
        return context.resources.getString(resId, *formatArgs)
    }

    fun getDimensionPixelOffset(@DimenRes id: Int) = context.resources.getDimensionPixelOffset(id)

    fun getDimensionPixelSize(@DimenRes id: Int) = context.resources.getDimensionPixelSize(id)




}