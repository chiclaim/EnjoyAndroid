package com.chiclaim.play.android.bean

/**
 * 接口返回的用户信息
 *
 * > 服务端返回的 JSON bean，作为原始数据，应设计成不可变，所以对应的属性均为 val
 *
 * @author chiclaim@google.com
 *
 * @see RespBO
 */
class UserBO {

    val admin = false
    val email: String? = null
    val publicName: String? = null
    val username: String? = null
    val coinCount = 0
    val collectIds: List<Int>? = null


}