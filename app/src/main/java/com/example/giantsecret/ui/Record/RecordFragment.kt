package com.example.giantsecret.ui.Record


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.giantsecret.R
import com.example.giantsecret.data.model.Record
import com.example.giantsecret.data.model.Routine
import com.example.giantsecret.data.model.RoutineWithExerciseAndSets


import com.example.giantsecret.databinding.FragmentRecordBinding
import com.example.giantsecret.ui.Routine.RoutineViewModel
import com.example.giantsecret.ui.adapter.RecordAdapter
import com.example.giantsecret.util.EventDecorator
import com.prolificinteractive.materialcalendarview.CalendarDay
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate
import java.util.*
import kotlin.collections.ArrayList

@AndroidEntryPoint
class RecordFragment : Fragment() {
    private lateinit var binding:FragmentRecordBinding
    private lateinit var eventDecorator: EventDecorator
    private lateinit var dotColorList:Array<String>
    private val routineViewModel: RoutineViewModel by activityViewModels()
    private val recordViewModel: RecordViewModel by activityViewModels()
    private var a :ArrayList<LocalDate> = arrayListOf()
    private lateinit var recordAdapter: RecordAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dotColorList = resources.getStringArray(R.array.dot_color_string_array)
        recordAdapter = RecordAdapter(
            ::modifyRecord,
            ::deleteRecord
        )

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRecordBinding.inflate(inflater, container, false)
        binding.recordRecyclerView.adapter = recordAdapter
        binding.recordRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        binding.calendarView.setDateSelected(recordViewModel.selectedDay,true)

        binding.createRecordLayout.setOnClickListener {
            recordViewModel.isCreateRecordView = true
            findNavController().navigate(R.id.createRecordFragment)
        }



            recordViewModel.allRecord.observe(viewLifecycleOwner) { list ->
                recordViewModel.allRecordList = list
                a = arrayListOf()
                binding.calendarView.removeDecorators()
                list.map { it ->
                    a.add(it.date)
                }
                list.map {
                    val eventDecorator = EventDecorator(
                        requireContext(), dotColorList,
                        CalendarDay.from(it.date.year, it.date.monthValue, it.date.dayOfMonth),
                        Collections.frequency(a,LocalDate.of(it.date.year,it.date.monthValue,it.date.dayOfMonth)).toInt()
                    )

                    binding.calendarView.addDecorator(eventDecorator)
                }
            }

            recordViewModel.allRecordInRoutine.observe(viewLifecycleOwner){
                recordAdapter.setRoutine(it)
            }

            binding.calendarView.setOnDateChangedListener { widget, date, selected ->
                recordViewModel.selectedDay = date
                recordViewModel.selectLocalDate = LocalDate.of(date.year, date.month, date.day)
                recordViewModel.updateSelectRecordList(LocalDate.of(date.year,date.month,date.day))
            }

            recordViewModel.selectRecordLiveData.observe(viewLifecycleOwner) {
                recordAdapter.setSelectRecord(it)
            }

            routineViewModel.allRoutineWithExerciseParts.observe(viewLifecycleOwner) {
                recordAdapter.setRoutineWithExerciseParts(it)
            }

            return binding.root
        }

        private fun deleteRecord(record: Record) {
            recordViewModel.deleteRecord(record)
            recordViewModel.deleteSelectRecord(record)
            recordAdapter.notifyDataSetChanged()
        }

        private fun modifyRecord(record: Record,routine:Routine,partString:String,position:Int) {
            recordViewModel.isCreateRecordView = false
            recordViewModel.modifyRecordData = record
            recordViewModel.modifyRecordInRoutine = routine
            recordViewModel.modifyRecordInPartString = partString
            recordViewModel.modifyRecordPosition = position
            findNavController().navigate(R.id.createRecordFragment)
        }

    }





