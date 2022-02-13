package com.example.giantsecret.data.repository

import com.example.giantsecret.data.dao.RoutineDao
import com.example.giantsecret.data.model.Exercise
import com.example.giantsecret.data.model.ExerciseSet
import com.example.giantsecret.data.model.ExerciseWithSet
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ExerciseRepository @Inject constructor(
    private val routineDao: RoutineDao
) {


    private val IoDispatchers : CoroutineDispatcher = Dispatchers.IO


    val exerciseWithSetFlow: Flow<List<ExerciseWithSet>>
        get() = routineDao.getExercisesWithFlow()
    val exerciseFlow: Flow<List<Exercise>>
        get() = routineDao.getAllExerciseFlow()
    val parentIdNullExerciseFlow :Flow<List<ExerciseWithSet>>
        get() = routineDao.getParentIdNullExerciseFlow()



    suspend fun createExercise(exercise: Exercise, exerciseSets: List<ExerciseSet>) :Long{
        return routineDao.createExercise(exercise,exerciseSets)
    }

    suspend fun getExerciseWithSetByRoutineId(id: Long) : List<ExerciseWithSet> {
        return routineDao.getExerciseWithSetByRoutineId(id)
    }

    suspend fun updateExerciseWithSet(exercise: Exercise, exerciseSets: List<ExerciseSet>) {
            withContext(IoDispatchers) {
                    routineDao.updateExerciseWithSet(exercise,exerciseSets)
            }
    }
}