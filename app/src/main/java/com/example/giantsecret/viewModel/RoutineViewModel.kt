package com.example.giantsecret.viewModel


import android.util.Log
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.giantsecret.data.model.ExerciseWithSet
import com.example.giantsecret.data.repository.RoutineRepository
import com.example.giantsecret.data.model.Routine
import com.example.giantsecret.data.model.RoutineExerciseCrossRef
import com.example.giantsecret.data.model.RoutineWithExercises
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException
import javax.inject.Inject





@HiltViewModel
class RoutineViewModel @Inject constructor(
    private val repository: RoutineRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel(){
    val allRoutines: LiveData<List<Routine>> = repository.allRoutines.asLiveData()



    val _modifyRoutine:MutableLiveData<Routine> = MutableLiveData()
    val modifyRoutine:LiveData<Routine> = _modifyRoutine

    var generatedExerciseData: MutableList<ExerciseWithSet>  = mutableListOf()
    var _generatedExercise: MutableLiveData<List<ExerciseWithSet>> = MutableLiveData()
    val generatedExercise: LiveData<List<ExerciseWithSet>> = _generatedExercise

    var _addRoutineId: MutableLiveData<Long> = MutableLiveData()
    var addRoutineId: LiveData<Long> = _addRoutineId

    fun initAddGeneratedExercise(){
        generatedExerciseData = mutableListOf()
        _generatedExercise.value = generatedExerciseData
    }

    fun addGeneratedExercise(exerciseWithSet: ExerciseWithSet){
        generatedExerciseData.add(exerciseWithSet)
        _generatedExercise.value = generatedExerciseData
    }
    fun getRoutineWithExercise():List<RoutineWithExercises> {
        var list:List<RoutineWithExercises> = emptyList()
        viewModelScope.launch {
            list = repository.getRoutineWithExercise()
        }
        return list
    }



    fun setModifyRoutine(routine: Routine){
        _modifyRoutine.value = routine
    }

    fun insertRoutineExerciseCrossRefs(routineExerciseCrossRef: RoutineExerciseCrossRef){
        viewModelScope.launch {
            repository.insertRoutineExerciseCrossRef(routineExerciseCrossRef)
        }
    }

     fun insertRoutine(routine: Routine) {
         viewModelScope.launch {
             val result = repository.insert(routine)
             _addRoutineId.value = result
             }
     }
    fun deleteRoutine(routine: Routine) = viewModelScope.launch {
        repository.delete(routine)
    }


}

