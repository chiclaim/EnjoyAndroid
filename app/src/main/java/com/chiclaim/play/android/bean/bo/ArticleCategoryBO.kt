package com.chiclaim.play.android.bean.bo

/**
 * 文章分类
 *
 * @author by chiclaim@google.com
 */
class ArticleCategoryBO {

    val courseId = 0
    val id = 0
    val name: String? = null
    val order = 0

    // 父分类 id
    val parentChapterId = 0
    val userControlSetTop = false
    val visible = 1

    // 子分类
    val children: List<ArticleCategoryBO>? = null

}