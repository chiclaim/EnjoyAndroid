package com.chiclaim.play.android.funcs.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.chiclaim.play.android.R
import com.chiclaim.play.android.base.BaseFragment

/**
 *
 * @author by chiclaim@google.com
 */
class MeFragment : BaseFragment() {


    private lateinit var homeViewModel: MeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProvider(this).get(MeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_tab_me, container, false)
        val textView: TextView = root.findViewById(R.id.text_me)
        homeViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }


    override fun onResume() {
        super.onResume()
    }
}