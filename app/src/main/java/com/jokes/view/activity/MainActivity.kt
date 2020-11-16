package com.jokes.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.jokes.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        handleNavigation()
    }

    private fun handleNavigation() {
        navController = Navigation.findNavController(this, R.id.main_navigation)
        bottomNavView.setupWithNavController(navController)
        appBarConfiguration = AppBarConfiguration(
            topLevelDestinationIds = setOf(
                R.id.categoriesFragment,
                R.id.searchJokesFragment,
                R.id.favoriteJokesFragment,
            )
        )
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration)
    }


    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController,null)
    }

}