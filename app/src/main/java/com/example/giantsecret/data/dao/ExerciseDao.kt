package com.example.giantsecret.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.giantsecret.data.model.Exercise
import com.example.giantsecret.data.model.ExerciseSet
import com.example.giantsecret.data.model.ExerciseWithSet

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

    @Query("SELECT * FROM exercises where exerciseId = :id")
    suspend fun getExerciseById(id:Long):Exercise

    @Query("SELECT * FROM exerciseset where parentExerciseId = :parentId")
    suspend fun getExerciseSetByParentId(parentId: Long) : List<ExerciseSet>

    @Transaction
    @Query("SELECT * FROM exercises where exerciseId = :id")
    suspend fun getExerciseWithSetByParentId(id:Long) : ExerciseWithSet

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createExercise(exercise: Exercise, sets: List<ExerciseSet>):Long{
        val exerciseId = insertExercise(exercise)
        sets.map {
            it.apply { parentExerciseId = exerciseId }
        }.also { insertSets(it) }
        return exerciseId
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSets(set:List<ExerciseSet>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSet(set: ExerciseSet)

}