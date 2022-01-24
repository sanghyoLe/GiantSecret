package com.example.giantsecret.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleObserver
import com.example.giantsecret.R
import com.example.giantsecret.databinding.FragmentProgramScheduleBinding

class RoutineFragment : Fragment(), LifecycleObserver {
    private lateinit var binding: FragmentProgramScheduleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_program_schedule, container, false)
        binding.lifecycleOwner = this


        return binding.root


    }






}