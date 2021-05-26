package com.chiclaim.play.android.utils

import android.content.Context
import android.widget.Toast

/**
 *
 * @author by chiclaim@google.com
 */
object ToastUtil {

    private var toast: Toast? = null

    fun showShort(context: Context, msg: CharSequence) {
        show(context, msg, Toast.LENGTH_SHORT)
    }

    fun showShort(context: Context, strId: Int) {
        show(context, strId, Toast.LENGTH_SHORT)
    }

    fun showLong(context: Context, msg: CharSequence) {
        show(context, msg, Toast.LENGTH_LONG)
    }

    fun showLong(context: Context, strId: Int) {
        show(context, strId, Toast.LENGTH_LONG)
    }

    private fun show(context: Context, strId: Int, duration: Int) {
        if (toast == null) toast = Toast.makeText(context, strId, duration)
        toast?.show()
    }

    private fun show(context: Context, msg: CharSequence, duration: Int) {
        if (toast == null) toast = Toast.makeText(context, msg, duration)
        toast?.show()
    }

}