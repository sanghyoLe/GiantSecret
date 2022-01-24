package com.example.giantsecret

import androidx.annotation.WorkerThread
import androidx.lifecycle.asLiveData
import com.example.giantsecret.db.dao.RoutineDao
import com.example.giantsecret.db.entity.Routine
import kotlinx.coroutines.flow.Flow

class RoutineRepository(private val routineDao: RoutineDao) {

    val allRoutines: Flow<List<Routine>> = routineDao.getAllRoutines()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(routine: Routine) {
        routineDao.insertRoutine(routine)
    }
}