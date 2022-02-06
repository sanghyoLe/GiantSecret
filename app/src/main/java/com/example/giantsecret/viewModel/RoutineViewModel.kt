package com.example.giantsecret.viewModel


import androidx.hilt.Assisted
import androidx.lifecycle.*
import com.example.giantsecret.data.model.*
import com.example.giantsecret.data.model.ExerciseSet
import com.example.giantsecret.data.repository.ExerciseRepository
import com.example.giantsecret.data.repository.RoutineRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject





@HiltViewModel
class RoutineViewModel @Inject constructor(
    private val routineRepository: RoutineRepository,
    private val exerciseRepository: ExerciseRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel(){
    val allRoutines: LiveData<List<RoutineWithExerciseAndSets>> = routineRepository.allRoutines.asLiveData()


    // routine Value
    var clickedUpdateExerciseWithSetData:MutableList<ExerciseWithSet> = mutableListOf()
    var _clickedUpdateExerciseWithSetList: MutableLiveData<List<ExerciseWithSet>> = MutableLiveData()
    var clickedUpdateExerciseWithSetList: LiveData<List<ExerciseWithSet>> = _clickedUpdateExerciseWithSetList

    lateinit var routineWithExerciseAndSetsData: RoutineWithExerciseAndSets


    // exercise Value
    val exerciseWithSetFlow: LiveData<List<ExerciseWithSet>> = exerciseRepository.exerciseWithSetFlow.asLiveData()
    val exerciseFlow: LiveData<List<Exercise>> = exerciseRepository.exerciseFlow.asLiveData()


    lateinit var clickedUpdateExerciseWithSet: ExerciseWithSet

    var exerciseWithSetData: MutableList<ExerciseWithSet>  = mutableListOf()
    var _exerciseWithSetLiveData: MutableLiveData<List<ExerciseWithSet>> = MutableLiveData()
    val exerciseWithSetLiveData: LiveData<List<ExerciseWithSet>> = _exerciseWithSetLiveData


    fun addClickedUpdateExerciseWithSet(exerciseWithSet: ExerciseWithSet){
        clickedUpdateExerciseWithSetData.add(exerciseWithSet)
        _clickedUpdateExerciseWithSetList.value = clickedUpdateExerciseWithSetData
    }
    fun remove(exerciseWithSet: ExerciseWithSet){
        clickedUpdateExerciseWithSetData.remove(exerciseWithSet)
        _clickedUpdateExerciseWithSetList.value = clickedUpdateExerciseWithSetData
    }

    fun initAddGeneratedExercise(){
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

    fun clickCreateRoutineBtn(routine: Routine){
        createRoutine(
            routine,
            exerciseWithSetData.toList()
        )
    }
    fun clickUpdateRoutineBtn(routineWithExerciseAndSets: RoutineWithExerciseAndSets) {
        routineWithExerciseAndSetsData = routineWithExerciseAndSets
    }

    fun createRoutine(routine: Routine, exerciseWithSet: List<ExerciseWithSet>) {
        viewModelScope.launch {
           routineRepository.createRoutine(routine,exerciseWithSet)
        }
    }

    fun deleteRoutine(routine: Routine) = viewModelScope.launch {
        routineRepository.delete(routine)
    }

    // --------------------- Exercise ----------------------------- //


    fun updateExerciseWithSet(exercise: Exercise, exerciseSets:List<ExerciseSet>) {

        clickedUpdateExerciseWithSetData.forEachIndexed { index, exerciseWithSet ->
            run {
                if (exerciseWithSet.exercise.exerciseId == exercise.exerciseId) {
                    remove(exerciseWithSet)
                }
            }
        }
        viewModelScope.launch {
            removeExerciseWithSetData(clickedUpdateExerciseWithSet)
            remove(clickedUpdateExerciseWithSet)
            exercise.exerciseId = clickedUpdateExerciseWithSet.exercise.exerciseId
            clickedUpdateExerciseWithSet.exerciseSets.map{ it1 ->
                exerciseSets.map {
                    it.setId  = it1.setId
                }
            }
            addExerciseWithSetData(ExerciseWithSet(exercise,exerciseSets))
            addClickedUpdateExerciseWithSet(ExerciseWithSet(exercise,exerciseSets))

            exerciseRepository.updateExerciseWithSet(exercise,exerciseSets)
        }

    }


    fun getGeneratedExerciseList(): List<Exercise>{
        var exercises = mutableListOf<Exercise>()
        exerciseWithSetLiveData.value?.map {
            exercises.add(it.exercise)
        }
        return exercises.toList()
    }

    fun createExercise(exercise: Exercise, exerciseSets: List<ExerciseSet>):Long {
        var createdId: Long = 0
        viewModelScope.launch {
            createdId = exerciseRepository.createExercise(exercise, exerciseSets)
            addExerciseWithSetData(ExerciseWithSet(exercise,exerciseSets))
        }
        return createdId
    }





}

