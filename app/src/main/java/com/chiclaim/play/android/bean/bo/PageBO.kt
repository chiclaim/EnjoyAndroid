package com.chiclaim.play.android.bean.bo

/**
 * 服务端返回的列表模板类
 *
 * @author chiclaim@google.com
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