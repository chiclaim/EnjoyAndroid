package com.chiclaim.play.android.bean.bo

import com.chiclaim.play.android.bean.bo.RespBO.Companion.CODE_INVALID_LOGIN
import com.chiclaim.play.android.exception.toApiException

/**
 * 统一抽取接口返回的 JSON bean，其中泛型参数 T 对应的是不同接口返回的 JSON bean，避免重复的模板代码。
 *
 * > 服务端返回的 JSON bean，作为原始数据，应设计成不可变，所以对应的属性均为 val
 *
 * @param errorCode 错误码：0 表示成功，其他情况均为失败，-1001 表示登录失效，需要重新登录 [CODE_INVALID_LOGIN]
 * @param errorMsg 错误信息，如果发生错误，则返回错误信息；如果请求成功 errorMsg 也可能为空字符串 ""，所以需要同时处理 null 和 "" 的情况
 * @param data 请求成功返回的数据对象
 *
 * @author chiclaim@google.com
 */
class RespBO<T>(
    val errorCode: Int = -1,
    val errorMsg: String? = null,
    val data: T? = null
) {

    companion object {
        const val CODE_INVALID_LOGIN = -1001

        fun <T> create(throwable: Throwable): RespBO<T> {
            val e = throwable.toApiException()
            return RespBO(
                errorCode = e.errorCode,
                errorMsg = e.message
            )
        }
    }

    /**
     * 接口是否请求成功
     */
    fun isSuccess() = errorCode == 0

    /**
     * 获取错误码和错误信息
     */
    fun getErrCodeMsg() = "[$errorCode] $errorMsg"
}