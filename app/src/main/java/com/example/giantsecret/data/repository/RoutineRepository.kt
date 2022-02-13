package com.example.giantsecret.data.repository

import com.example.giantsecret.data.dao.RoutineDao

import com.example.giantsecret.data.model.*

import kotlinx.coroutines.Dispatchers

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RoutineRepository @Inject constructor(
    private val routineDao:RoutineDao,
) {
    private val IoDispatchers = Dispatchers.IO

    val allRoutines: Flow<List<RoutineWithExerciseAndSets>>
        get() = routineDao.getAllRoutine()
    val allRoutineWithExerciseParts:Flow<List<RoutineWithExerciseParts>>
        get() = routineDao.getAllRoutineWithExerciseParts()



    suspend fun deleteRoutineWithChild(routine: Routine) {
        routineDao.deleteRoutineWithChild(routine)
    }

    suspend fun createRoutine(routine: Routine, exerciseWithSet: List<ExerciseWithSet>,isPartCheck:List<Boolean>):Long {
        return routineDao.createRoutine(routine,exerciseWithSet,isPartCheck)
    }
    suspend fun updateRoutineWithChild(routine: Routine, exerciseWithSet: List<ExerciseWithSet>, isPartCheck: List<Boolean>) {
        return routineDao.updateRoutineWithChild(routine,exerciseWithSet,isPartCheck)
    }

    suspend fun getRoutineExercisePartCrossRefByRoutineId(id:Long) : List<RoutineExercisePartCrossRef> {
        return routineDao.getRoutineExercisePartCrossRefByRoutineId(id)
    }


}