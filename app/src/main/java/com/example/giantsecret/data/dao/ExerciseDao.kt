package com.example.giantsecret.data.dao

import androidx.room.*
import com.example.giantsecret.data.model.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ExerciseDao {
    @Query("SELECT * FROM routine ORDER BY routineId DESC")
    fun getAllRoutine() : Flow<List<RoutineWithExerciseAndSets>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRoutine(routine: Routine):Long

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createRoutine(routine: Routine, exerciseWithSet: List<ExerciseWithSet>,isPartCheck:List<Boolean>) :Long {
        val routineId = insertRoutine(routine)

        exerciseWithSet.map {
            it.exercise.parentRoutineId = routineId
            if(it.exercise.exerciseId != null) it.exercise.exerciseId = null
            it.exerciseSets.map {
                if(it.setId != null) it.setId = null
            }
            var exerciseId = insertExercise(it.exercise)
            it.exerciseSets.map {
                it.parentExerciseId = exerciseId
                insertSet(it)
            }
        }
        isPartCheck.forEachIndexed { index, check ->
            if(isPartCheck[index] == true ) {
                insertRoutineExercisePartCrossRef(
                    RoutineExercisePartCrossRef(routineId,(index+1).toLong())
                )
            }
        }
        return routineId
    }

    @Transaction
    @Query("SELECT * from routineexercisepartcrossref where routineId = :id")
    suspend fun getRoutineExercisePartCrossRefByRoutineId(id:Long):List<RoutineExercisePartCrossRef>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExercisePart(exercisePart: ExercisePart)

    @Delete
    suspend fun deleteRoutine(routine: Routine)



    @Transaction
    @Insert
    suspend fun insertRoutineExercisePartCrossRef(routineExercisePartCrossRef: RoutineExercisePartCrossRef)


    // for Exercise
    @Transaction
    @Query("SELECT * FROM exercises")
    fun getExercisesWithFlow(): Flow<List<ExerciseWithSet>>

    @Query("SELECT * FROM exercises where parentRoutineId IS NULL")
    fun getParentIdNullExerciseFlow() : Flow<List<ExerciseWithSet>>

    @Query("SELECT * FROM exercises")
    fun getAllExerciseFlow() : Flow<List<Exercise>>

    @Transaction
    @Query("SELECT * FROM exercises where exerciseId = :id")
    suspend fun getExerciseWithSetByParentId(id:Long) : ExerciseWithSet

    @Transaction
    @Query("SELECT * FROM exercises where parentRoutineId = :id")
    suspend fun getExerciseWithSetByRoutineId(id: Long) : List<ExerciseWithSet>



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


    @Transaction
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun createExercise(exercise: Exercise, exerciseSets: List<ExerciseSet>):Long{
        val exerciseId = insertExercise(exercise)
        exerciseSets.map {
            it.apply { parentExerciseId = exerciseId }
        }.also { insertSets(it) }
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





}