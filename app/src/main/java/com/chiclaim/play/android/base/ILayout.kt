package com.chiclaim.play.android.base

/**
 * 抽象布局设置方法，接口方法粒度细化。Fragment 和 Activity 封装的时候都会用到
 * @author by chiclaim@google.com
 */
interface ILayout {

    fun getLayoutId():Int
}