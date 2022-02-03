package com.example.giantsecret.ui
import android.content.DialogInterface
import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog

import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import com.example.giantsecret.R
import com.example.giantsecret.databinding.ActivityMainBinding
import com.example.giantsecret.viewModel.MviewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    private lateinit var navController: NavController


    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Splash Screen 복구



        // dataBinding , headerNavBinding 초기화
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)


        init()
    }


    fun init(){
        // Navigation UI를 Toolbar, NavigationView 연결
        navController = Navigation.findNavController(this, R.id.fragmentContainerView)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.exerciseRecordFragment -> showBottomNav()
                R.id.statisticFragment -> showBottomNav()
                R.id.routineFragment -> showBottomNav()
                else -> hideBottomNav()
            }
        }

        binding.bottomNavigation.setupWithNavController(navController)



    }
    private fun showBottomNav() {
        binding.bottomNavigation.visibility = View.VISIBLE

    }

    private fun hideBottomNav() {
        binding.bottomNavigation.visibility = View.GONE

    }


}


