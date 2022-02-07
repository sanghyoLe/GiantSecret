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
import com.example.giantsecret.data.model.Routine
import com.example.giantsecret.data.model.RoutineWithExerciseAndSets


import com.example.giantsecret.databinding.FragmentRoutineBinding
import com.example.giantsecret.ui.adapter.RoutineAdapter
import com.example.giantsecret.viewModel.RoutineViewModel


import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class RoutineFragment : Fragment(){
    private lateinit var binding: FragmentRoutineBinding
    private val routineViewModel: RoutineViewModel by activityViewModels()
    private lateinit var routineAdapter: RoutineAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        routineAdapter = RoutineAdapter(::deleteRoutine,::modifyRoutine)
        routineObserver()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_routine, container, false)
        binding.createRoutineBtnLayout.setOnClickListener {
            findNavController().navigate(R.id.createRoutineAction)
            routineViewModel.routineWithExerciseAndSetsData = null
            routineViewModel.initExerciseWithSetData()
        }
        binding.routineRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.routineRecyclerView.adapter = routineAdapter


        return binding.root

    }
    private fun routineObserver() {
        routineViewModel.allRoutines.observe(this) {
            routineAdapter.setRoutine(it)
        }
    }
    private fun deleteRoutine(routine: Routine) {
        routineViewModel.deleteRoutine(routine)
        routineAdapter.notifyDataSetChanged()
    }
    private fun modifyRoutine(routine: RoutineWithExerciseAndSets) {
        routineViewModel.clickUpdateRoutineBtn(routine)
        findNavController().navigate(R.id.updateRoutineFragment)
    }
}