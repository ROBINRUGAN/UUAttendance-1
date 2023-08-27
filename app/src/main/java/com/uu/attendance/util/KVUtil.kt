package com.uu.attendance.util

import com.tencent.mmkv.MMKV

object KVUtil {
    private val mmkv by lazy { MMKV.defaultMMKV() }

    fun put(key: String, value: Any) {
        when (value) {
            is String -> mmkv.encode(key, value)
            is Int -> mmkv.encode(key, value)
            is Boolean -> mmkv.encode(key, value)
            is Float -> mmkv.encode(key, value)
            is Long -> mmkv.encode(key, value)
            is ByteArray -> mmkv.encode(key, value)
            else -> throw IllegalArgumentException("Unsupported type.")
        }
    }

    @Suppress("UNCHECKED_CAST")
    fun <T> get(key: String, default: T): T = when (default) {
        is String -> mmkv.decodeString(key, default) as T
        is Int -> mmkv.decodeInt(key, default) as T
        is Boolean -> mmkv.decodeBool(key, default) as T
        is Float -> mmkv.decodeFloat(key, default) as T
        is Long -> mmkv.decodeLong(key, default) as T
        is ByteArray -> mmkv.decodeBytes(key, default) as T
        else -> throw IllegalArgumentException("Unsupported type.")
    }

}