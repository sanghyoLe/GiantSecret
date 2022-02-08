package com.example.giantsecret.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.fragment.app.activityViewModels
import com.example.giantsecret.R
import com.example.giantsecret.databinding.FragmentStatisticBinding


class StatisticFragment : Fragment() {


    private var _binding: FragmentStatisticBinding? = null

    private val binding get() = _binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentStatisticBinding.inflate(inflater,container,false)

        binding!!.root.let { rootView ->
            return rootView
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}