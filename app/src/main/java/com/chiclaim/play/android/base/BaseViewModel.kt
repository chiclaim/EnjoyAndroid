package com.chiclaim.play.android.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 *
 * @author by chiclaim@google.com
 */
abstract class BaseViewModel : ViewModel() {

    companion object {
        fun <T> postValue(liveData: LiveData<T>, data: T) {
            if (liveData is MutableLiveData)
                liveData.postValue(data)
            else throw IllegalArgumentException("$liveData must be MutableLiveData")
        }

        fun <T> setValue(liveData: LiveData<T>, data: T) {
            if (liveData is MutableLiveData)
                liveData.value = (data)
            else throw IllegalArgumentException("$liveData must be MutableLiveData")
        }
    }

    fun <T> createLiveData(): LiveData<T> {
        return MutableLiveData()
    }


}