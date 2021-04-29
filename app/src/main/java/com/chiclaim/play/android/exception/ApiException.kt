package com.chiclaim.play.android.exception

import java.lang.Exception

/**
 *
 * Created by chiclaim@google.com
 */
class ApiException(val errorCode: String, errorMessage: String) : Exception(errorMessage)


fun Throwable.toApiException(): ApiException {
    if (this is ApiException) return this
    return ApiException("unknown_error_code", message ?: "unknown_error_message")
}