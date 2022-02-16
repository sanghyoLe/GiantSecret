package com.example.giantsecret.ui.Routine

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.giantsecret.R
import com.example.giantsecret.data.model.ExerciseWithSet
import com.example.giantsecret.data.model.Routine
import com.example.giantsecret.databinding.FragmentCreateRoutineBinding
import com.example.giantsecret.ui.Dialog.SearchDialogFragment
import com.example.giantsecret.ui.adapter.ExerciseAdapter
import com.example.giantsecret.util.BaseFragment
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CreateUpdateRoutineFragment : BaseFragment<FragmentCreateRoutineBinding>(FragmentCreateRoutineBinding::inflate) {
    private val routineViewModel : RoutineViewModel by activityViewModels()
    private lateinit var searchDialogFragment: SearchDialogFragment
    private lateinit var searchAdapter: SearchDialogFragment.SearchAdapter
    private var isCheckList:ArrayList<Boolean> = arrayListOf(false,false,false,false,false,false,false)
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

        binding.exerciseListRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.exerciseListRecyclerView.adapter = exerciseAdapter
        searchAdapter = SearchDialogFragment.SearchAdapter()
        searchDialogFragment = SearchDialogFragment(searchAdapter)

        if(!routineViewModel.isCreateRoutineView) {
            changeViewForUpdateRoutine()
        }
        addEventListeners()
        addObservers()
    }

    override fun onDestroy() {
        super.onDestroy()
        routineViewModel.isCreateRoutineView = false
    }


    private fun changeViewForUpdateRoutine(){
            binding.topAppBarTitle.text = "루틴 수정하기"
            binding.routineSaveBtn.text = "수정"
            binding.routineNameEditText.setText(
                routineViewModel.routineWithExerciseAndSetsData!!.routine.name
            )
            binding.chip1.isChecked = routineViewModel.isPartCheckByRoutineId.get(0)
            binding.chip2.isChecked = routineViewModel.isPartCheckByRoutineId.get(1)
            binding.chip3.isChecked = routineViewModel.isPartCheckByRoutineId.get(2)
            binding.chip4.isChecked = routineViewModel.isPartCheckByRoutineId.get(3)
            binding.chip5.isChecked = routineViewModel.isPartCheckByRoutineId.get(4)
            binding.chip6.isChecked = routineViewModel.isPartCheckByRoutineId.get(5)
            binding.chip7.isChecked = routineViewModel.isPartCheckByRoutineId.get(6)
    }
    private fun addEventListeners(){
        binding.createRoutineBackBtn.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.routineSaveBtn.setOnClickListener {
            var routineName:String? = binding.routineNameEditText.text.toString()
            if(!checkChipsEvent()){
                Toast.makeText(requireContext(),"운동 부위를 선택하세요",Toast.LENGTH_SHORT).show()
            } else if(routineName == null){
                Toast.makeText(requireContext(),"루틴 제목을 입력하세요",Toast.LENGTH_SHORT).show()
            } else {
                if(routineViewModel.isCreateRoutineView) {
                    var createdRoutine = Routine(0,routineName)
                    routineViewModel.clickCreateRoutineBtn(createdRoutine,isCheckList)
                } else {
                    var updateRoutineData =
                        Routine(routineViewModel.routineWithExerciseAndSetsData!!.routine.routineId,
                            routineName)
                    routineViewModel.clickUpdateRoutineBtn(updateRoutineData,isCheckList)
                }

                findNavController().popBackStack()
            }

        }
        binding.createExerciseBtnLayout.setOnClickListener {
            routineViewModel.isCreateExerciseView = true
            findNavController().navigate(R.id.createExerciseFragment)
        }
        binding.searchExerciseBtnLayout.setOnClickListener {

            if(!searchDialogFragment.isAdded)
                searchDialogFragment.show(childFragmentManager,"SEARCH_DIALOG")
        }

    }
    private fun checkChipsEvent(): Boolean {
        val chipGroup: ChipGroup = binding.root.findViewById(R.id.routineChipGroup) as ChipGroup
        var isCheckedPart: Boolean = false

            for (i: Int in 0 until chipGroup.childCount) {
                val child:View = chipGroup.getChildAt(i) as Chip
                if (child is Chip) {
                    if(child.isChecked) isCheckList[i] = true
                }
        }

        isCheckList.map {
            if(it) isCheckedPart = true
        }
        return isCheckedPart
    }
    private fun addObservers() {
        routineViewModel.exerciseWithSetLiveData.observe(viewLifecycleOwner) { exercises ->
            exerciseAdapter.setExerciseWithSet(exercises)
        }
        routineViewModel.parentIdNullExerciseFlow.observe(viewLifecycleOwner) { allExercise ->
            searchAdapter.setExercise(allExercise)
        }
    }

    private fun clickUpdateExercise(exerciseWithSet: ExerciseWithSet,position:Int) {
        routineViewModel.clickedExerciseSetDataPosition = position
        routineViewModel.isCreateExerciseView = false
        routineViewModel.updateExerciseWithSet(exerciseWithSet)
        findNavController().navigate(R.id.createExerciseFragment)
    }
    private fun clickDeleteExercise(exerciseWithSet: ExerciseWithSet) {
        routineViewModel.removeExerciseWithSetData(exerciseWithSet)
        routineViewModel.deleteExerciseWithSetInRoutine(
            exerciseWithSet.exercise,
            exerciseWithSet.exerciseSets
        )
    }



}






