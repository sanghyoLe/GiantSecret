package com.example.giantsecret.ui.Record


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.giantsecret.R
import com.example.giantsecret.data.model.Record
import com.example.giantsecret.data.model.Routine


import com.example.giantsecret.databinding.FragmentRecordBinding
import com.example.giantsecret.ui.Routine.RoutineViewModel
import com.example.giantsecret.ui.adapter.RecordAdapter
import com.example.giantsecret.util.BaseFragment
import com.example.giantsecret.util.EventDecorator
import com.prolificinteractive.materialcalendarview.CalendarDay
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate
import java.util.*
import kotlin.collections.ArrayList

@AndroidEntryPoint
class RecordFragment : BaseFragment<FragmentRecordBinding>(FragmentRecordBinding::inflate) {
    private lateinit var dotColorList:Array<String>
    private val routineViewModel: RoutineViewModel by activityViewModels()
    private val recordViewModel: RecordViewModel by activityViewModels()
    private var dateList :ArrayList<LocalDate> = arrayListOf()
    private lateinit var recordAdapter: RecordAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dotColorList = resources.getStringArray(R.array.dot_color_string_array)
        recordAdapter = RecordAdapter(
            ::modifyRecord,
            ::deleteRecord
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        addEventListeners()
        addObservers()
    }

        private fun initView() {
            binding.recordRecyclerView.adapter = recordAdapter
            binding.recordRecyclerView.layoutManager = LinearLayoutManager(requireContext())

            binding.calendarView.setDateSelected(recordViewModel.selectedDay,true)
        }
        private fun addEventListeners(){
            binding.createRecordLayout.setOnClickListener {
                recordViewModel.isCreateRecordView = true
                findNavController().navigate(R.id.createRecordFragment)
            }

            binding.calendarView.setOnDateChangedListener { widget, date, selected ->
                recordViewModel.selectedDay = date
                recordViewModel.selectLocalDate = LocalDate.of(date.year, date.month, date.day)
                recordViewModel.updateSelectRecordList(LocalDate.of(date.year,date.month,date.day))
            }
        }
        private fun addObservers() {
            recordViewModel.allRecord.observe(viewLifecycleOwner) { list ->
                recordViewModel.allRecordList = list
                dateList = arrayListOf()
                binding.calendarView.removeDecorators()
                list.map { it ->
                    dateList.add(it.date)
                }
                list.map {
                    val eventDecorator = EventDecorator(
                        requireContext(), dotColorList,
                        CalendarDay.from(it.date.year, it.date.monthValue, it.date.dayOfMonth),
                        Collections.frequency(dateList,LocalDate.of(it.date.year,it.date.monthValue,it.date.dayOfMonth)).toInt()
                    )

                    binding.calendarView.addDecorator(eventDecorator)
                }
            }

            recordViewModel.allRecordInRoutine.observe(viewLifecycleOwner){
                recordAdapter.setRoutine(it)
            }

            recordViewModel.selectRecordLiveData.observe(viewLifecycleOwner) {
                recordAdapter.setSelectRecord(it)
            }

            routineViewModel.allRoutineWithExerciseParts.observe(viewLifecycleOwner) {
                recordAdapter.setRoutineWithExerciseParts(it)
            }

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





