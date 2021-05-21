@file:JvmName("ArticleMapper")

package com.chiclaim.play.android.mapper

import com.chiclaim.play.android.bean.bo.ArticleCategoryBO
import com.chiclaim.play.android.bean.vo.ArticleCategoryVO

fun ArticleCategoryBO.convert2VO(): ArticleCategoryVO {
    val vo = this.convertSimpleVO()
    this.children?.map {
        it.convertSimpleVO()
    }?.let {
        vo.children = it
    }
    return vo
}

fun List<ArticleCategoryBO>.convert2VO() = this.map { bo ->
    bo.convert2VO()
}

private fun ArticleCategoryBO.convertSimpleVO() = ArticleCategoryVO().also {
    it.id = this.id
    it.courseId = this.courseId
    it.name = this.name
    it.order = this.order
    it.parentChapterId = this.parentChapterId
    it.userControlSetTop = this.userControlSetTop
    it.visible = this.visible
}

