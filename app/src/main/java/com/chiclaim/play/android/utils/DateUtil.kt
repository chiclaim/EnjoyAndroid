package com.chiclaim.play.android.utils

import android.os.Build
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

/**
 * 时间格式化和解析工具类。
 *
 * [java.text.SimpleDateFormat] 有线程安全问题（内部共享 calendar 对象），通过 ThreadLocal 解决。
 * 对于 Android [Build.VERSION_CODES.O] 及以上版本使用 [java.time.format.DateTimeFormatter]
 *
 * @author by chiclaim@google.com
 */
class DateUtil {

    companion object {

        const val PATTERN_SIMPLE_DATE = "yyyy-MM-dd"
        const val PATTERN_SIMPLE_DATE_BACKSLASH = "yyyy/MM/dd"
        const val PATTERN_TIME_DETAIL = "yyyy-MM-dd HH:mm:ss"


        fun format(pattern: String, milli: Long): String {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val ldt =
                    LocalDateTime.ofInstant(Instant.ofEpochMilli(milli), ZoneId.systemDefault())
                DateTimeFormatter.ofPattern(pattern).format(ldt)
            } else {
                val sdf = SimpleDateFormatter.simpleDateFormat()
                sdf.applyPattern(pattern)
                sdf.format(Date(milli))
            }
        }

        fun parse(pattern: String, str: String): Long {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val dtf = DateTimeFormatter.ofPattern(pattern)
                val localDateTime = LocalDateTime.parse(str, dtf)
                localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
            } else {
                val sdf = SimpleDateFormatter.simpleDateFormat()
                sdf.applyPattern(pattern)
                try {
                    sdf.parse(str)?.time ?: 0L
                } catch (e: ParseException) {
                    e.printStackTrace()
                    0
                }
            }
        }


        private class SimpleDateFormatter {
            companion object {
                private val THREAD_LOCAL = object : ThreadLocal<SimpleDateFormat>() {
                    override fun initialValue(): SimpleDateFormat {
                        return SimpleDateFormat(PATTERN_TIME_DETAIL, Locale.CHINA)
                    }
                }


                fun simpleDateFormat(): SimpleDateFormat {
                    return THREAD_LOCAL.get()
                        ?: error("SimpleDateFormat is null in ${Thread.currentThread().name}")
                }
            }
        }

    }
}




