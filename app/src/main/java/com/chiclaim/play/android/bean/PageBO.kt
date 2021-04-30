package com.chiclaim.play.android.bean

/**
 * 服务端返回的列表模板类
 *
 * @author kumu@2dfire.com
 */
class PageBO<T> {

    val curPage = 0
    val datas: List<T>? = null
    val offset = 0
    val over = false
    val pageCount = 0
    val size = 0
    val total = 0


}