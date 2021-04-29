package com.chiclaim.play.android.source

import com.chiclaim.play.android.exception.ApiException

/**
 *
 * Created by chiclaim@google.com
 */
interface ApiCallback<T> {

    fun onSuccess(data: T?)
    fun onFailed(exception: ApiException)
}