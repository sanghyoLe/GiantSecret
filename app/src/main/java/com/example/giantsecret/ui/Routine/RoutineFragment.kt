package com.example.giantsecret.ui.Routine
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels



import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager


import com.example.giantsecret.R
import com.example.giantsecret.data.model.Routine
import com.example.giantsecret.data.model.RoutineWithExerciseAndSets
import com.example.giantsecret.databinding.FragmentRoutineBinding


import com.example.giantsecret.ui.adapter.RoutineAdapter


import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class RoutineFragment : Fragment(){
    private lateinit var binding: FragmentRoutineBinding

    private val routineViewModel: RoutineViewModel by activityViewModels()

    private lateinit var routineAdapter: RoutineAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        routineAdapter = RoutineAdapter(
            ::deleteRoutine,
            ::modifyRoutine,
            ::startRoutine,
            true
        )



    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        binding = FragmentRoutineBinding.inflate(inflater,container, false)
        binding.createRoutineBtnLayout.setOnClickListener {
            findNavController().navigate(R.id.createRoutineAction)
            routineViewModel.routineWithExerciseAndSetsData = null
            routineViewModel.isCreateRoutineView = true
            routineViewModel.initExerciseWithSetData()
        }
        binding.routineRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.routineRecyclerView.adapter = routineAdapter

        routineViewModel.allRoutines.observe(viewLifecycleOwner) {
            routineAdapter.setRoutine(it)

        }
        routineViewModel.allRoutineWithExerciseParts.observe(viewLifecycleOwner) {
            routineAdapter.setRoutineWithExerciseParts(it)

        }

        return binding.root

    }
    private fun deleteRoutine(routine: Routine) {
        routineViewModel.deleteRoutineWithChild(routine)
        routineAdapter.notifyDataSetChanged()
    }
    private fun modifyRoutine(routine: RoutineWithExerciseAndSets) {
        routineViewModel.isPartCheckByRoutineId.replaceAll { false }
        routineViewModel.clickShowUpdateRoutine(routine)
        routineViewModel.isCreateRoutineView = false
        findNavController().navigate(R.id.createRoutineFragment)
    }

    private fun startRoutine(routine:Routine, string: String) {
        routineViewModel._isProgressRoutineLiveData.value = true

    }


}