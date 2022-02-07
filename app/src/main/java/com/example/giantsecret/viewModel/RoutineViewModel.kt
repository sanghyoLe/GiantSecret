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



    var routineWithExerciseAndSetsData: RoutineWithExerciseAndSets? = null
    lateinit var exerciseWithSetByRoutineId:List<ExerciseWithSet>



    // exercise Value
    val exerciseWithSetFlow: LiveData<List<ExerciseWithSet>> = exerciseRepository.exerciseWithSetFlow.asLiveData()
    val exerciseFlow: LiveData<List<Exercise>> = exerciseRepository.exerciseFlow.asLiveData()
    val parentIdNullExerciseFlow : LiveData<List<ExerciseWithSet>> = exerciseRepository.parentIdNullExerciseFlow.asLiveData()




    var exerciseWithSetData: MutableList<ExerciseWithSet>  = mutableListOf()
    var _exerciseWithSetLiveData: MutableLiveData<List<ExerciseWithSet>> = MutableLiveData()
    val exerciseWithSetLiveData: LiveData<List<ExerciseWithSet>> = _exerciseWithSetLiveData

    var clickedExerciseSetDataPosition: Int = 0



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

    fun clickCreateRoutineBtn(routine: Routine){
        createRoutine(
            routine,
            exerciseWithSetData.toList()
        )
    }
    fun clickUpdateRoutineBtn(routineWithExerciseAndSets: RoutineWithExerciseAndSets) {
        initExerciseWithSetData()
        routineWithExerciseAndSetsData = routineWithExerciseAndSets
        viewModelScope.launch {
            exerciseRepository.getExerciseWithSetByRoutineId(
                routineWithExerciseAndSets.routine.routineId!!
            ).map {
                addExerciseWithSetData(it)
            }

        }
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


    fun updateExerciseWithSet(exerciseWithSet : ExerciseWithSet) {

            exerciseWithSet.exercise.exerciseId =
                exerciseWithSetData.get(clickedExerciseSetDataPosition).exercise.exerciseId

            exerciseWithSet.exercise.parentRoutineId =
                exerciseWithSetData.get(clickedExerciseSetDataPosition).exercise.parentRoutineId

            exerciseWithSet.exerciseSets.map {  currentSet ->
                exerciseWithSetData.get(clickedExerciseSetDataPosition).exerciseSets.map { beforeSet ->
                    currentSet.setId = beforeSet.setId
                    currentSet.parentExerciseId = beforeSet.parentExerciseId
                }
            }


        if(routineWithExerciseAndSetsData == null){
            viewModelScope.launch {
                exerciseRepository.updateExerciseWithSet(
                    exerciseWithSet.exercise,exerciseWithSet.exerciseSets
                )
            }
        }
        removeExerciseWithSetData(exerciseWithSetData.get(clickedExerciseSetDataPosition))
        exerciseWithSetData.add(clickedExerciseSetDataPosition,exerciseWithSet)
        _exerciseWithSetLiveData.value = exerciseWithSetData




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

