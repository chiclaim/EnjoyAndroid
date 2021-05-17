package com.chiclaim.play.android.retrofit

import androidx.lifecycle.LiveData
import com.chiclaim.play.android.bean.bo.RespBO
import retrofit2.*
import retrofit2.CallAdapter.Factory
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import java.util.concurrent.atomic.AtomicBoolean

/**
 * 自定义 CallAdapter，整合 Retrofit 和 LiveData
 * @author chiclaim@google.com
 */
class LiveDataCallAdapterFactory : Factory() {

    override fun get(
        returnType: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {

        // LiveData<RespBO<UserBO>> as example

        if (getRawType(returnType) != LiveData::class.java) {
            return null
        }
        val observableType = getParameterUpperBound(0, returnType as ParameterizedType)
        // Log.e("LiveDataAdapterFactory", observableType.typeName) //RespBO<UserBO>

        val rawObservableType = getRawType(observableType)

        check(rawObservableType == RespBO::class.java) {
            "LiveData's generic type must be RespBo, eg. LiveData<${RespBO::class.simpleName}<XXX>>"
        }

        check(observableType is ParameterizedType) {
            "LiveData must have generic type, eg. LiveData<${RespBO::class.simpleName}<XXX>>"
        }

        // val bodyType = getParameterUpperBound(0, observableType)
        // Log.e("LiveDataAdapterFactory", bodyType.typeName) //UserBO
        return LiveDataCallAdapter<RespBO<*>>(observableType)
    }


    class LiveDataCallAdapter<R : RespBO<R>>(private val responseType: Type) :
        CallAdapter<R, LiveData<RespBO<R>>> {

        override fun responseType() = responseType

        override fun adapt(call: Call<R>): LiveData<RespBO<R>> {
            return object : LiveData<RespBO<R>>() {
                private var started = AtomicBoolean(false)
                override fun onActive() {
                    super.onActive()
                    if (started.compareAndSet(false, true)) {
                        call.enqueue(object : Callback<R> {
                            override fun onResponse(call: Call<R>, response: Response<R>) {
                                postValue(response.body())
                            }

                            override fun onFailure(call: Call<R>, throwable: Throwable) {
                                postValue(RespBO.create(throwable))
                            }
                        })
                    }
                }
            }
        }
    }

}
