package com.example.giantsecret.data.dao

import androidx.room.*
import com.example.giantsecret.data.model.Record
import com.example.giantsecret.data.model.RecordAndRoutine
import com.example.giantsecret.data.model.Routine
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate



@Dao
interface RecordDao {

    @Insert
    suspend fun insertRecord(record: Record)

    @Delete
    suspend fun deleteRecord(record: Record)

    @Update
    suspend fun updateRecord(record: Record)

    @Query("SELECT * FROM records")
    fun getRecordAllDate(): Flow<List<Record>>

    @Query("SELECT * FROM records,routine where recordInRoutineId = routineId")
    fun getRecordInRoutine() :Flow<List<Routine>>





}