package com.chiclaim.play.android

import android.content.Intent
import android.os.Bundle
import android.util.SparseArray
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.util.forEach
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.ui.*
import com.chiclaim.play.android.base.BaseActivity
import com.chiclaim.play.android.funcs.article.ArticleFragment
import com.chiclaim.play.android.funcs.project.ProjectFragment
import com.chiclaim.play.android.funcs.user.MeFragment
import com.chiclaim.play.android.funcs.user.login.LoginActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

/**
 * 主页面
 *
 * @author chiclaim@google.com
 */
class MainActivity : BaseActivity() {

    private lateinit var drawerNavigationView: NavigationView
    private lateinit var bottomNavigation: BottomNavigationView
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var toolbar: Toolbar

    private var fragments = SparseArray<Fragment>().apply {
        put(R.id.navigation_home, ArticleFragment())
        put(R.id.navigation_project, ProjectFragment())
        put(R.id.navigation_me, MeFragment())
    }


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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        drawerNavigationView = findViewById(R.id.drawer_navigation_view)
        bottomNavigation = findViewById(R.id.bottom_navigation)
        drawerLayout = findViewById(R.id.drawer_layout)
        toolbar = findViewById(R.id.toolbar)

        if (savedInstanceState != null) {
            retrieveFragment(ArticleFragment::class.java.simpleName)
            retrieveFragment(ProjectFragment::class.java.simpleName)
            retrieveFragment(MeFragment::class.java.simpleName)
        }


        val actionBarDrawerToggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()


        drawerNavigationView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_home -> {
                    startActivity(Intent(this, LoginActivity::class.java))
                }
            }
            return@setNavigationItemSelectedListener true
        }

        bottomNavigation.setOnNavigationItemSelectedListener {
            toolbar.title = it.title
            switchFragment(fragments[it.itemId])
            true
        }
        // 触发 OnNavigationItemSelectedListener 更新 title
        bottomNavigation.selectedItemId = R.id.navigation_home
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
