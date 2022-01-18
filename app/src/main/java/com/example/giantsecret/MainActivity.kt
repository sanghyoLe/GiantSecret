package com.example.giantsecret

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.giantsecret.App.Companion.prefs

import com.example.giantsecret.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    private lateinit var mViewModel:MviewModel
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController
    private lateinit var navHeader:View
    private var isHaveInformation: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {



        super.onCreate(savedInstanceState)
        mViewModel = ViewModelProvider(this,
        ViewModelProvider.NewInstanceFactory()).get(MviewModel::class.java)

        mViewModel.showErrorToast.observe(this, Observer {
            it.getContentIfNotHandled()?.let {
                Toast.makeText(this, "텍스트를 입력하세요", Toast.LENGTH_SHORT).show()
            }
        })

        setTheme(R.style.Theme_GiantSecret)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.activity = this

    }
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        if(binding.drawerLayout.isDrawerOpen(GravityCompat.START)){
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
    fun init(){
        navHeader = binding.navigationView.getHeaderView(0)

        if(mViewModel.isHaveInformation()){
            navHeader.findViewById<TextView>(R.id.nickNameHeaderTextView).text = prefs.getValue("nickName")
            navHeader.findViewById<TextView>(R.id.oneRmHeaderTextView).text =
                """b: ${prefs.getValue("benchPressWeight")} s: ${prefs.getValue("squatWeight")} d: ${prefs.getValue("deadLeftWeight")} ohp: ${prefs.getValue("overHeadPressWeight")}
                """
        }

        val topLevelDestinations:HashSet<Int> = object : HashSet<Int>()
        {
            init{
                add(R.id.inputUserDataFragment)
                add(R.id.oneRmCalculatorFragment)
            }
        }
        navController = Navigation.findNavController(this, R.id.fragmentContainerView)
        appBarConfiguration = AppBarConfiguration(topLevelDestinationIds = topLevelDestinations,binding.drawerLayout)
        setSupportActionBar(binding.topAppBar)
        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.navigationView.setupWithNavController(navController)

    }




}


