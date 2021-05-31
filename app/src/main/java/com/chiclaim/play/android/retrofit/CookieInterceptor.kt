package com.chiclaim.play.android.retrofit

import com.chiclaim.play.android.store.LightStore
import okhttp3.Interceptor
import okhttp3.Response

/**
 *  wanandroid.com 的开放 API 基于 Cookie 返回登陆信息，每次调用接口都需要带上登录时返回的 Cookie 信息
 *
 * @author chiclaim@google.com
 */
class CookieInterceptor : Interceptor {

    companion object {
        const val KEY_SET_COOKIE = "set-cookie"
        const val KEY_LOGIN_COOKIE = "login_cookie"
    }


    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val requestUrl = request.url.toString()
        // register or login api. eg: https://www.wanandroid.com/user/login
        val isLoginUrl = requestUrl.contains("user/login")
                || requestUrl.contains("user/register")
        if (isLoginUrl) {
            val response = chain.proceed(request)
            if (response.headers(KEY_SET_COOKIE).isNotEmpty()) {
                val cookies = response.headers(KEY_SET_COOKIE)
                val cookieStr = encodeCookie(cookies)
                // loginUserName/token_pass/....
                if (cookieStr.contains("loginUserName")) {
                    LightStore.putString(KEY_LOGIN_COOKIE, cookieStr)
                }
            }
            return response
        } else {
            val localCookieStr = LightStore.getString(KEY_LOGIN_COOKIE)
            if (localCookieStr?.isNotEmpty() == true) {
                val builder = request.newBuilder()
                builder.addHeader("Cookie", localCookieStr)
                return chain.proceed(builder.build())
            }
        }
        return chain.proceed(request)
    }


    private fun encodeCookie(cookies: List<String>): String {
        val sb = StringBuilder()
        val set = HashSet<String>()
        cookies
            .map { cookie ->
                cookie.split(";".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            }
            .forEach { it ->
                it.filterNot { set.contains(it) }.forEach { set.add(it) }
            }

        val ite = set.iterator()
        while (ite.hasNext()) {
            val cookie = ite.next()
            sb.append(cookie).append(";")
        }

        val last = sb.lastIndexOf(";")
        if (sb.length - 1 == last) {
            sb.deleteCharAt(last)
        }

        return sb.toString()
    }
}