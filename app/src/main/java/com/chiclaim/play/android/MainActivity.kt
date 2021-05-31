package com.chiclaim.play.android

import android.content.Intent
import android.os.Bundle
import android.util.SparseArray
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.util.containsKey
import androidx.core.util.forEach
import androidx.fragment.app.Fragment
import androidx.navigation.ui.*
import com.chiclaim.play.android.base.BaseBindingActivity
import com.chiclaim.play.android.databinding.ActivityMainBinding
import com.chiclaim.play.android.funcs.article.ArticleFragment
import com.chiclaim.play.android.funcs.project.ProjectFragment
import com.chiclaim.play.android.funcs.user.MeFragment
import com.chiclaim.play.android.funcs.user.login.LoginActivity

/**
 * 主页面
 *
 * @author chiclaim@google.com
 */
class MainActivity : BaseBindingActivity<ActivityMainBinding>() {

    companion object {
        const val CURRENT_POSITION = "currentPosition"
    }

    private var tabPosition = 0

    private var fragments = SparseArray<Fragment>(3)

    override fun getLayoutId() = R.layout.activity_main

    private fun retrieveFragment(tag: String) {
        when (val fragment =
            supportFragmentManager.findFragmentByTag(tag)) {
            is ArticleFragment -> {
                fragments.put(R.id.navigation_home, fragment)
            }
            is ProjectFragment -> {
                fragments.put(R.id.navigation_project, fragment)
            }
            is MeFragment -> {
                fragments.put(R.id.navigation_me, fragment)
            }
        }
    }

    private fun initFragments(savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            retrieveFragment(ArticleFragment::class.java.simpleName)
            retrieveFragment(ProjectFragment::class.java.simpleName)
            retrieveFragment(MeFragment::class.java.simpleName)
        }

        if (!fragments.containsKey(R.id.navigation_home))
            fragments.put(R.id.navigation_home, ArticleFragment())
        if (!fragments.containsKey(R.id.navigation_project))
            fragments.put(R.id.navigation_project, ProjectFragment())
        if (!fragments.containsKey(R.id.navigation_me))
            fragments.put(R.id.navigation_me, MeFragment())
    }

    override fun init(savedInstanceState: Bundle?) {
        super.init(savedInstanceState)
        if (savedInstanceState != null) {
            tabPosition = savedInstanceState.getInt(CURRENT_POSITION)
        }
        initFragments(savedInstanceState)

        val binding = requireDataBinding()

        val actionBarDrawerToggle = ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            binding.toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        binding.drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()


        binding.drawerNavigationView.setNavigationItemSelectedListener {
            when (it.itemId) {

                R.id.navigation_home -> {
                    startActivity(Intent(this, LoginActivity::class.java))
                }
            }
            return@setNavigationItemSelectedListener true
        }

        binding.bottomNavigation.setOnNavigationItemSelectedListener {
            binding.toolbar.title = it.title
            tabPosition = fragments.indexOfKey(it.itemId)
            switchFragment(fragments[it.itemId])
            true
        }

        // 首次 IU 加载，触发 OnNavigationItemSelectedListener 更新 title
        binding.bottomNavigation.selectedItemId = fragments.keyAt(tabPosition)
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(CURRENT_POSITION, tabPosition)
    }

    private fun switchFragment(fragment: Fragment) {
        if (fragment.isVisible) return
        fragments.forEach { _, _fragment ->
            if (_fragment.isVisible) {
                supportFragmentManager.beginTransaction()
                    .hide(_fragment)
                    .commitAllowingStateLoss()
            }
        }
        if (!fragment.isAdded) {
            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container_view, fragment, fragment.javaClass.simpleName)
                .commitAllowingStateLoss()
        } else {
            supportFragmentManager.beginTransaction()
                .show(fragment)
                .commitAllowingStateLoss()
        }
    }

}
