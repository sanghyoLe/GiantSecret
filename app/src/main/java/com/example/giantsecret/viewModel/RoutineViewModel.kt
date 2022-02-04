package com.example.giantsecret.viewModel


import androidx.hilt.Assisted
import androidx.lifecycle.*
import com.example.giantsecret.data.model.*
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
    val allRoutines: LiveData<List<Routine>> = routineRepository.allRoutines.asLiveData()


    // routine Value
    var clickedUpdateExerciseWithSetData:MutableList<ExerciseWithSet> = mutableListOf()
    var _clickedUpdateExerciseWithSetList: MutableLiveData<List<ExerciseWithSet>> = MutableLiveData()
    var clickedUpdateExerciseWithSetList: LiveData<List<ExerciseWithSet>> = _clickedUpdateExerciseWithSetList

    val _clickedUpdateRoutine: MutableLiveData<RoutineWithExercises> = MutableLiveData()
    val clickedUpdateRoutine : LiveData<RoutineWithExercises> = _clickedUpdateRoutine

    // exercise Value
    val exerciseWithSetFlow: LiveData<List<ExerciseWithSet>> = exerciseRepository.exerciseWithSetFlow.asLiveData()
    val exerciseFlow: LiveData<List<Exercise>> = exerciseRepository.exerciseFlow.asLiveData()


    lateinit var clickedUpdateExerciseWithSet: ExerciseWithSet

    var generatedExerciseData: MutableList<ExerciseWithSet>  = mutableListOf()
    var _generatedExercise: MutableLiveData<List<ExerciseWithSet>> = MutableLiveData()
    val generatedExercise: LiveData<List<ExerciseWithSet>> = _generatedExercise


    fun addClickedUpdateExerciseWithSet(exerciseWithSet: ExerciseWithSet){
        clickedUpdateExerciseWithSetData.add(exerciseWithSet)
        _clickedUpdateExerciseWithSetList.value = clickedUpdateExerciseWithSetData
    }
    fun remove(exerciseWithSet: ExerciseWithSet){
        clickedUpdateExerciseWithSetData.remove(exerciseWithSet)
        _clickedUpdateExerciseWithSetList.value = clickedUpdateExerciseWithSetData
    }


    fun getRoutineWithExercisesById(id: Long) {
        viewModelScope.launch {
            _clickedUpdateRoutine.value = routineRepository.getRoutineWithExercisesById(id)
            _clickedUpdateRoutine.value!!.exercises.map {
                it.exerciseId?.let {
                    addClickedUpdateExerciseWithSet(exerciseRepository.getExerciseWithSetByParentId(it))
                }
            }
        }
    }

    fun createRoutine(routine: Routine, exercises: List<Exercise>) {
        var routineId: Long = 0
        viewModelScope.launch {
            routineId = routineRepository.createRoutine(routine,exercises)
        }
    }

    fun deleteRoutine(routine: Routine) = viewModelScope.launch {
        routineRepository.delete(routine)
    }

    // --------------------- Exercise ----------------------------- //


    fun initAddGeneratedExercise(){
        generatedExerciseData = mutableListOf()
        _generatedExercise.value = generatedExerciseData
    }

    fun addGeneratedExercise(exerciseWithSet: ExerciseWithSet){
        generatedExerciseData.add(exerciseWithSet)
        _generatedExercise.value = generatedExerciseData
    }

    fun removeGeneratedExercise(exerciseWithSet: ExerciseWithSet) {
        generatedExerciseData.remove(exerciseWithSet)
        _generatedExercise.value = generatedExerciseData
    }



    fun updateExerciseWithSet(exercise: Exercise, sets:List<ExerciseSet>) {

        clickedUpdateExerciseWithSetData.forEachIndexed { index, exerciseWithSet ->
            run {
                if (exerciseWithSet.exercise.exerciseId == exercise.exerciseId) {
                    remove(exerciseWithSet)
                }
            }
        }


        viewModelScope.launch {
            removeGeneratedExercise(clickedUpdateExerciseWithSet)
            remove(clickedUpdateExerciseWithSet)
            exercise.exerciseId = clickedUpdateExerciseWithSet.exercise.exerciseId
            clickedUpdateExerciseWithSet.sets.map{ it1 ->
                sets.map {
                    it.setId  = it1.setId
                }
            }
            addGeneratedExercise(ExerciseWithSet(exercise,sets))
            addClickedUpdateExerciseWithSet(ExerciseWithSet(exercise,sets))

            exerciseRepository.updateExerciseWithSet(exercise,sets)
        }

    }


    fun getGeneratedExerciseList(): List<Exercise>{
        var exercises = mutableListOf<Exercise>()
        generatedExercise.value?.map {
            exercises.add(it.exercise)
        }
        return exercises.toList()
    }



    fun getExerciseWithSetByParentId(id:Long) : ExerciseWithSet {
        lateinit var exerciseWithSet: ExerciseWithSet
        viewModelScope.launch {
            exerciseWithSet = exerciseRepository.getExerciseWithSetByParentId(id)
        }
        return exerciseWithSet
    }

    fun createExercise(exercise: Exercise, sets: List<ExerciseSet>):Long {
        var createdId: Long = 0
        viewModelScope.launch {
            createdId = exerciseRepository.createExercise(exercise, sets)
            addGeneratedExercise(exerciseRepository.getExerciseWithSetByParentId(createdId))
        }
        return createdId
    }



    fun insertExercise(exercise: Exercise) {
        viewModelScope.launch {
            exerciseRepository.insertExercise(exercise)
        }
    }


    fun insertSets(set: List<ExerciseSet>) = viewModelScope.launch {
        exerciseRepository.insertSets(set)
    }

    fun insertSet(set: ExerciseSet) = viewModelScope.launch {
        exerciseRepository.insertSet(set)
    }




}

