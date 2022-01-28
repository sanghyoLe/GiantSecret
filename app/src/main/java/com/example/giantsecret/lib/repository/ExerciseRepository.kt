package com.example.giantsecret.lib.repository

import androidx.lifecycle.LiveData
import com.example.giantsecret.lib.dao.ExerciseDao
import com.example.giantsecret.lib.model.Exercise
import com.example.giantsecret.lib.model.ExerciseSet
import com.example.giantsecret.lib.model.ExerciseWithSet

class ExerciseRepository(private val exerciseDao: ExerciseDao) {

    val readAllData : LiveData<List<Exercise>> = exerciseDao.getExercise()

    var addId:Long = 0
    suspend fun insertExercise(exercise: Exercise):Long {
        addId = exerciseDao.insertExercise(exercise)
        return addId
    }
    suspend fun createExercise(exercise: Exercise, sets: List<ExerciseSet>) {
        val exerciseId = insertExercise(exercise)
        sets.map {
            it.apply { parentExerciseId = exerciseId }
        }.also { insertSets(it) }
    }

    suspend fun getAllExercises() :List<ExerciseWithSet> {
        return exerciseDao.getAllExercises()
    }

    suspend fun insertSets(sets: List<ExerciseSet>) {
        exerciseDao.insertSets(sets)
    }
    suspend fun insertSet(set: ExerciseSet) {
        exerciseDao.insertSet(set)
    }



}