package com.example.giantsecret.data.repository

import androidx.room.Transaction
import androidx.room.Update
import com.example.giantsecret.data.dao.ExerciseDao
import com.example.giantsecret.data.model.Exercise
import com.example.giantsecret.data.model.ExerciseSet
import com.example.giantsecret.data.model.ExerciseWithSet
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.properties.Delegates

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
    suspend fun createExercise(exercise: Exercise, sets: List<ExerciseSet>) :Long{
        var exerciseId by Delegates.notNull<Long>()
        withContext(IoDispatchers){
            exerciseId = insertExercise(exercise)
            sets.map {
                it.apply { parentExerciseId = exerciseId }
            }.also { insertSets(it) }
        }
        return exerciseId
    }

    suspend fun updateExerciseWithSet(exercise: Exercise,sets: List<ExerciseSet>) {
            withContext(IoDispatchers) {
                    exerciseDao.updateExerciseWithSet(exercise,sets)
            }
    }


    suspend fun getExerciseWithSetByParentId(id:Long) : ExerciseWithSet {
        return exerciseDao.getExerciseWithSetByParentId(id)
    }




    suspend fun insertSets(sets: List<ExerciseSet>) {
        exerciseDao.insertSets(sets)
    }
    suspend fun insertSet(set: ExerciseSet) {
        exerciseDao.insertSet(set)
    }




}