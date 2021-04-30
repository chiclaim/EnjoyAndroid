package com.chiclaim.play.android.utils

import com.chiclaim.play.android.R

/**
 * 网络错误信息封装。错误信息需要国际化，所以不能硬编码。将逻辑封装在 [NetError] 中便于将来替换实现
 *
 * @author chiclaim@google.com
 */
class NetError {

    companion object {
        private const val ERR_CODE_OFFSET = 110011
        const val ERR_CODE_UNKNOWN = ERR_CODE_OFFSET + 1
        const val ERR_CODE_CONNECT = ERR_CODE_OFFSET + 2
        const val ERR_CODE_TIMEOUT = ERR_CODE_OFFSET + 3
        const val ERR_CODE_PARSE_DATA = ERR_CODE_OFFSET + 4

        fun getTimeoutMsg() = AppGlobal.getString(R.string.net_err_timeout)
        fun getConnectErrMsg() = AppGlobal.getString(R.string.net_err_connect)
        fun getParseDataErrMsg() = AppGlobal.getString(R.string.net_err_parse)
        fun getUnknownErrMsg() = AppGlobal.getString(R.string.net_err_parse)
    }

}
