package com.example.giantsecret.ui.Routine

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.giantsecret.R
import com.example.giantsecret.data.model.ExerciseWithSet
import com.example.giantsecret.databinding.FragmentExerciseListBinding
import com.example.giantsecret.ui.adapter.ExerciseAdapter
import com.example.giantsecret.util.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExerciseListFragment : BaseFragment<FragmentExerciseListBinding>(FragmentExerciseListBinding::inflate) {
    private val routineViewModel: RoutineViewModel by activityViewModels()
    lateinit var exerciseAdapter:ExerciseAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        exerciseAdapter = ExerciseAdapter(
            ::clickDeleteExercise,
            ::clickUpdateExercise,
            false
        )

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        routineViewModel.parentIdNullExerciseFlow.observe(viewLifecycleOwner) { allExercise ->
            exerciseAdapter.setExerciseWithSet(allExercise)
        }

        binding.createExerciseBtn.setOnClickListener {
            routineViewModel.isCreateExerciseView = true
            findNavController().navigate(R.id.createExerciseFragment)
        }
        binding.exerciseRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.exerciseRecyclerView.adapter = exerciseAdapter

    }

    private fun clickUpdateExercise(exerciseWithSet: ExerciseWithSet, position:Int) {
        routineViewModel.clickedExerciseSetDataPosition = position
        routineViewModel.isCreateExerciseView = false
        routineViewModel.updateExerciseWithSet(exerciseWithSet)
        findNavController().navigate(R.id.createExerciseFragment)
    }
    private fun clickDeleteExercise(exerciseWithSet: ExerciseWithSet) {
        routineViewModel.removeExerciseWithSetData(exerciseWithSet)
        routineViewModel.deleteExerciseWithSet(exerciseWithSet.exercise,exerciseWithSet.exerciseSets)

    }
}