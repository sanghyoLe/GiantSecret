package com.example.giantsecret.data.repository

import com.example.giantsecret.data.dao.ExerciseDao
import com.example.giantsecret.data.dao.RoutineDao
import com.example.giantsecret.data.model.*

import kotlinx.coroutines.Dispatchers

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.properties.Delegates

class RoutineRepository @Inject constructor(
    private val routineDao:RoutineDao,
) {
    private val IoDispatchers = Dispatchers.IO

    val allRoutines: Flow<List<Routine>>
        get() = routineDao.getAllRoutine()

    suspend fun getRoutineWithExercise() :List<RoutineWithExercises> {
        return routineDao.getRoutineWithExercises()
    }

    suspend fun getRoutineWithExercisesById(id : Long) : RoutineWithExercises {
        return routineDao.getRoutineWithExerciseById(id)
    }

    suspend fun insertRoutine(routine: Routine):Long {
        return routineDao.insertRoutine(routine)
    }

    suspend fun delete(routine: Routine) {
        routineDao.deleteRoutine(routine)
    }

    suspend fun insertRoutineExerciseCrossRef(routineExerciseCrossRef: RoutineExerciseCrossRef) {
        routineDao.insertRoutineExerciseCrossRef(routineExerciseCrossRef)
    }

    suspend fun createRoutine(routine: Routine, exercises: List<Exercise>):Long {
        var routineId by Delegates.notNull<Long>()

        withContext(IoDispatchers) {
            routineId = insertRoutine(routine)
            exercises.map {
                it.exerciseId?.let {
                    insertRoutineExerciseCrossRef(RoutineExerciseCrossRef(routineId,it))
                }
            }
        }

        return routineId
    }
}