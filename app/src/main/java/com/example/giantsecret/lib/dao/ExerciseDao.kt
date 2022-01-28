package com.example.giantsecret.lib.dao

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.giantsecret.lib.model.Exercise
import com.example.giantsecret.lib.model.ExerciseSet
import com.example.giantsecret.lib.model.ExerciseWithSet

@Dao
interface ExerciseDao {
    // Exercise Dao
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExercise(exercise: Exercise):Long

    @Delete
    suspend fun deleteExercise(exercise: Exercise)

    @Query("SELECT * FROM exercises")
    fun getExercise(): LiveData<List<Exercise>>

    @Transaction
    @Query("SELECT * FROM exercises")
    suspend fun getAllExercises() : List<ExerciseWithSet>

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createExercise(exercise: Exercise, sets: List<ExerciseSet>) {
        val exerciseId = insertExercise(exercise)
        sets.map {
            it.apply { parentExerciseId = exerciseId }
        }.also { insertSets(it) }
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSets(set:List<ExerciseSet>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSet(set: ExerciseSet)

}