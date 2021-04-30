package com.chiclaim.play.android.exception

import com.chiclaim.play.android.NetError
import com.google.gson.JsonParseException
import java.lang.Exception
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * 自定义网络请求异常类
 *
 * @param errorCode 错误码
 * @param errorMsg 错误消息
 * Created by chiclaim@google.com
 */
class ApiException(val errorCode: Int, errorMsg: String) : Exception(errorMsg)

/**
 * 将底层的异常转换成上层能够识别的自定义异常
 */
fun Throwable.toApiException(): ApiException {
    if (this is ApiException) return this
    return when (this) {
        is SocketTimeoutException -> ApiException(
            NetError.ERR_CODE_TIMEOUT,
            NetError.getTimeoutMsg()
        )
        is ConnectException -> ApiException(
            NetError.ERR_CODE_CONNECT,
            NetError.getConnectErrMsg()
        )
        is UnknownHostException -> ApiException(
            NetError.ERR_CODE_TIMEOUT,
            NetError.getTimeoutMsg()
        )
        is JsonParseException -> ApiException(
            NetError.ERR_CODE_PARSE_DATA,
            NetError.getParseDataErrMsg()
        )
        else -> ApiException(
            NetError.ERR_CODE_UNKNOWN,
            this.message ?: NetError.getUnknownErrMsg()
        )
    }
}