package com.hianuy.todolist.project.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.hianuy.todolist.R
import com.hianuy.todolist.databinding.ActivityMainBinding
import com.hianuy.todolist.project.ui.fragment.TaskFragmentArgs

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var toolbarConfiguration: AppBarConfiguration


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.fragmentContainer) as NavHostFragment
        navController = navHostFragment.navController
        toolbarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, toolbarConfiguration)
        listeningBackButton()


    }

    private fun listeningBackButton() =
//        navController.addOnDestinationChangedListener { controller, destination, arguments ->  }
        navController.addOnDestinationChangedListener { _, _, _ ->
            run {
                supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_arrow_back)

            }


        }


    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(toolbarConfiguration) || super.onSupportNavigateUp()

    }
}