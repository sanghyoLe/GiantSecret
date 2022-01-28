package com.example.giantsecret.lib.repository

import androidx.annotation.WorkerThread
import com.example.giantsecret.lib.dao.RoutineDao
import com.example.giantsecret.lib.model.Routine
import kotlinx.coroutines.flow.Flow

class RoutineRepository(private val routineDao: RoutineDao) {

    val allRoutines: Flow<List<Routine>> = routineDao.getAllRoutines()



    suspend fun insert(routine: Routine) {
        routineDao.insertRoutine(routine)
    }
}