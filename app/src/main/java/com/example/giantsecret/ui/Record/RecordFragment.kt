package com.example.giantsecret.ui.Record


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.giantsecret.R


import com.example.giantsecret.databinding.FragmentRecordBinding
import com.example.giantsecret.ui.Routine.RoutineViewModel
import com.example.giantsecret.ui.adapter.RecordAdapter
import com.example.giantsecret.util.EventDecorator
import com.prolificinteractive.materialcalendarview.CalendarDay
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate

@AndroidEntryPoint
class RecordFragment : Fragment() {
    private lateinit var binding:FragmentRecordBinding
    private lateinit var eventDecorator: EventDecorator
    private lateinit var dotColorList:Array<String>
    private val routineViewModel: RoutineViewModel by activityViewModels()
    private val recordViewModel: RecordViewModel by activityViewModels()
    private lateinit var recordAdapter: RecordAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dotColorList = resources.getStringArray(R.array.dot_color_string_array)
        recordAdapter = RecordAdapter()


        recordViewModel.allRecord.observe(this){ list ->
            list.map { it ->
                val eventDecorator = EventDecorator(requireContext(),dotColorList,
                    CalendarDay.from(it.date.year,it.date.monthValue,it.date.dayOfMonth),
                    1
                )
                binding.calendarView.addDecorator(eventDecorator)
            }

            recordViewModel.allRecordInRoutine.observe(this){ routines  ->
                if(binding != null){
                    binding.calendarView.setOnDateChangedListener { widget, date, selected ->
                        recordViewModel.selectedDay = date
                        recordViewModel.selectLocalDate = LocalDate.of(date.year,date.month,date.day)
                        recordAdapter.setRecord(
                            list,
                            LocalDate.of(date.year,date.month,date.day),
                            routines
                        )
                    }
                }
            }

        }


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRecordBinding.inflate(inflater,container,false)
        binding.recordRecyclerView.adapter = recordAdapter
        binding.recordRecyclerView.layoutManager = LinearLayoutManager(requireContext())



//        eventDecorator = EventDecorator(requireContext(),dotColorList,CalendarDay.today(),2)
//        binding.calendarView.addDecorator(eventDecorator)

        binding.createRecordLayout.setOnClickListener {
            findNavController().navigate(R.id.createRecordFragment)
        }
//        recordViewModel.recordDateList.map {
//           binding.calendarView.addDecorator(EventDecorator(requireContext(),dotColorList,it,1))
//       }


        return binding.root
    }


}


