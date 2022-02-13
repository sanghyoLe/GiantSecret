package com.example.giantsecret.ui.Record

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

    var selectRecordData : MutableList<Record>  = mutableListOf()
    val _selectRecordLiveData : MutableLiveData<List<Record>> = MutableLiveData()
    val selectRecordLiveData : LiveData<List<Record>> = _selectRecordLiveData

    val _selectDateLiveData  : MutableLiveData<LocalDate> = MutableLiveData()
    val selectDateLiveData: LiveData<LocalDate> = _selectDateLiveData

    val recordDateList: List<CalendarDay> = listOf(
        CalendarDay.from(2022,2,2),
        CalendarDay.from(2022,2,1),
        CalendarDay.from(2022,2,3))
    var selectedDay: CalendarDay = CalendarDay.today()
    var selectLocalDate:LocalDate = LocalDate.now()

    fun init(){
        _selectDateLiveData.value = LocalDate.now()
    }
    fun insertRecord(record: Record){
        viewModelScope.launch {
            recordRepository.insertRecord(record)
        }
    }
    fun deleteRecord(record: Record) {
        viewModelScope.launch {
            recordRepository.deleteRecord(record)
        }
    }

    fun selectRecordDay(selectDate: LocalDate){
        selectRecordData = mutableListOf()
        allRecord.map {
            it.map {
                if(it.date == selectDate)
                    selectRecordData.add(it)
            }
        }
        _selectRecordLiveData.value = selectRecordData
    }




}