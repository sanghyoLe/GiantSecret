package com.example.giantsecret.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import com.example.giantsecret.viewModel.MviewModel
import com.example.giantsecret.R
import com.example.giantsecret.databinding.FragmentStatisticBinding


class StatisticFragment : Fragment() {
    val mViewModel by activityViewModels<MviewModel>()
    private lateinit var binding: FragmentStatisticBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_statistic,container,false)
        binding.viewModel = mViewModel
        binding.lifecycleOwner = this



        return binding.root
    }

}