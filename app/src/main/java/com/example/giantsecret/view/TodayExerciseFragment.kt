package com.example.giantsecret.view

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.content.PackageManagerCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.*
import androidx.navigation.NavDeepLinkBuilder
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.giantsecret.R
import com.example.giantsecret.RoutineAdapter
import com.example.giantsecret.RoutineApplication
import com.example.giantsecret.db.entity.Routine
import com.example.giantsecret.viewModel.RoutineViewModel
import com.example.giantsecret.viewModel.RoutineViewModelFactory
import com.google.android.material.bottomnavigation.BottomNavigationView


class TodayExerciseFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_today_exercise, container, false )


        return view.rootView
    }


}


