package com.example.giantsecret.data.repository

import com.example.giantsecret.data.dao.RecordDao
import com.example.giantsecret.data.model.Record
import com.example.giantsecret.data.model.Routine
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import javax.inject.Inject

class RecordRepository @Inject constructor(private val recordDao:RecordDao){

    val allRecord: Flow<List<Record>>
        get() = recordDao.getRecordAllDate()

    val allRecordInRoutine: Flow<List<Routine>>
        get() = recordDao.getRecordInRoutine()


    suspend fun insertRecord(record: Record) {
        recordDao.insertRecord(record)
    }
    suspend fun deleteRecord(record: Record) {
        recordDao.deleteRecord(record)
    }
    suspend fun updateRecord(record: Record) {
        recordDao.updateRecord(record)
    }


}