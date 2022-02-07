package com.example.giantsecret.ui.Routine

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.giantsecret.R
import com.example.giantsecret.data.model.ExerciseWithSet
import com.example.giantsecret.databinding.FragmentCreateRoutineBinding
import com.example.giantsecret.ui.adapter.ExerciseAdapter

import com.example.giantsecret.viewModel.RoutineViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class UpdateRoutineFragment : Fragment() {
    private lateinit var binding: FragmentCreateRoutineBinding
    private lateinit var exerciseAdapter: ExerciseAdapter
    private val routineViewModel: RoutineViewModel by activityViewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        exerciseAdapter = ExerciseAdapter(
            ::clickDeleteExercise,
            ::clickUpdateExercise
        )
        observerModifyRoutine()
        routineViewModel.exerciseWithSetLiveData.observe(this) { exercises ->
            exerciseAdapter.setExerciseWithSet(exercises)
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

    }

    fun clickUpdateExercise(exerciseWithSet: ExerciseWithSet,position:Int) {
        routineViewModel.clickedExerciseSetDataPosition = position
        routineViewModel.updateExerciseWithSet(exerciseWithSet)
        findNavController().navigate(R.id.updateExerciseFragment)
    }


    fun clickDeleteExercise(exerciseWithSet: ExerciseWithSet) {

    }

}