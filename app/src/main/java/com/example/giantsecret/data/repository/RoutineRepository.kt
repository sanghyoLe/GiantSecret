package com.example.giantsecret.data.repository

import com.example.giantsecret.data.dao.ExerciseDao

import com.example.giantsecret.data.model.*

import kotlinx.coroutines.Dispatchers

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.properties.Delegates

class RoutineRepository @Inject constructor(
    private val exerciseDao:ExerciseDao,
) {
    private val IoDispatchers = Dispatchers.IO

    val allRoutines: Flow<List<RoutineWithExerciseAndSets>>
        get() = exerciseDao.getAllRoutine()


    suspend fun insertRoutine(routine: Routine):Long {
        return exerciseDao.insertRoutine(routine)
    }

    suspend fun deleteRoutineWithChild(routine: Routine) {
        exerciseDao.deleteRoutineWithChild(routine)
    }

    suspend fun createRoutine(routine: Routine, exerciseWithSet: List<ExerciseWithSet>,isPartCheck:List<Boolean>):Long {
        return exerciseDao.createRoutine(routine,exerciseWithSet,isPartCheck)
    }
    suspend fun updateRoutineWithChild(routine: Routine, exerciseWithSet: List<ExerciseWithSet>, isPartCheck: List<Boolean>) {
        return exerciseDao.updateRoutineWithChild(routine,exerciseWithSet,isPartCheck)
    }

    suspend fun getRoutineExercisePartCrossRefByRoutineId(id:Long) : List<RoutineExercisePartCrossRef> {
        return exerciseDao.getRoutineExercisePartCrossRefByRoutineId(id)
    }

    suspend fun insertRoutineExercisePartCrossRef(routineExercisePartCrossRef: RoutineExercisePartCrossRef){
        return exerciseDao.insertRoutineExercisePartCrossRef(routineExercisePartCrossRef)
    }
}