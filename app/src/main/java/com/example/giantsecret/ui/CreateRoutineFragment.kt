package com.example.giantsecret.ui

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Button
import androidx.activity.OnBackPressedCallback
import androidx.annotation.RequiresApi
import androidx.core.view.forEach
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.giantsecret.BottomSheetListView
import com.example.giantsecret.R
import com.example.giantsecret.databinding.FragmentCreateRoutineBinding
import com.example.giantsecret.generated.callback.OnClickListener
import com.example.giantsecret.ui.adapter.ExerciseAdapter
import com.example.giantsecret.viewModel.ExerciseViewModel
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CreateRoutineFragment : Fragment() {
    private lateinit var binding: FragmentCreateRoutineBinding
    private lateinit var callback: OnBackPressedCallback
    private val exerciseViewModel: ExerciseViewModel by activityViewModels()

    private lateinit var searchDialog:SearchDialogFragment
    private lateinit var searchAdapter: SearchDialogFragment.SearchAdapter
    lateinit var exerciseAdapter:ExerciseAdapter

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        exerciseAdapter = ExerciseAdapter()
        searchAdapter = SearchDialogFragment.SearchAdapter()
        searchDialog = SearchDialogFragment(searchAdapter)



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
            var parts = registerFilterChange()
            var partString:String = ""


        }
        binding.createExerciseBtnLayout.setOnClickListener {
            findNavController().navigate(R.id.createRoutineToCreateExercise)
        }
        binding.searchExerciseBtnLayout.setOnClickListener {
            searchDialog.show(childFragmentManager,"SEARCH_DIALOG")
        }


    }


    private fun registerFilterChange(): MutableList<CharSequence> {
        val ids = binding.routineChipGroup.checkedChipIds
        val partNames = mutableListOf<CharSequence>()
        val partString:String = ""
        ids.forEachIndexed { index, id ->
            partNames.add(index, binding.routineChipGroup.findViewById<Chip>(id).text)
        }
        return partNames
    }
    private fun createRoutineObserver() {
        exerciseViewModel.generatedExercise.observe(this) { exercises ->
            exerciseAdapter.setExerciseWithSet(exercises)
        }
        exerciseViewModel.exerciseWithSetFlow.observe(this) { allExercise ->
            searchAdapter.setExercise(allExercise)
        }
    }



}


