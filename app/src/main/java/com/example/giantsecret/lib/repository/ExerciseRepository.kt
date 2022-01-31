package com.example.giantsecret.lib.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.giantsecret.lib.dao.ExerciseDao
import com.example.giantsecret.lib.model.Exercise
import com.example.giantsecret.lib.model.ExerciseSet
import com.example.giantsecret.lib.model.ExerciseWithSet

class ExerciseRepository(private val exerciseDao: ExerciseDao) {


    val readAllData : LiveData<List<Exercise>> = exerciseDao.getExercise()

    var generatedExerciseWithSetList:MutableList<ExerciseWithSet> = mutableListOf()

    var _generatedExerciseWithSet: MutableLiveData<List<ExerciseWithSet>> = MutableLiveData()

    var generatedExerciseWithSet:LiveData<List<ExerciseWithSet>> =_generatedExerciseWithSet

    var generatedExerciseList:MutableList<Exercise> = mutableListOf()

    var _generatedExercise:MutableLiveData<List<Exercise>> = MutableLiveData()

    var generatedExercise:LiveData<List<Exercise>> = _generatedExercise

    var readExerciseData: List<ExerciseSet> = emptyList()




    fun addGeneratedExercise(exercise: Exercise){
        generatedExerciseList.add(exercise)
        _generatedExercise.value = generatedExerciseList
    }
    fun addGeneratedExerciseWithSet(exerciseWithSet: ExerciseWithSet) {
        generatedExerciseWithSetList.add(exerciseWithSet)
        Log.d("hi",exerciseWithSet.exercise.name)
        _generatedExerciseWithSet.value = generatedExerciseWithSetList
    }
    fun upDateReadExerciseData(set: List<ExerciseSet>) {
        readExerciseData = set
    }


    suspend fun insertExercise(exercise: Exercise): Long {
        return exerciseDao.insertExercise(exercise)
    }
    suspend fun createExercise(exercise: Exercise, sets: List<ExerciseSet>) :Long{
        val exerciseId = insertExercise(exercise)
        sets.map {
            it.apply { parentExerciseId = exerciseId }
        }.also { insertSets(it) }

        addGeneratedExercise(
            exerciseDao.getExerciseById(exerciseId)
        )
        addGeneratedExerciseWithSet(
            exerciseDao.getExerciseWithSetByParentId(exerciseId)
        )

        return exerciseId

    }

    suspend fun getExerciseById(id:Long) :Exercise{
        return exerciseDao.getExerciseById(id)
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
    suspend fun getExerciseSetByParentId(parentId:Long) {
        upDateReadExerciseData(exerciseDao.getExerciseSetByParentId(parentId))
    }



}