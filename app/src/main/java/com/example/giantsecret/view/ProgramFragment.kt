package com.example.giantsecret.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.giantsecret.R
import androidx.databinding.DataBindingUtil
import com.example.giantsecret.databinding.FragmentProgramBinding


private const val ARG_PROGRAM_WEEK = "programWeek"


class ProgramFragment : Fragment() {
    private lateinit var binding:FragmentProgramBinding

    private var programWeek: Int = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            programWeek = it.getInt(ARG_PROGRAM_WEEK)

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(layoutInflater,R.layout.fragment_program,container,false)

        return binding.root
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: Int) =
            ProgramFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PROGRAM_WEEK, param1)

                }
            }
    }

}