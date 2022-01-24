package com.example.giantsecret.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import com.example.giantsecret.viewModel.MviewModel
import com.example.giantsecret.R
import com.example.giantsecret.databinding.FragmentInputUserDataBinding


class InputUserDataFragment : Fragment() {
    val mViewModel by activityViewModels<MviewModel>()
    private lateinit var binding: FragmentInputUserDataBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_input_user_data,container,false)
        binding.viewModel = mViewModel
        binding.lifecycleOwner = this



        return binding.root
    }

}