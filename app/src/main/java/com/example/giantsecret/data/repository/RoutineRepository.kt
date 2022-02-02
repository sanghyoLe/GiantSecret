package com.example.giantsecret.data.repository

import com.example.giantsecret.data.dao.RoutineDao
import com.example.giantsecret.data.model.Routine
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RoutineRepository @Inject constructor(
    private val routineDao:RoutineDao
) {

    val allRoutines: Flow<List<Routine>> = routineDao.getAllRoutines()



    suspend fun insert(routine: Routine) {
        routineDao.insertRoutine(routine)
    }
}