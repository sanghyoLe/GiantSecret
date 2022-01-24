package com.example.giantsecret

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.giantsecret.databinding.ActivityMainBinding
import com.example.giantsecret.databinding.HeaderNavigationDrawerBinding
import com.example.giantsecret.viewModel.MviewModel
import com.example.giantsecret.viewModel.RoutineViewModel
import com.example.giantsecret.viewModel.RoutineViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    val mViewModel by viewModels<MviewModel>()
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController
    private lateinit var headerNavBinding: HeaderNavigationDrawerBinding




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Splash Screen 복구
        setTheme(R.style.Theme_GiantSecret)

        // InputUserDataFragment EditText is Empty
        mViewModel.showErrorToast.observe(this, Observer {
            it.getContentIfNotHandled()?.let {
                Toast.makeText(this, "모든 값을 입력해주세요", Toast.LENGTH_SHORT).show()
            }
        })

        mViewModel.showCompleteInputToast.observe(this, Observer {
            it.getContentIfNotHandled()?.let {
                Toast.makeText(this,mViewModel.inputBtnText,Toast.LENGTH_SHORT).show()
            }
        })

        // dataBinding , headerNavBinding 초기화
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        headerNavBinding = DataBindingUtil.inflate(layoutInflater, R.layout.header_navigation_drawer, binding.navigationView,
            false)



        init()
    }
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

    // 취소 버튼 눌렀을 시, DrawLayout Close
    override fun onBackPressed() {
        if(binding.drawerLayout.isDrawerOpen(GravityCompat.START)){
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
    fun init(){


        val topLevelDestinations:HashSet<Int> = object : HashSet<Int>()
        {
            init{
                add(R.id.inputUserDataFragment)
                add(R.id.programScheduleFragment)
                add(R.id.todayExerciseFragment)
            }
        }
        // Navigation UI를 Toolbar, NavigationView 연결
        navController = Navigation.findNavController(this, R.id.fragmentContainerView)

        // drawlayout에 있는 모든 Menu 항목을 상위 항목으로 설정
        appBarConfiguration = AppBarConfiguration(topLevelDestinationIds = topLevelDestinations,binding.drawerLayout)
        setSupportActionBar(binding.topAppBar)
        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.navigationView.setupWithNavController(navController)

        // navigationView에 navHeader 추가
        binding.navigationView.addHeaderView(headerNavBinding.root)


        // navHeader의 dataBinding을 위한 설정
        headerNavBinding.viewModel = mViewModel
        headerNavBinding.lifecycleOwner = this
    }




}


