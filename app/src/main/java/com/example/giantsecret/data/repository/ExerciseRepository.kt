package com.example.giantsecret.data.repository

import com.example.giantsecret.data.dao.ExerciseDao
import com.example.giantsecret.data.model.Exercise
import com.example.giantsecret.data.model.ExerciseSet
import com.example.giantsecret.data.model.ExerciseWithSet
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ExerciseRepository @Inject constructor(
    private val exerciseDao: ExerciseDao
) {


    private val IoDispatchers : CoroutineDispatcher = Dispatchers.IO


    val exerciseWithSetFlow: Flow<List<ExerciseWithSet>>
        get() = exerciseDao.getExercisesWithFlow()
    val exerciseFlow: Flow<List<Exercise>>
        get() = exerciseDao.getAllExerciseFlow()



    suspend fun insertExercise(exercise: Exercise): Long {
        return exerciseDao.insertExercise(exercise)
    }
    suspend fun createExercise(exercise: Exercise, exerciseSets: List<ExerciseSet>) :Long{
        return exerciseDao.createExercise(exercise,exerciseSets)
    }

    suspend fun updateExerciseWithSet(exercise: Exercise, exerciseSets: List<ExerciseSet>) {
            withContext(IoDispatchers) {
                    exerciseDao.updateExerciseWithSet(exercise,exerciseSets)
            }
    }


    suspend fun getExerciseWithSetByParentId(id:Long) : ExerciseWithSet {
        return exerciseDao.getExerciseWithSetByParentId(id)
    }




    suspend fun insertSets(exerciseSets: List<ExerciseSet>) {
        exerciseDao.insertSets(exerciseSets)
    }
    suspend fun insertSet(exerciseSet: ExerciseSet) {
        exerciseDao.insertSet(exerciseSet)
    }




}