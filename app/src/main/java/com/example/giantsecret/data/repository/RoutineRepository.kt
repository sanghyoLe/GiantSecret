package com.example.giantsecret.data.repository

import com.example.giantsecret.data.dao.RoutineDao

import com.example.giantsecret.data.model.Routine
import com.example.giantsecret.data.model.RoutineExerciseCrossRef
import com.example.giantsecret.data.model.RoutineWithExercises

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RoutineRepository @Inject constructor(
    private val routineDao:RoutineDao
) {

    val allRoutines: Flow<List<Routine>>
        get() = routineDao.getAllRoutine()


    suspend fun insert(routine: Routine):Long {
        return routineDao.insertRoutine(routine)
    }
    suspend fun getRoutineWithExercise() :List<RoutineWithExercises> {
        return routineDao.getRoutineWithExercises()
    }
    suspend fun insertRoutineExerciseCrossRef(routineExerciseCrossRef: RoutineExerciseCrossRef) {
        routineDao.insertRoutineExerciseCrossRef(routineExerciseCrossRef)
    }
    suspend fun delete(routine: Routine) {
        routineDao.deleteRoutine(routine)
    }
}