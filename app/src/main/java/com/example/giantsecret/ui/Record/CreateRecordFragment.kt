package com.example.giantsecret.ui.Record

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.giantsecret.R
import com.example.giantsecret.data.model.Record
import com.example.giantsecret.data.model.Routine
import com.example.giantsecret.data.model.RoutineWithExerciseAndSets
import com.example.giantsecret.databinding.FragmentCreateRecordBinding
import com.example.giantsecret.ui.adapter.RoutineAdapter
import com.example.giantsecret.ui.Routine.RoutineViewModel
import com.prolificinteractive.materialcalendarview.CalendarDay
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate

@AndroidEntryPoint
class CreateRecordFragment : Fragment() {
    private lateinit var binding:FragmentCreateRecordBinding
    private lateinit var routineAdapter: RoutineAdapter
    private var selectRoutineId:Long? = null
    private val routineViewModel: RoutineViewModel by activityViewModels()
    private val recordViewModel: RecordViewModel by activityViewModels()
    private val lifeCycleOwner:LifecycleOwner =  this
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        routineAdapter = RoutineAdapter(
            ::deleteRoutine,
            ::modifyRoutine,
            ::selectRoutine,
            false
        )

       routineViewModel.routineObserver(
           routineAdapter,
           lifeCycleOwner
       )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCreateRecordBinding.inflate(layoutInflater,container,false)

        binding.routineCardView.setOnClickListener {
            showRoutineList()
        }

        binding.routineRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.routineRecyclerView.adapter = routineAdapter
        binding.recordDatePicker.updateDate(
            recordViewModel.selectedDay.year,
            recordViewModel.selectedDay.month,
            recordViewModel.selectedDay.day
        )
        binding.recordDatePicker.setOnDateChangedListener { _, year, month, day ->

           recordViewModel.selectedDay = CalendarDay.from(year,month+1,day)
            recordViewModel.selectLocalDate = LocalDate.of(year,month+1,day)

        }
        binding.saveBtn.setOnClickListener {
            if(selectRoutineId == null){
                Toast.makeText(requireContext(),"루틴을 선택하세요",Toast.LENGTH_LONG).show()
            } else {
                recordViewModel.insertRecord(
                    Record(0,
                        selectRoutineId!!,
                        recordViewModel.selectLocalDate,
                        binding.recordMemoEditText.text.toString()
                        )
                )
                findNavController().popBackStack()
            }

        }

        return binding.root
    }

    private fun showRoutineList(){
        binding.routineRecyclerView.visibility  = if(binding.routineRecyclerView.visibility == View.VISIBLE){
            binding.recordMainLayout.visibility = View.VISIBLE
            View.GONE
        }
        else {
            binding.recordMainLayout.visibility = View.GONE
            View.VISIBLE
        }
    }
    private fun selectRoutine(routine: Routine,partString:String) {
        showRoutineList()
        binding.routineNameTextView.text = routine.name
        binding.exercisePartTextView.text = partString
        selectRoutineId = routine.routineId
    }
    private fun deleteRoutine(routine: Routine) {
        routineViewModel.deleteRoutineWithChild(routine)
        routineAdapter.notifyDataSetChanged()
    }
    private fun modifyRoutine(routine: RoutineWithExerciseAndSets) {
        routineViewModel.isPartCheckByRoutineId.replaceAll { false }
        routineViewModel.clickShowUpdateRoutine(routine)
        routineViewModel.isCreateRoutineView = false
        findNavController().navigate(R.id.createRoutineFragment)
    }




}