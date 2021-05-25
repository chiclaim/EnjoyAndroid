package com.chiclaim.play.android

import com.chiclaim.play.android.utils.DateUtil
import org.junit.Test

import org.junit.Assert.*
import java.text.SimpleDateFormat

/**
 * 工具类 [DateUtil] 的单元测试
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class DateUtilUnitTest {

    @Test
    fun testFormat() {

        val current = System.currentTimeMillis()

        val f1 = DateUtil.format(DateUtil.PATTERN_TIME_DETAIL, current)

        val sdf = SimpleDateFormat()
        sdf.applyPattern(DateUtil.PATTERN_TIME_DETAIL)
        val f2 = sdf.format(current)

        assertEquals(f1, f2)

        println("mill=$current -> $f1")
    }

    @Test
    fun testParse() {
        val dateStr = "2021-05-25 11:06:49"
        val v1 = DateUtil.parse(DateUtil.PATTERN_TIME_DETAIL, dateStr)
        val dateStr2 = DateUtil.format(DateUtil.PATTERN_TIME_DETAIL, v1)
        assertEquals(dateStr, dateStr2)
    }
}