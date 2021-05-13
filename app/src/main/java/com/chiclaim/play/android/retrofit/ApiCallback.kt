package com.chiclaim.play.android.retrofit

import com.chiclaim.play.android.exception.ApiException

/**
 *
 * @author chiclaim@google.com
 */
interface ApiCallback<T> {

    fun onSuccess(data: T?)
    fun onFailed(exception: ApiException)
}