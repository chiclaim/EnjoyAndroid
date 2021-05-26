@file:JvmName("ArticleMapper")

package com.chiclaim.play.android.mapper

import com.chiclaim.play.android.bean.bo.ArticleBO
import com.chiclaim.play.android.bean.bo.ArticleCategoryBO
import com.chiclaim.play.android.bean.vo.ArticleCategoryVO
import com.chiclaim.play.android.bean.vo.ArticleVO
import com.chiclaim.play.android.utils.DateUtil

fun ArticleCategoryBO.convert2VO(): ArticleCategoryVO {
    val vo = this.convertSimpleVO()
    this.children?.map {
        it.convertSimpleVO()
    }?.let {
        vo.children = it
    }
    return vo
}

fun List<ArticleCategoryBO>.convert2VO() = this.map { bo -> bo.convert2VO() }

private fun ArticleCategoryBO.convertSimpleVO() = ArticleCategoryVO().also {
    it.id = this.id
    it.courseId = this.courseId
    it.name = this.name
    it.order = this.order
    it.parentChapterId = this.parentChapterId
    it.userControlSetTop = this.userControlSetTop
    it.visible = this.visible
}

fun ArticleBO.convert2VO(): ArticleVO {
    return ArticleVO().also {
        it.id = this.id
        it.title = this.title
        it.author = if (this.author.isNullOrEmpty()) this.shareUser else this.author
        it.date = DateUtil.format(DateUtil.PATTERN_SIMPLE_DATE, this.publishTime)
    }
}

fun convert(source: List<ArticleBO>) = source.map { it.convert2VO() }

