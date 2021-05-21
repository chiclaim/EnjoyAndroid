package com.chiclaim.play.android.funcs.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.chiclaim.play.android.R
import com.chiclaim.play.android.base.BaseListFragment

/**
 *
 * @author by chiclaim@google.com
 */
class ArticleListFragment : BaseListFragment() {


    private var viewDataBinding: ViewDataBinding? = null

    override fun init(){
        viewDataBinding
    }

    override fun getLayoutId() = R.layout.fragment_home


}