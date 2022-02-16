package com.example.giantsecret.ui.Record

import android.util.Log
import androidx.lifecycle.*
import com.example.giantsecret.data.model.Record
import com.example.giantsecret.data.model.Routine
import com.example.giantsecret.data.repository.RecordRepository
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject
import kotlin.properties.Delegates

@HiltViewModel
class RecordViewModel @Inject constructor(
    private val recordRepository: RecordRepository
) : ViewModel(){

    var isCreateRecordView: Boolean = false
    var isProgressedEndRecordView: Boolean = false
    val allRecord: LiveData<List<Record>> = recordRepository.allRecord.asLiveData()
    val allRecordInRoutine : LiveData<List<Routine>> = recordRepository.allRecordInRoutine.asLiveData()

    var allRecordList : List<Record> = emptyList()

    var selectRecordData : MutableList<Record>  = mutableListOf()
    val _selectRecordLiveData : MutableLiveData<List<Record>> = MutableLiveData()
    val selectRecordLiveData : LiveData<List<Record>> = _selectRecordLiveData

    lateinit var modifyRecordData: Record
    lateinit var modifyRecordInRoutine: Routine
    lateinit var modifyRecordInPartString: String
    var modifyRecordPosition by Delegates.notNull<Int>()


    var selectedDay: CalendarDay = CalendarDay.today()
    var selectLocalDate:LocalDate = LocalDate.now()



    fun initSelectRecord() {
        selectRecordData = mutableListOf()
        _selectRecordLiveData.value = selectRecordData
    }
    fun addSelectRecord(record: Record){
        selectRecordData.add(record)
        _selectRecordLiveData.value = selectRecordData
    }
    fun deleteSelectRecord(record:Record) {
        selectRecordData.remove(record)
        _selectRecordLiveData.value = selectRecordData
    }
    fun updateSelectRecord(updatedRecord:Record) {
        selectRecordData.removeAt(modifyRecordPosition)
        selectRecordData.add(modifyRecordPosition,updatedRecord)
        _selectRecordLiveData.value = selectRecordData
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
    fun updateRecord(record: Record) {
        viewModelScope.launch {
            recordRepository.updateRecord(record)
        }
    }

    fun updateSelectRecordList(selectLocalDate:LocalDate){
        initSelectRecord()
        allRecordList.map {
            if(it.date == selectLocalDate)
                addSelectRecord(it)
        }
    }





}