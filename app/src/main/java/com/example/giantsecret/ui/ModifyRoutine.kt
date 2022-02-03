package com.example.giantsecret.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.forEach
import androidx.core.view.forEachIndexed
import androidx.core.view.get
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.giantsecret.R
import com.example.giantsecret.data.model.Exercise
import com.example.giantsecret.data.model.ExerciseWithSet
import com.example.giantsecret.data.model.RoutineWithExercises
import com.example.giantsecret.databinding.FragmentCreateRoutineBinding
import com.example.giantsecret.ui.adapter.ExerciseAdapter
import com.example.giantsecret.viewModel.ExerciseViewModel
import com.example.giantsecret.viewModel.RoutineViewModel
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ModifyRoutine : Fragment() {
    private lateinit var binding: FragmentCreateRoutineBinding
    private lateinit var exerciseAdapter: ExerciseAdapter
    private val routineViewModel: RoutineViewModel by activityViewModels()
    private val exerciseViewModel: ExerciseViewModel by activityViewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        exerciseAdapter = ExerciseAdapter()
        var list:List<Exercise> = emptyList()
        Log.d("adDId",routineViewModel._addRoutineId.value.toString())
        routineViewModel.getRoutineWithExercise().map {
            Log.d("name",it.routine.name)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_create_routine, container, false)
        binding.topAppBarTitle.text = "루틴 수정하기"
        binding.exerciseListRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.exerciseListRecyclerView.adapter = exerciseAdapter
        return binding.root
    }


    fun observerModifyRoutine(){
        routineViewModel.modifyRoutine.observe(this) {

        }
    }

}