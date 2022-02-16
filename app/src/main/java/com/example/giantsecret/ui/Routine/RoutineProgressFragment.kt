package com.example.giantsecret.ui.Routine

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.*
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.giantsecret.R
import com.example.giantsecret.data.model.ExerciseWithSet
import com.example.giantsecret.data.model.RoutineWithExerciseAndSets
import com.example.giantsecret.databinding.FragmentRoutineProgressBinding
import com.example.giantsecret.ui.Record.RecordViewModel
import com.example.giantsecret.ui.adapter.ExerciseAdapter
import com.example.giantsecret.util.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.Long.parseLong


@AndroidEntryPoint
class RoutineProgressFragment : BaseFragment<FragmentRoutineProgressBinding>(FragmentRoutineProgressBinding::inflate), LifecycleObserver {

    private val routineViewModel: RoutineViewModel by activityViewModels()
    private val recordViewModel: RecordViewModel by activityViewModels()
    private var isStart = false
    private var startTime = 0L
    private var isNotPaused = true
    private var pauseTime: Long = 0L
    private var resumeTime: Long = 0L
    private lateinit var exerciseAdapter: ExerciseAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        exerciseAdapter = ExerciseAdapter(
            {ExerciseWithSet -> },
            {ExerciseWithSet, Int ->},
            true
        )

        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
        initView()
        addEventListeners()

    }





    private fun initView(){
        binding.routineNameTextView.text = routineViewModel.progressedRoutine.routine.name
        binding.partsTextView.text = routineViewModel.progressedPartString
        binding.exerciseRecyclerView.adapter = exerciseAdapter
        binding.exerciseRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        exerciseAdapter.setExerciseWithSet(
            routineViewModel.progressedRoutine.exercise
        )
    }
    private fun addEventListeners(){
        // 타이머 시작
        binding.startBtn.setOnClickListener {
            if(!isStart) {
                startTime = System.currentTimeMillis()
                startTimer(startTime)
                binding.startBtn.setImageResource(R.drawable.ic_baseline_pause_24)
            } else {
                if(isNotPaused) {
                    pauseTimer()
                    isNotPaused = !isNotPaused
                    binding.startBtn.setImageResource(R.drawable.ic_baseline_play_arrow_24)
                } else {
                    startTimer(resumeTime)
                    isNotPaused = !isNotPaused
                    binding.startBtn.setImageResource(R.drawable.ic_baseline_pause_24)
                }
            }
        }

        // 뒤로 가기
        binding.backBtn.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.endRoutineBtn.setOnClickListener {
            recordViewModel.isProgressedEndRecordView = true
            recordViewModel.isCreateRecordView = true
            findNavController().popBackStack()
            findNavController().navigate(R.id.createRecordFragment)
        }
    }

    companion object {
        private const val INTERVAL = 10L
    }


    fun startTimer(time:Long) {
        lifecycleScope.launch(Dispatchers.Main) {
            while (isNotPaused) {
                binding.timerView.text = (System.currentTimeMillis()-time).displayTime()
                delay(INTERVAL)
            }
        }
        isStart = true
    }
    fun pauseTimer() {

        pauseTime = (System.currentTimeMillis() - startTime)
        binding.timerView.text = pauseTime.displayTime()
        lifecycleScope.launch(Dispatchers.Main) {
            while(true) {
                resumeTime = System.currentTimeMillis() - pauseTime
                delay(INTERVAL)
            }
        }
    }




    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onAppBackgrounded() {
        val startIntent = Intent(requireActivity(), ForegroundService::class.java)
        startIntent.putExtra(COMMAND_ID, COMMAND_START)
        startIntent.putExtra(STARTED_TIMER_TIME_MS, startTime)
        activity?.startService(startIntent)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onAppForegrounded() {
        val stopIntent = Intent(requireActivity(), ForegroundService::class.java)
        stopIntent.putExtra(COMMAND_ID, COMMAND_STOP)
        activity?.startService(stopIntent)
    }



}
