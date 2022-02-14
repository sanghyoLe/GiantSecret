package com.example.giantsecret.ui
import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.activity.viewModels
import androidx.annotation.RequiresApi

import androidx.appcompat.app.AppCompatActivity

import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import com.example.giantsecret.R
import com.example.giantsecret.data.model.Routine
import com.example.giantsecret.databinding.ActivityMainBinding
import com.example.giantsecret.ui.Routine.RoutineViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    private lateinit var navController: NavController
    private val routineViewModel: RoutineViewModel by viewModels()
    private var time = 0
    private var isRunning = false
    private var timerTask: Timer? = null
    private var index :Int = 1


    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Splash Screen 복구

        // dataBinding , headerNavBinding 초기화
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
        addEventListener()
        routineViewModel.isProgressRoutineLiveData.observe(this){
            if(it == true) {
                binding.timerLayout.visibility = View.VISIBLE
            } else {
                binding.timerLayout.visibility = View.GONE
            }
        }
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
    private fun start() {
        binding.startBtn.setImageResource(R.drawable.ic_baseline_pause_24)

        timerTask = kotlin.concurrent.timer(period = 1) {
            time++
            val timeStr = finalTimeText(time)

            runOnUiThread {
                binding.timerText.text = timeStr
            }
        }
    }

    private fun pause() {
        binding.startBtn.setImageResource(R.drawable.ic_baseline_play_arrow_24)
        timerTask?.cancel();
    }

    private fun reset() {
        timerTask?.cancel()

        time = 0
        isRunning = false
        binding.startBtn.setImageResource(R.drawable.ic_baseline_play_arrow_24)
        binding.timerText.text = "00:00:00"

    }

    fun addEventListener() {
        binding.startBtn.setOnClickListener {
            isRunning = !isRunning
            if (isRunning) start() else pause()
        }
        binding.endRoutineBtn.setOnClickListener {
            routineViewModel._isProgressRoutineLiveData.value = false
            reset()
            binding.timerLayout.visibility = View.GONE
        }
    }
    fun finalTimeText(time: Int): String {
        var t = time

        val minute = t / (1000 * 60)
        t %= (1000*60)
        val sec = t / 1000


        return "${minute} : ${sec}"
    }


}


