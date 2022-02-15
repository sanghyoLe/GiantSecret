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



@AndroidEntryPoint
class RoutineProgressFragment : Fragment(), LifecycleObserver {
    private lateinit var binding: FragmentRoutineProgressBinding
    private val routineViewModel: RoutineViewModel by activityViewModels()
    private val recordViewModel: RecordViewModel by activityViewModels()
    private var startTime = 0L
    private lateinit var exerciseAdapter: ExerciseAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startTime = System.currentTimeMillis()
        exerciseAdapter = ExerciseAdapter(
            {ExerciseWithSet -> },
            {ExerciseWithSet, Int ->},
            true
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)

        binding = FragmentRoutineProgressBinding.inflate(inflater,container,false)

        initView()
        return binding.root
    }



    private fun initView(){

        binding.routineNameTextView.text = routineViewModel.progressedRoutine.routine.name
        binding.partsTextView.text = routineViewModel.progressedPartString
        binding.exerciseRecyclerView.adapter = exerciseAdapter
        binding.exerciseRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        exerciseAdapter.setExerciseWithSet(
            routineViewModel.progressedRoutine.exercise
        )
        // 타이머 시작
        binding.startBtn.setOnClickListener {
            startTime = System.currentTimeMillis()
            binding.startBtn.setImageResource(R.drawable.ic_baseline_refresh_24)
            lifecycleScope.launch(Dispatchers.Main) {
                while (true) {
                    binding.timerView.text = (System.currentTimeMillis() - startTime).displayTime()
                    delay(INTERVAL)
                }
            }
        }
        // 뒤로 가기
        binding.backBtn.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.endRoutineBtn.setOnClickListener {
            recordViewModel.isCreateRecordView = true
            findNavController().popBackStack()
            findNavController().navigate(R.id.createRecordFragment)
        }
    }

    companion object {
        private const val INTERVAL = 10L
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