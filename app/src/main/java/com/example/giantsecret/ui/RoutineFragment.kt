package com.example.giantsecret.ui
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleObserver


import androidx.navigation.fragment.findNavController


import com.example.giantsecret.R


import com.example.giantsecret.databinding.FragmentRoutineBinding
import com.example.giantsecret.viewModel.ExerciseViewModel


import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class RoutineFragment : Fragment(), LifecycleObserver {
    private lateinit var binding: FragmentRoutineBinding
    private val  exerciseViewModel: ExerciseViewModel by activityViewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_routine, container, false)


        binding.createRoutineBtnLayout.setOnClickListener {
            findNavController().navigate(R.id.createRoutineAction)
            exerciseViewModel.initAddGeneratedExercise()
//            val intent = Intent(activity?.applicationContext, CreateRoutineActivity::class.java)
//            startActivityForResult(intent, newRoutineActivityRequestCode)
        }

        return binding.root
    }


}