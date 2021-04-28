package com.chiclaim.play.android.bean

/**
 * 统一抽取接口返回的 JSON bean，其中泛型参数 T 对应的是不同接口返回的 JSON bean，避免重复的模板代码。
 *
 * > 服务端返回的 JSON bean，作为原始数据，应设计成不可变，所以对应的属性均为 val
 *
 * Created by kumu@2dfire.com on 2021/4/26.
 */
class RespDO<T> {

    companion object{
        const val CODE_INVALID_LOGIN =  -1001
    }

    /**
     * 错误码：0 表示成功，其他情况均为失败
     *
     *  -1001 表示登录失效，虫咬重新登录
     *
     *  @see CODE_INVALID_LOGIN
     */
    val errorCode: Int = -1

    /**
     * errorMsg 错误信息。
     *
     * 如果发生错误，则返回错误信息；如果请求成功 errorMsg 也可能为空字符串 ""，所以需要同时处理 null 和 "" 的情况
     */
    val errorMsg: String? = null

    /**
     * 请求成功返回的数据
     */
    val data: T? = null

    /**
     * 接口是否请求成功
     */
    fun isSuccess() = errorCode == 0
}