package com.example.giantsecret.ui

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.activity.OnBackPressedCallback
import androidx.annotation.RequiresApi
import androidx.core.view.forEach
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.giantsecret.AppApplication
import com.example.giantsecret.R
import com.example.giantsecret.databinding.FragmentCreateRoutineBinding
import com.example.giantsecret.ui.adapter.ExerciseAdapter
import com.example.giantsecret.viewModel.ExerciseViewModel
import com.example.giantsecret.viewModel.ExerciseViewModelFactory
import com.google.android.material.chip.Chip


class CreateRoutineFragment : Fragment() {
    private lateinit var binding: FragmentCreateRoutineBinding
    private lateinit var callback: OnBackPressedCallback
    private var exerciseAdapter = ExerciseAdapter()
    private val exerciseViewModel: ExerciseViewModel by viewModels {
        ExerciseViewModelFactory((activity?.application as AppApplication).exerciseRepository)
    }
    private var x:Int = 1
    private var y:Int = 1


    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(layoutInflater,R.layout.fragment_create_routine, container, false)
        binding.routineChipGroup.forEach { child ->
            (child as? Chip)?.setOnCheckedChangeListener { _, _ ->
                registerFilterChange()
            }
        }
        binding.createRoutineBackBtn.setOnClickListener {
            findNavController().navigate(R.id.createRoutineToCloseAction)
        }

        binding.createExerciseBtnLayout.setOnClickListener {
            findNavController().navigate(R.id.createRoutineToCreateExercise)
        }

        binding.exerciseListRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.exerciseListRecyclerView.adapter = exerciseAdapter

        return binding.root
    }
    class ActivityLifeCycleObserver(private val update:() -> Unit) :
        DefaultLifecycleObserver {
        override fun onCreate(owner: LifecycleOwner) {
            super.onCreate(owner)
            owner.lifecycle.removeObserver(this)
            update()
        }
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController()?.navigate(R.id.createRoutineToCloseAction)
            }
        }

        activity?.lifecycle?.addObserver(ActivityLifeCycleObserver{
            exerciseViewModel.readAllData.observe(this) {
                    exerciseAdapter.setExercise(it)
            }
        })

        requireActivity().onBackPressedDispatcher.addCallback(this,callback)
    }

    override fun onDetach() {
        super.onDetach()
        callback.remove()
    }
    private fun registerFilterChange() {
        val ids = binding.routineChipGroup.checkedChipIds
        val partNames = mutableListOf<CharSequence>()
        ids.forEachIndexed { index, id ->
            partNames.add(index, binding.routineChipGroup.findViewById<Chip>(id).text)
        }
            // 체크된 name 가져오기
//            partNames.forEachIndexed { index, charSequence ->
//                Log.d("partNames",partNames.get(index).toString())
//            }
    }


}


