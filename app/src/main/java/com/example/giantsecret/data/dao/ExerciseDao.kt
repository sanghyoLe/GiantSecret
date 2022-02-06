package com.example.giantsecret.data.dao

import androidx.room.*
import com.example.giantsecret.data.model.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ExerciseDao {
    // Exercise Dao
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRoutine(routine: Routine):Long

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createRoutine(routine: Routine, exerciseWithSet: List<ExerciseWithSet>) :Long {
        val routineId = insertRoutine(routine)
        insertRoutine(routine)
        exerciseWithSet.map { it ->
            it.exercise.apply { parentRoutineId = routineId }
            var exerciseId = insertExercise(it.exercise)!!
            it.exerciseSets.map {
                var setId = insertSet(it)
                insertExerciseSetCrossRef(
                    ExerciseSetCrossRef(exerciseId,setId)
                )
            }

        }

        return routineId
    }



    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExerciseSetCrossRef(exerciseSetCrossRef: ExerciseSetCrossRef)

    @Query("SELECT * FROM routine ORDER BY routineId DESC")
    fun getAllRoutine() : Flow<List<RoutineWithExerciseAndSets>>

    @Delete
    suspend fun deleteRoutine(routine: Routine)

    // for Exercise
    @Transaction
    @Query("SELECT * FROM exercises")
    fun getExercisesWithFlow(): Flow<List<ExerciseWithSet>>

    @Query("SELECT * FROM exercises")
    fun getAllExerciseFlow() : Flow<List<Exercise>>

    @Transaction
    @Query("SELECT * FROM exercises where exerciseId = :id")
    suspend fun getExerciseWithSetByParentId(id:Long) : ExerciseWithSet


    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createExercise(exercise: Exercise, exerciseSets: List<ExerciseSet>):Long{

        val exerciseId = insertExercise(exercise)
        exerciseSets.map {
            var setId = insertSet(it)
            insertExerciseSetCrossRef(
                ExerciseSetCrossRef(exerciseId,setId)
            )
        }
        return exerciseId
    }
    @Transaction
    @Delete
    suspend fun deleteExerciseWithSet(exercise: Exercise, exerciseSets: List<ExerciseSet>) {
        deleteExercise(exercise)

        exerciseSets.map {
            deleteSet(it)
        }
    }

    @Transaction
    @Update
    suspend fun updateExerciseWithSet(exercise: Exercise, exerciseSets: List<ExerciseSet>) {
        updateExercise(exercise)
        exerciseSets.map {
            updateSet(it)
        }
    }





    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExercise(exercise: Exercise):Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSet(exerciseSet:ExerciseSet) :Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSets(exerciseSet:List<ExerciseSet>)

    @Update
    suspend fun updateSet(exerciseSet: ExerciseSet)

    @Update
    suspend fun updateSets(exerciseSets: List<ExerciseSet>)

    @Update
    suspend fun updateExercise(exercise: Exercise)

    @Delete
    suspend fun deleteExercise(exercise: Exercise)

    @Delete
    suspend fun deleteSet(exerciseSet:ExerciseSet)




}