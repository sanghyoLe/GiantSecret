package com.example.giantsecret.ui.Routine

import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.view.forEach
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.giantsecret.R
import com.example.giantsecret.data.model.ExerciseWithSet
import com.example.giantsecret.data.model.Routine
import com.example.giantsecret.databinding.FragmentCreateRoutineBinding
import com.example.giantsecret.ui.Dialog.SearchDialogFragment
import com.example.giantsecret.ui.adapter.ExerciseAdapter

import com.example.giantsecret.viewModel.RoutineViewModel
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CreateRoutineFragment : Fragment() {
    private lateinit var binding: FragmentCreateRoutineBinding


    private val routineViewModel : RoutineViewModel by activityViewModels()

    private lateinit var searchDialogFragment: SearchDialogFragment
    private lateinit var searchAdapter: SearchDialogFragment.SearchAdapter
    lateinit var exerciseAdapter:ExerciseAdapter

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        exerciseAdapter = ExerciseAdapter(
            ::clickDeleteExercise,
            ::clickUpdateExercise
            )
        searchAdapter = SearchDialogFragment.SearchAdapter()
        searchDialogFragment = SearchDialogFragment(searchAdapter)



        createRoutineObserver()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(layoutInflater,R.layout.fragment_create_routine, container, false)
        initView()

        binding.exerciseListRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.exerciseListRecyclerView.adapter = exerciseAdapter

        return binding.root
    }



    fun initView(){
        binding.routineChipGroup.forEach { child ->
            (child as? Chip)?.setOnCheckedChangeListener { _, _ ->
                registerFilterChange()
            }
        }
        binding.createRoutineBackBtn.setOnClickListener {
            findNavController().navigate(R.id.createRoutineToCloseAction)
        }

        binding.routineSaveBtn.setOnClickListener {
            var routineName:String? = binding.routineNameEditText.text.toString()
            if(registerFilterChange() == null){
                Toast.makeText(requireContext(),"운동 부위를 선택하세요",Toast.LENGTH_SHORT).show()
            } else if(routineName == null){
                Toast.makeText(requireContext(),"루틴 제목을 입력하세요",Toast.LENGTH_SHORT).show()
            } else {
//                var parts = registerFilterChange().toString()
//                parts = parts.substring(0, parts.length-1)

                var createdRoutine = Routine(null,routineName)
                routineViewModel.clickCreateRoutineBtn(createdRoutine)

                findNavController().navigate(R.id.createRoutineToCloseAction)
            }

        }
        binding.createExerciseBtnLayout.setOnClickListener {
            findNavController().navigate(R.id.createExerciseFragment)
        }
        binding.searchExerciseBtnLayout.setOnClickListener {
            if(!searchDialogFragment.isAdded)
                searchDialogFragment.show(childFragmentManager,"SEARCH_DIALOG")
        }


    }


    private fun registerFilterChange(): String? {
        val ids = binding.routineChipGroup.checkedChipIds
        var partString:String = ""

        ids.forEachIndexed { _, id ->
            partString = partString.plus(binding.routineChipGroup.findViewById<Chip>(id).text).plus(",")
        }

        return if(partString == ""){
            null
        } else {
            partString
        }
    }
    private fun createRoutineObserver() {
        routineViewModel.exerciseWithSetLiveData.observe(this) { exercises ->
            exerciseAdapter.setExerciseWithSet(exercises)
        }
        routineViewModel.exerciseWithSetFlow.observe(this) { allExercise ->
            searchAdapter.setExercise(allExercise)
        }
    }

    fun clickUpdateExercise(exerciseWithSet: ExerciseWithSet) {
        routineViewModel.clickedUpdateExerciseWithSet = exerciseWithSet
        findNavController().navigate(R.id.updateExerciseFragment)
    }
    fun clickDeleteExercise(exerciseWithSet: ExerciseWithSet) {

    }

}






