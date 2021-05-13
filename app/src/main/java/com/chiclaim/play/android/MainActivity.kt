package com.chiclaim.play.android

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.graphics.drawable.DrawerArrowDrawable
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.*
import com.chiclaim.play.android.base.BaseActivity
import com.chiclaim.play.android.funcs.user.login.LoginActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

/**
 * 主页面
 *
 * @author chiclaim@google.com
 */
class MainActivity : BaseActivity() {

    private lateinit var navigationView: NavigationView
    private lateinit var bottomNavigation: BottomNavigationView
    private lateinit var drawerLayout: DrawerLayout

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    private lateinit var indicatorController: IndicatorController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        navigationView = findViewById(R.id.drawer_navigation_view)
        bottomNavigation = findViewById(R.id.bottom_navigation)
        drawerLayout = findViewById(R.id.drawer_layout)

        indicatorController = IndicatorController.of(this)
        indicatorController.setActionBarUpIndicator(true)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.hostFragment) as NavHostFragment
        navController = navHostFragment.navController

        navigationView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_home -> {
                    startActivity(Intent(this, LoginActivity::class.java))
                }
            }
            return@setNavigationItemSelectedListener true
        }

        drawerLayout.addDrawerListener(object : DrawerLayout.DrawerListener {
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
            }

            override fun onDrawerOpened(drawerView: View) {
                indicatorController.setActionBarUpIndicator(false)
                Log.e("addDrawerListener", "onDrawerOpened")
            }

            override fun onDrawerClosed(drawerView: View) {
                indicatorController.setActionBarUpIndicator(true)
                Log.e("addDrawerListener", "onDrawerClosed")

            }

            override fun onDrawerStateChanged(newState: Int) {
                Log.e("addDrawerListener", "onDrawerStateChanged:$newState")
            }
        })

        bottomNavigation.setupWithNavController(navController)


        appBarConfiguration = AppBarConfiguration(navController.graph, drawerLayout)

        //NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration)

        // DrawerLayout 中的 NavigationView item 的点击和 BottomNavigationView 联动
        NavigationUI.setupWithNavController(navigationView, navController)


    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START)
            } else {
                drawerLayout.openDrawer(GravityCompat.START)
            }
            return true
        }
        return super.onOptionsItemSelected(item)
    }


    override fun onSupportNavigateUp(): Boolean {
        //return navController.navigateUp()
        return NavigationUI.navigateUp(navController, appBarConfiguration)

    }

}
