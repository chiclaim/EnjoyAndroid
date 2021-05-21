package com.chiclaim.play.android.retrofit

import com.chiclaim.play.android.bean.bo.RespBO
import com.chiclaim.play.android.exception.ApiException
import com.chiclaim.play.android.exception.toApiException
import okhttp3.Request
import okio.Timeout
import retrofit2.*
import retrofit2.CallAdapter.Factory
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * 自定义 CallAdapter，统一处理和协程搭配使用时产生的接口错误（业务错误、网络错误）
 * @author chiclaim@google.com
 * @see LiveDataCallAdapterFactory
 */
class CoroutineCallAdapterFactory : Factory() {

    override fun get(
        returnType: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {

        // Call<RespBO<UserBO>> as example

        val rawType = Call::class.java

        if (getRawType(returnType) != rawType) {
            return null
        }

        check(returnType is ParameterizedType) { "$returnType must be parameterized. eg $returnType<XXX>" }

        //RespBO<UserBO>
        val observableType = getParameterUpperBound(0, returnType)

        check(getRawType(observableType) == RespBO::class.java) {
            "${rawType.simpleName}'s generic type must be RespBo, eg. ${rawType.simpleName}<${RespBO::class.simpleName}<XXX>>"
        }

        check(observableType is ParameterizedType) {
            "${rawType.simpleName} must have generic type, eg. ${rawType.simpleName}<${RespBO::class.simpleName}<XXX>>"
        }

        //UserBO
        //val bodyType = getParameterUpperBound(0, observableType)
        return CoroutineCallAdapter<RespBO<*>>(observableType)
    }

    class RespCall<R>(private val okHttpCall: Call<RespBO<R>>) : Call<RespBO<R>> {

        override fun clone(): Call<RespBO<R>> {
            return RespCall(okHttpCall.clone())
        }

        override fun execute(): Response<RespBO<R>> {
            return okHttpCall.execute()
        }

        override fun enqueue(callback: Callback<RespBO<R>>) {
            okHttpCall.enqueue(object : Callback<RespBO<R>> {
                override fun onResponse(
                    call: Call<RespBO<R>>,
                    response: Response<RespBO<R>>
                ) {
                    val body = response.body()
                    // 统一处理业务错误
                    if (body != null && !body.isSuccess()) {
                        callback.onFailure(
                            this@RespCall,
                            ApiException(body.errorCode, body.errorMsg ?: "unknown error")
                        )
                        return
                    }
                    when {
                        // 处理 HTTP code [200..300)
                        response.isSuccessful -> {
                            callback.onResponse(this@RespCall, Response.success(body))
                        }
                        // 处理 HTTP code 不在 [200..300) 范围的情况，如 405
                        response.errorBody() != null -> {
                            callback.onFailure(
                                this@RespCall,
                                ApiException(
                                    response.code(),
                                    response.errorBody()?.string() ?: "unknown error"
                                )
                            )
                        }
                        // 其他未知错误
                        else -> {
                            callback.onFailure(
                                this@RespCall,
                                ApiException(response.code(), "unknown error")
                            )
                        }
                    }

                }

                override fun onFailure(call: Call<RespBO<R>>, t: Throwable) {
                    callback.onFailure(this@RespCall, t.toApiException())
                }

            })
        }

        override fun isExecuted(): Boolean {
            return okHttpCall.isExecuted
        }

        override fun cancel() {
            okHttpCall.cancel()
        }

        override fun isCanceled(): Boolean {
            return okHttpCall.isCanceled
        }

        override fun request(): Request {
            return okHttpCall.request()
        }

        override fun timeout(): Timeout {
            return okHttpCall.timeout()
        }
    }


    class CoroutineCallAdapter<R>(private val responseType: Type) :
        CallAdapter<RespBO<R>, Call<RespBO<R>>> {

        override fun responseType() = responseType

        override fun adapt(delegate: Call<RespBO<R>>): Call<RespBO<R>> {
            return RespCall(delegate)
        }
    }

}
