package com.chiclaim.play.android

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
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

    private lateinit var drawerNavigationView: NavigationView
    private lateinit var bottomNavigation: BottomNavigationView
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var toolbar: Toolbar

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        drawerNavigationView = findViewById(R.id.drawer_navigation_view)
        bottomNavigation = findViewById(R.id.bottom_navigation)
        drawerLayout = findViewById(R.id.drawer_layout)
        toolbar = findViewById(R.id.toolbar)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.hostFragment) as NavHostFragment
        navController = navHostFragment.navController


        val actionBarDrawerToggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()

        bottomNavigation.setupWithNavController(navController)


        appBarConfiguration = AppBarConfiguration(navController.graph, drawerLayout)

        //NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration)

        // DrawerLayout 中的 NavigationView item 的点击和 BottomNavigationView 联动
        //NavigationUI.setupWithNavController(drawerNavigationView, navController)


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
            true
        }
        // 触发 OnNavigationItemSelectedListener 更新 title
        bottomNavigation.selectedItemId = R.id.navigation_home
    }


    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, appBarConfiguration)
    }

}
