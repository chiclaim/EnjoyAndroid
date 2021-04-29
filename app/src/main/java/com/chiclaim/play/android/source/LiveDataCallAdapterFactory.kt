package com.chiclaim.play.android.source

import android.util.Log
import androidx.lifecycle.LiveData
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import java.util.concurrent.atomic.AtomicBoolean

class LiveDataCallAdapterFactory : CallAdapter.Factory() {

    override fun get(returnType: Type, annotations: Array<out Annotation>?, retrofit: Retrofit?): CallAdapter<*, *>? {

        //androidx.lifecycle.LiveData<com.chiclaim.play.android.bean.RespBO<com.chiclaim.play.android.bean.UserBO>>
        //Log.e("LiveDataAdapterFactory",returnType.typeName)

        if (getRawType(returnType) != LiveData::class.java) {
            return null
        }

        check(returnType is ParameterizedType) {
             "LiveData must have generic type (e.g., LiveData<ResponseBody>)"
        }


        val type = getParameterUpperBound(0, returnType)

        //com.chiclaim.play.android.bean.RespBO<com.chiclaim.play.android.bean.UserBO>
        //Log.e("LiveDataAdapterFactory",type.typeName)

        return LiveDataCallAdapter<Any>(type)
    }

    class LiveDataCallAdapter<R>(private var type: Type) : CallAdapter<R, LiveData<R>> {
        override fun adapt(call: retrofit2.Call<R>): LiveData<R> {
            return object : LiveData<R>() {

                val flag = AtomicBoolean(false)

                override fun onActive() {
                    super.onActive()
                    if (flag.compareAndSet(false, true)) {
                        call.enqueue(object : retrofit2.Callback<R> {
                            override fun onFailure(call: retrofit2.Call<R>?, t: Throwable?) {
                                t?.printStackTrace()
                                postValue(null)
                            }

                            override fun onResponse(call: retrofit2.Call<R>?, response: retrofit2.Response<R>?) {
                                postValue(response?.body())
                            }
                        })
                    }
                }
            }
        }

        override fun responseType(): Type {
            return type
        }
    }
}