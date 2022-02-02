package com.example.giantsecret.viewModel

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.giantsecret.data.model.Exercise
import com.example.giantsecret.data.model.ExerciseSet
import com.example.giantsecret.data.model.ExerciseWithSet
import com.example.giantsecret.data.repository.ExerciseRepository
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ExerciseViewModel @Inject constructor(
    private val exerciseRepository: ExerciseRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    val readAllData: LiveData<List<Exercise>>
    val generatedExerciseData: LiveData<List<Exercise>>
    var readExerciseSetData: List<ExerciseSet> = emptyList()
    var generatedExerciseWithSet: LiveData<List<ExerciseWithSet>>


    init {
        readAllData = exerciseRepository.readAllData
        generatedExerciseData = exerciseRepository.generatedExercise
        readExerciseSetData = exerciseRepository.readExerciseData
        generatedExerciseWithSet = exerciseRepository.generatedExerciseWithSet

    }

    fun createExercise(exercise: Exercise, sets: List<ExerciseSet>) {
        viewModelScope.launch {
                exerciseRepository.createExercise(exercise, sets)
        }
    }
    fun getExerciseById(id: Long) {
        viewModelScope.launch {
            exerciseRepository.getExerciseById(id)
        }
    }


    fun insertExercise(exercise: Exercise) {
        viewModelScope.launch {
            exerciseRepository.insertExercise(exercise)
        }
    }

    fun getExerciseSetByParentId(parentId: Long) {
        viewModelScope.launch {
            exerciseRepository.getExerciseSetByParentId(parentId)
        }
    }

    fun insertSets(set: List<ExerciseSet>) = viewModelScope.launch {
        exerciseRepository.insertSets(set)
    }

    fun insertSet(set: ExerciseSet) = viewModelScope.launch {
        exerciseRepository.insertSet(set)
    }


}






