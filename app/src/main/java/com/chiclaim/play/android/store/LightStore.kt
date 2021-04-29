package com.chiclaim.play.android.store

import android.content.Context
import android.os.Parcelable
import com.tencent.mmkv.MMKV

/**
 *  轻量级本地存储（如 SharedPreference），由于 SharedPreference 问题比较多，本项目采用 Tencent MMKV
 *
 * Created by  chiclaim@google.com
 */
object LightStore {

    fun init(context: Context) {
        MMKV.initialize(context)
    }

    private fun getMMKV(name: String?): MMKV? {
        if (name?.isEmpty() == true) {
            return MMKV.defaultMMKV()
        }
        return MMKV.mmkvWithID(name)
    }

    fun putInt(key: String, value: Int, name: String? = null): LightStore {
        getMMKV(name)?.encode(key, value)
        return this
    }

    fun putBoolean(key: String, value: Boolean, name: String? = null): LightStore {
        getMMKV(name)?.encode(key, value)
        return this
    }

    fun putFloat(key: String, value: Float, name: String? = null): LightStore {
        getMMKV(name)?.encode(key, value)
        return this
    }

    fun putLong(key: String, value: Long, name: String? = null): LightStore {
        getMMKV(name)?.encode(key, value)
        return this
    }

    fun putDouble(key: String, value: Double, name: String? = null): LightStore {
        getMMKV(name)?.encode(key, value)
        return this
    }

    fun putSetString(key: String, value: Set<String>?, name: String? = null): LightStore {
        getMMKV(name)?.encode(key, value)
        return this
    }

    fun putByteArray(key: String, value: ByteArray?, name: String? = null): LightStore {
        getMMKV(name)?.encode(key, value)
        return this
    }


    fun putString(key: String, value: String?, name: String? = null): LightStore {
        getMMKV(name)?.encode(key, value)
        return this
    }

    fun putParcelable(key: String, value: Parcelable?, name: String? = null): LightStore {
        getMMKV(name)?.encode(key, value)
        return this
    }

    fun getInt(key: String, default: Int = 0, name: String? = null): Int {
        return getMMKV(name)?.decodeInt(key, default) ?: default
    }

    fun getBoolean(key: String, default: Boolean = false, name: String? = null): Boolean {
        return getMMKV(name)?.decodeBool(key, default) ?: default
    }

    fun getFloat(key: String?, default: Float = 0f, name: String? = null): Float {
        return getMMKV(name)?.decodeFloat(key, default) ?: default
    }

    fun getLong(key: String?, default: Long = 0, name: String? = null): Long {
        return getMMKV(name)?.decodeLong(key, default) ?: default
    }

    fun getDouble(key: String?, default: Double = 0.0, name: String? = null): Double {
        return getMMKV(name)?.decodeDouble(key, default) ?: default
    }

    fun getSetString(
        key: String?,
        default: Set<String>? = null,
        name: String? = null
    ): Set<String>? {
        return getMMKV(name)?.decodeStringSet(key, default) ?: default
    }

    fun getByteArray(key: String?, default: ByteArray?, name: String? = null): ByteArray? {
        return getMMKV(name)?.decodeBytes(key, default) ?: default
    }

    fun getString(key: String, value: String? = null, name: String? = null): String? {
        return getMMKV(name)?.decodeString(key, value)
    }

    fun <T : Parcelable?> getParcelable(key: String, clazz: Class<T>, name: String? = null): T? {
        return getMMKV(name)?.decodeParcelable(key, clazz)
    }


}