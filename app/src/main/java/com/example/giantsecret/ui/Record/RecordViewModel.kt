package com.example.giantsecret.ui.Record

import android.util.Log
import androidx.lifecycle.*
import com.example.giantsecret.data.model.Record
import com.example.giantsecret.data.model.Routine
import com.example.giantsecret.data.repository.RecordRepository
import com.prolificinteractive.materialcalendarview.CalendarDay
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class RecordViewModel @Inject constructor(
    private val recordRepository: RecordRepository
) : ViewModel(){
    val allRecord: LiveData<List<Record>> = recordRepository.allRecord.asLiveData()
    val allRecordInRoutine : LiveData<List<Routine>> = recordRepository.allRecordInRoutine.asLiveData()

    val recordDateList: List<CalendarDay> = listOf(
        CalendarDay.from(2022,2,2),
        CalendarDay.from(2022,2,1),
        CalendarDay.from(2022,2,3))
    var selectedDay: CalendarDay = CalendarDay.today()
    var _selectLocalDateLiveDate:MutableLiveData<LocalDate> = MutableLiveData()
    var selectLocalDateLiveDate:LiveData<LocalDate> = liveData {  }
    var selectLocalDate:LocalDate = LocalDate.now()

    fun insertRecord(record: Record){
        viewModelScope.launch {
            recordRepository.insertRecord(record)
        }
    }



}