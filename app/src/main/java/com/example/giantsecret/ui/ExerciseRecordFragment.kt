package com.example.giantsecret.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.giantsecret.R
import com.example.giantsecret.databinding.FragmentCreateRecordBinding
import com.example.giantsecret.databinding.FragmentExerciseRecordBinding
import com.example.giantsecret.util.EventDecorator
import com.prolificinteractive.materialcalendarview.CalendarDay
import kotlin.random.Random


class ExerciseRecordFragment : Fragment() {
    private lateinit var binding:FragmentExerciseRecordBinding
    private lateinit var eventDecorator: EventDecorator
    private lateinit var dotColorList:Array<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dotColorList = arrayOf("#000000")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentExerciseRecordBinding.inflate(inflater,container,false)
        binding.calendarView.setOnDateChangedListener { widget, date, selected ->
        }

        eventDecorator = EventDecorator(requireContext(),dotColorList,CalendarDay.today(),2)
        binding.calendarView.addDecorator(eventDecorator)





        return binding.root
    }

}


