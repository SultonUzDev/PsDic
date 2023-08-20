package com.uzdev.psdic

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.uzdev.psdic.data.preferences.PreferencesManager

class MainActivity : AppCompatActivity() {
    private lateinit var preferenceManager: PreferencesManager
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        preferenceManager = PreferencesManager(this)


        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment) as NavHostFragment
        val inflater = navHostFragment.navController.navInflater
        val graph = inflater.inflate(R.navigation.my_navigation)

        if (preferenceManager.isIntroduced) {
            graph.startDestination = R.id.listFragment
        } else {
            graph.startDestination = R.id.onBoardingFragment
        }

        navController = navHostFragment.navController
        navController.graph = graph
        setupActionBarWithNavController(navController)

    }

    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.fragment).navigateUp() || super.onSupportNavigateUp()
    }
}