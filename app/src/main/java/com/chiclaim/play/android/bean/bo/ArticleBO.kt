package com.chiclaim.play.android.bean.bo

/**
 * 接口返回的文章列表信息
 *
 * > 服务端返回的 JSON bean，作为原始数据，应设计成不可变，所以对应的属性设计为 val
 *
 * @author kumu@2dfire.com
 */
class ArticleBO {

    val id = 0
    val title: String? = null
    val type = 0
    val userId = 0
    val visible = 0
    val zan = 0
    val apkLink: String? = null
    val audit = 0
    val author: String? = null
    val canEdit = false
    val chapterId = 0
    val chapterName: String? = null
    val collect = false
    val courseId = 0
    val desc: String? = null
    val descMd: String? = null
    val envelopePic: String? = null
    val fresh = false
    val host: String? = null
    val link: String? = null
    val niceDate: String? = null
    val niceShareDate: String? = null
    val origin: String? = null
    val prefix: String? = null
    val projectLink: String? = null
    val publishTime = 0L
    val realSuperChapterId = 0
    val selfVisible = 0
    val shareDate = 0L
    val shareUser: String? = null
    val superChapterId = 0
    val superChapterName: String? = null
    val tags: List<Tag>? = null


    class Tag {
        val name: String? = null
        val url: String? = null
    }

}