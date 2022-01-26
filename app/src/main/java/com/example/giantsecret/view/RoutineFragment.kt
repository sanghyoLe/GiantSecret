package com.example.giantsecret.view

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner

import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.giantsecret.R
import com.example.giantsecret.RoutineAdapter
import com.example.giantsecret.RoutineApplication

import com.example.giantsecret.databinding.FragmentRoutineBinding
import com.example.giantsecret.db.entity.Routine
import com.example.giantsecret.viewModel.RoutineViewModel
import com.example.giantsecret.viewModel.RoutineViewModelFactory

class RoutineFragment : Fragment(), LifecycleObserver {
    private lateinit var binding: FragmentRoutineBinding
    private val newRoutineActivityRequestCode = 1
    private lateinit var adapter: RoutineAdapter
    private val routineViewModel: RoutineViewModel by viewModels {
        RoutineViewModelFactory((activity?.application as RoutineApplication).repository)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_routine, container, false)
        binding.lifecycleOwner = this




        binding.createRoutineBtnLayout.setOnClickListener {
            findNavController().navigate(R.id.createRoutineAction)
//            val intent = Intent(activity?.applicationContext, CreateRoutineActivity::class.java)
//            startActivityForResult(intent, newRoutineActivityRequestCode)
        }
        adapter = RoutineAdapter()
        binding.routineRecyclerView.adapter = adapter
        binding.routineRecyclerView.layoutManager = LinearLayoutManager(activity?.applicationContext)

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
        activity?.lifecycle?.addObserver(ActivityLifeCycleObserver{
            routineViewModel.allRoutines.observe(this) { names ->
                names.let { adapter.submitList(it)}
            }
        })
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, intentData: Intent?) {
        super.onActivityResult(requestCode, resultCode, intentData)

        if (requestCode == newRoutineActivityRequestCode && resultCode == Activity.RESULT_OK) {
            intentData?.getStringExtra(CreateRoutineActivity.EXTRA_REPLY)?.let { reply ->
                val word = Routine(reply)
                routineViewModel.insert(word)
            }
        } else {
            Toast.makeText(
                activity?.applicationContext,
                "호호이",
                Toast.LENGTH_LONG
            ).show()
        }
    }
}