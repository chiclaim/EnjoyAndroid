package com.chiclaim.play.android.bean.vo

/**
 * 文章分类
 *
 * @author by chiclaim@google.com
 */
class ArticleCategoryVO {

    var courseId = 0
    var id = 0
    var name: String? = null
    var order = 0

    // 父分类 id
    var parentChapterId = 0
    var userControlSetTop = false
    var visible = 1

    // 子分类
    var children: List<ArticleCategoryVO>? = null

}