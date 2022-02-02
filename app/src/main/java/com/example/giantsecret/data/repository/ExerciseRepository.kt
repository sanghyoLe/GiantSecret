package com.example.giantsecret.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.giantsecret.data.dao.ExerciseDao
import com.example.giantsecret.data.model.Exercise
import com.example.giantsecret.data.model.ExerciseSet
import com.example.giantsecret.data.model.ExerciseWithSet
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.properties.Delegates

class ExerciseRepository @Inject constructor(
    private val exerciseDao: ExerciseDao
) {


    private val IoDispatchers : CoroutineDispatcher = Dispatchers.IO

    val readAllData : LiveData<List<Exercise>> = exerciseDao.getExercise()
    val generatedExerciseWithSetList:MutableList<ExerciseWithSet> = mutableListOf()
    val _generatedExerciseWithSet: MutableLiveData<List<ExerciseWithSet>> = MutableLiveData()
    val generatedExerciseWithSet:LiveData<List<ExerciseWithSet>> =_generatedExerciseWithSet
    val generatedExerciseList:MutableList<Exercise> = mutableListOf()
    val _generatedExercise:MutableLiveData<List<Exercise>> = MutableLiveData()
    val generatedExercise:LiveData<List<Exercise>> = _generatedExercise
    val readExerciseData: List<ExerciseSet> = emptyList()




    fun addGeneratedExercise(exercise: Exercise){
        generatedExerciseList.add(exercise)
        _generatedExercise.value = generatedExerciseList
    }
    fun addGeneratedExerciseWithSet(exerciseWithSet: ExerciseWithSet) {
        generatedExerciseWithSetList.add(exerciseWithSet)
        _generatedExerciseWithSet.value = generatedExerciseWithSetList
    }



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
        exerciseDao.getExerciseSetByParentId(parentId)
    }



}