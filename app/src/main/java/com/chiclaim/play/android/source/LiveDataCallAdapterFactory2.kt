package com.chiclaim.play.android.source

import androidx.lifecycle.LiveData
import com.chiclaim.play.android.bean.RespBO
import retrofit2.*
import retrofit2.CallAdapter.Factory
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import java.util.concurrent.atomic.AtomicBoolean

class LiveDataCallAdapterFactory2 : Factory() {

    override fun get(
        returnType: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {


        if (getRawType(returnType) != LiveData::class.java) {
            return null
        }

        check(returnType is ParameterizedType) {
            "LiveData must have generic type (e.g., LiveData<RespBO<UserBO>>)"
        }

        val type = getParameterUpperBound(0, returnType)

        return LiveDataCallAdapter<Any>(type)





        /*if (getRawType(returnType) != LiveData::class.java) {
            return null
        }
        val observableType = getParameterUpperBound(0, returnType as ParameterizedType)
        val rawObservableType = getRawType(observableType)
        if (rawObservableType != RespBO::class.java) {
            throw IllegalArgumentException("type must be a resource")
        }
        if (observableType !is ParameterizedType) {
            throw IllegalArgumentException("resource must be parameterized")
        }
        val bodyType = getParameterUpperBound(0, observableType)
        return LiveDataCallAdapter<Any>(bodyType)*/
    }


    class LiveDataCallAdapter<R>(private val responseType: Type) :
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
                                postValue(RespBO.create(response.body()))
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
