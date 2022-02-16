package com.example.giantsecret.ui.Routine

import android.util.Log
import androidx.hilt.Assisted
import androidx.lifecycle.*
import com.example.giantsecret.data.model.*
import com.example.giantsecret.data.repository.ExerciseRepository
import com.example.giantsecret.data.repository.RoutineRepository
import com.example.giantsecret.ui.MainActivity
import com.example.giantsecret.ui.adapter.RoutineAdapter
import com.example.giantsecret.util.displayTime
import com.prolificinteractive.materialcalendarview.CalendarDay
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RoutineViewModel @Inject constructor(
    private val routineRepository: RoutineRepository,
    private val exerciseRepository: ExerciseRepository,

    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel(){
    lateinit var clickedExerciseSetData: ExerciseWithSet
    var clickedExerciseSetDataPosition: Int = 0
    var isCreateExerciseView: Boolean =  false


    // routine Value
    val allRoutines: LiveData<List<RoutineWithExerciseAndSets>> = routineRepository.allRoutines.asLiveData()
    val allRoutineWithExerciseParts: LiveData<List<RoutineWithExerciseParts>> = routineRepository.allRoutineWithExerciseParts.asLiveData()
    var routineWithExerciseAndSetsData: RoutineWithExerciseAndSets? = null
    var isCreateRoutineView: Boolean = false
    var isPartCheckByRoutineId:ArrayList<Boolean> = arrayListOf(false,false,false,false,false,false,false)

    lateinit var progressedRoutine: RoutineWithExerciseAndSets

    var progressedPartString = ""

    // exercise Value
    val parentIdNullExerciseFlow : LiveData<List<ExerciseWithSet>> = exerciseRepository.parentIdNullExerciseFlow.asLiveData()
    var exerciseWithSetData: MutableList<ExerciseWithSet>  = mutableListOf()
    var _exerciseWithSetLiveData: MutableLiveData<List<ExerciseWithSet>> = MutableLiveData()
    val exerciseWithSetLiveData: LiveData<List<ExerciseWithSet>> = _exerciseWithSetLiveData




    // Exercise,ExerciseSet Method
    fun initExerciseWithSetData(){
        exerciseWithSetData = mutableListOf()
        _exerciseWithSetLiveData.value = exerciseWithSetData
    }
    fun addExerciseWithSetData(exerciseWithSet: ExerciseWithSet){
        exerciseWithSetData.add(exerciseWithSet)
        _exerciseWithSetLiveData.value = exerciseWithSetData
    }
    fun removeExerciseWithSetData(exerciseWithSet: ExerciseWithSet) {
        exerciseWithSetData.remove(exerciseWithSet)
        _exerciseWithSetLiveData.value = exerciseWithSetData
    }
    fun updateExerciseWithSet(exerciseWithSet : ExerciseWithSet) {
        clickedExerciseSetData = exerciseWithSetData.get(clickedExerciseSetDataPosition)

        exerciseWithSet.exercise.exerciseId =
            clickedExerciseSetData.exercise.exerciseId

        exerciseWithSet.exercise.parentRoutineId =
            clickedExerciseSetData.exercise.parentRoutineId

        exerciseWithSet.exerciseSets.map {
            it.apply { parentExerciseId = exerciseWithSet.exercise.exerciseId }
        }


        if (routineWithExerciseAndSetsData == null) {
            viewModelScope.launch {
                exerciseRepository.updateExerciseWithSet(
                    exerciseWithSet.exercise, exerciseWithSet.exerciseSets
                )
            }
        }
        removeExerciseWithSetData(clickedExerciseSetData)
        exerciseWithSetData.add(clickedExerciseSetDataPosition, exerciseWithSet)
        _exerciseWithSetLiveData.value = exerciseWithSetData
    }
    fun createExercise(exercise: Exercise, exerciseSets: List<ExerciseSet>):Long {
        var createdId: Long = 0
        viewModelScope.launch {
            createdId = exerciseRepository.createExercise(exercise, exerciseSets)
            addExerciseWithSetData(ExerciseWithSet(exercise,exerciseSets))
        }
        return createdId
    }

    fun deleteExerciseWithSet(exercise: Exercise,exerciseSets: List<ExerciseSet>) {
        viewModelScope.launch {
            exerciseRepository.deleteExerciseWithSet(exercise,exerciseSets)
        }
    }
    fun deleteExerciseWithSetInRoutine(exercise: Exercise,exerciseSets: List<ExerciseSet>) {
        viewModelScope.launch {
            exerciseRepository.deleteExerciseWithSetInRoutine(exercise,exerciseSets)
        }
    }


    // routine Method
    fun clickCreateRoutineBtn(routine: Routine,isPartCheck:ArrayList<Boolean>){
        createRoutine(
            routine,
            exerciseWithSetData.toList(),
            isPartCheck
        )

    }
    fun clickShowUpdateRoutine(routineWithExerciseAndSets: RoutineWithExerciseAndSets) {
        initExerciseWithSetData()
        routineWithExerciseAndSetsData = routineWithExerciseAndSets
        viewModelScope.launch {
            exerciseRepository.getExerciseWithSetByRoutineId(
                routineWithExerciseAndSets.routine.routineId!!
            ).map { addExerciseWithSetData(it)
            }
            routineRepository.getRoutineExercisePartCrossRefByRoutineId(
                routineWithExerciseAndSets.routine.routineId
            ).map {
                var checkPartId = (it.partId-1).toInt()
                isPartCheckByRoutineId[checkPartId] = true
            }
        }
    }
    fun clickUpdateRoutineBtn(routine: Routine,isPartCheck: ArrayList<Boolean>) {
        Log.d("exerciseWithSetData",exerciseWithSetData.toString())
        updateRoutineWithChild(
            routine,
            exerciseWithSetData.toList(),
            isPartCheck
        )
    }
    fun updateRoutineWithChild(routine: Routine,exerciseWithSet: List<ExerciseWithSet>,isPartCheck:ArrayList<Boolean>){
        viewModelScope.launch {
            routineRepository.updateRoutineWithChild(
                routine,
                exerciseWithSet,
                isPartCheck
            )
        }
    }
    fun createRoutine(routine: Routine, exerciseWithSet: List<ExerciseWithSet>,isPartCheck:ArrayList<Boolean>) {
        viewModelScope.launch {
            routineRepository.createRoutine(routine,exerciseWithSet,isPartCheck)
        }
    }
    fun deleteRoutineWithChild(routine: Routine) = viewModelScope.launch {
        routineRepository.deleteRoutineWithChild(routine)
    }
}