package com.example.giantsecret.ui.Record

import android.os.Bundle
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
import com.example.giantsecret.util.BaseFragment
import com.prolificinteractive.materialcalendarview.CalendarDay
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate

@AndroidEntryPoint
class CreateRecordFragment : BaseFragment<FragmentCreateRecordBinding>(FragmentCreateRecordBinding::inflate){

    private lateinit var routineAdapter: RoutineAdapter
    private var selectRoutineId:Long? = null
    private val routineViewModel: RoutineViewModel by activityViewModels()
    private val recordViewModel: RecordViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        routineAdapter = RoutineAdapter(
            ::deleteRoutine,
            ::modifyRoutine,
            ::selectRoutine,
            false
        )

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        addEventListeners()
        addObservers()
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
    private fun selectRoutine(routineWithExerciseAndSets: RoutineWithExerciseAndSets,partString:String) {
        showRoutineList()
        binding.routineNameTextView.text = routineWithExerciseAndSets.routine.name
        binding.exercisePartTextView.text = partString
        selectRoutineId = routineWithExerciseAndSets.routine.routineId
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
    private fun initView() {
        binding.routineRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.routineRecyclerView.adapter = routineAdapter

        if(!recordViewModel.isCreateRecordView) {
            binding.topAppBarTitle.text = "?????? ?????? ????????????"
            binding.saveBtn.text = "??????"
            binding.recordMemoEditText.setText(recordViewModel.modifyRecordData.memo)
            binding.recordDatePicker.updateDate(
                recordViewModel.modifyRecordData.date.year,
                recordViewModel.modifyRecordData.date.monthValue-1,
                recordViewModel.modifyRecordData.date.dayOfMonth
            )

            binding.routineNameTextView.text = recordViewModel.modifyRecordInRoutine.name
            binding.exercisePartTextView.text = recordViewModel.modifyRecordInPartString

            selectRoutineId = recordViewModel.modifyRecordInRoutine.routineId
            recordViewModel.selectLocalDate = recordViewModel.modifyRecordData.date

        } else if(recordViewModel.isProgressedEndRecordView) {
                binding.routineNameTextView.text = routineViewModel.progressedRoutine.routine.name

                binding.exercisePartTextView.text = routineViewModel.progressedPartString
                selectRoutineId = routineViewModel.progressedRoutine.routine.routineId

                binding.recordDatePicker.updateDate(
                    LocalDate.now().year,
                    LocalDate.now().monthValue-1,
                    LocalDate.now().dayOfMonth
                )
        } else {
            binding.recordDatePicker.updateDate(
                recordViewModel.selectedDay.year,
                recordViewModel.selectedDay.month-1,
                recordViewModel.selectedDay.day
            )
        }
    }
    private fun addEventListeners(){
        binding.routineCardView.setOnClickListener {
            showRoutineList()
        }
        binding.recordDatePicker.setOnDateChangedListener { _, year, month, day ->
            recordViewModel.selectedDay = CalendarDay.from(year,month,day)
            recordViewModel.selectLocalDate = LocalDate.of(year,month+1,day)
        }
        binding.backBtn.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.saveBtn.setOnClickListener {
            if(selectRoutineId == null){
                Toast.makeText(requireContext(),"????????? ???????????????",Toast.LENGTH_LONG).show()
            } else {
                if(recordViewModel.isCreateRecordView) {
                    var createRecordData = Record(0,
                        selectRoutineId!!,
                        recordViewModel.selectLocalDate,
                        binding.recordMemoEditText.text.toString()
                    )
                    recordViewModel.insertRecord(
                        createRecordData
                    )
                    recordViewModel.addSelectRecord(createRecordData)
                } else {
                    var updateRecordData = Record(
                        recordViewModel.modifyRecordData.recordId,
                        selectRoutineId!!,
                        recordViewModel.selectLocalDate,
                        binding.recordMemoEditText.text.toString()
                    )
                    recordViewModel.updateRecord(updateRecordData)

                    recordViewModel.updateSelectRecord(
                        updateRecordData
                    )

                }
                findNavController().navigate(R.id.RecordFragment)
            }
        }
    }
    private fun addObservers(){
        routineViewModel.allRoutines.observe(viewLifecycleOwner) {
            routineAdapter.setRoutine(it)
        }
        routineViewModel.allRoutineWithExerciseParts.observe(viewLifecycleOwner){
            routineAdapter.setRoutineWithExerciseParts(it)
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        recordViewModel.isCreateRecordView = false
        recordViewModel.isProgressedEndRecordView = false
    }
}
