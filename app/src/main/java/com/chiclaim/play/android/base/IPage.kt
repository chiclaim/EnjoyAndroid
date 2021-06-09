package com.chiclaim.play.android.base

/**
 *
 * @author by chiclaim@google.com
 */
interface IPage {

    interface ILayout {

        /**
         * 抽象布局设置方法，接口方法粒度细化。Fragment 和 Activity 封装的时候都会用到
         */
        fun getLayoutId(): Int
    }

    /**
     * 页面公共加载行为抽象
     */
    interface ILoad {

        /**
         * 延迟加载
         */
        fun lazyLoad()

        /**
         * 真正加载数据
         */
        fun loadData()
    }
}