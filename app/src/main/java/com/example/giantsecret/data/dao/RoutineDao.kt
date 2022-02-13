package com.example.giantsecret.data.dao

import androidx.annotation.TransitionRes
import androidx.room.*
import com.example.giantsecret.data.model.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

@Dao
interface RoutineDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExercisePart(exercisePart: ExercisePart)

    // for Exercise
    @Transaction
    @Query("SELECT * FROM exercises")
    fun getExercisesWithFlow(): Flow<List<ExerciseWithSet>>

    @Transaction
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




    // Routine Method

    @Transaction
    @Query("SELECT * FROM routine ORDER BY routineId DESC")
    fun getAllRoutine() : Flow<List<RoutineWithExerciseAndSets>>

    @Transaction
    @Query("SELECT * FROM routine ORDER BY routineId DESC")
    fun getAllRoutineWithExerciseParts() :Flow<List<RoutineWithExerciseParts>>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRoutine(routine: Routine):Long

    @Update
    suspend fun updateRoutine(routine:Routine)

    @Delete
    suspend fun deleteRoutine(routine: Routine)

    @Transaction
    @Insert
    suspend fun insertRoutineExercisePartCrossRef(routineExercisePartCrossRef: RoutineExercisePartCrossRef)

    @Transaction
    @Delete
    suspend fun deleteRoutineExercisePartCrossRef(routineExercisePartCrossRef: RoutineExercisePartCrossRef)

    @Transaction
    @Query("SELECT * from routineexercisepartcrossref where routineId = :id")
    suspend fun getRoutineExercisePartCrossRefByRoutineId(id:Long):List<RoutineExercisePartCrossRef>


    @Transaction
    @Delete
    suspend fun deleteRoutineWithChild(routine: Routine) {
        deleteRoutine(routine)
        getExerciseWithSetByRoutineId(routine.routineId!!).map { exerciseWithSet ->
            deleteExerciseWithSet(
                exerciseWithSet.exercise,
                exerciseWithSet.exerciseSets
            )
        }
        getRoutineExercisePartCrossRefByRoutineId(routine.routineId!!).map {
            deleteRoutineExercisePartCrossRef(it)
        }
    }

    @Transaction
    @Update
    suspend fun updateRoutineWithChild(routine: Routine, exerciseWithSet: List<ExerciseWithSet>, isPartCheck: List<Boolean>){
        updateRoutine(routine)
        exerciseWithSet.map { it ->
            if(it.exercise.parentRoutineId == null){
                it.exercise.parentRoutineId = routine.routineId!!
                var exerciseId = insertExercise(it.exercise)
                it.exerciseSets.map {
                    it.parentExerciseId = exerciseId
                    insertSet(it)
                }
            } else {
                updateExerciseWithSet(it.exercise,it.exerciseSets)
            }
        }

        getRoutineExercisePartCrossRefByRoutineId(routine.routineId!!).map {
            deleteRoutineExercisePartCrossRef(it)
        }

        isPartCheck.forEachIndexed { index, check ->
            if(isPartCheck[index] == true ) {
                insertRoutineExercisePartCrossRef(
                    RoutineExercisePartCrossRef(routine.routineId!!,(index+1).toLong())
                )
            }
        }
    }

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createRoutine(routine: Routine, exerciseWithSet: List<ExerciseWithSet>,isPartCheck:List<Boolean>) :Long {
        val routineId = insertRoutine(routine)
        isPartCheck.forEachIndexed { index, check ->
            if(isPartCheck[index] == true ) {
                insertRoutineExercisePartCrossRef(
                    RoutineExercisePartCrossRef(routineId,(index+1).toLong())
                )
            }
        }
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

        return routineId
    }



}