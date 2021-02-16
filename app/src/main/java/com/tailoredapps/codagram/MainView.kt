package com.tailoredapps.codagram

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.collection.arraySetOf
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.tailoredapps.codagram.databinding.ActivityMainBinding


class MainView : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val navController by lazy { (supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment).navController }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        binding.toolbar.setupWithNavController(navController, AppBarConfiguration(
            arraySetOf(        //alle 3 Views am selben Level
            R.id.firstView,
            R.id.secondView,
            R.id.thirdView
        )
        ))
        binding.bnvMain.setupWithNavController(navController)

    }

    override fun onSupportNavigateUp(): Boolean = navController.navigateUp()

    override fun onBackPressed() {
        if (!navController.popBackStack()) super.onBackPressed()
    }
}

