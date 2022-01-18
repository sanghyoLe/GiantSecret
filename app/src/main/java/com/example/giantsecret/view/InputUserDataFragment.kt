package com.example.giantsecret.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.giantsecret.MainActivity
import com.example.giantsecret.MviewModel
import com.example.giantsecret.R
import com.example.giantsecret.databinding.FragmentInputUserDataBinding


class InputUserDataFragment : Fragment() {
    private lateinit var mViewModel: MviewModel
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

            activity?.run{
                mViewModel = ViewModelProvider(this,
                ViewModelProvider.NewInstanceFactory()).get(MviewModel::class.java)
                binding.viewModel = mViewModel
                binding.lifecycleOwner = this
            }


        return binding.root
    }

}