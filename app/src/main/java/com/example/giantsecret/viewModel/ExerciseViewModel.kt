package com.example.giantsecret.viewModel

import androidx.lifecycle.*
import com.example.giantsecret.lib.model.Exercise
import com.example.giantsecret.lib.model.ExerciseSet
import com.example.giantsecret.lib.model.ExerciseWithSet
import com.example.giantsecret.lib.repository.ExerciseRepository
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class ExerciseViewModel(private val exerciseRepository: ExerciseRepository) : ViewModel() {

    val readAllData: LiveData<List<Exercise>>
    val generatedExerciseData: LiveData<List<Exercise>>
    var readExerciseSetData: List<ExerciseSet> = emptyList()
    var generatedExerciseWithSet:LiveData<List<ExerciseWithSet>>




    init {
        readAllData = exerciseRepository.readAllData
        generatedExerciseData = exerciseRepository.generatedExercise
        readExerciseSetData = exerciseRepository.readExerciseData
        generatedExerciseWithSet = exerciseRepository.generatedExerciseWithSet
    }



    fun getExerciseById(id:Long){
        viewModelScope.launch {
            exerciseRepository.getExerciseById(id)
        }
    }

    fun createExercise(exercise: Exercise, sets: List<ExerciseSet>) {
        viewModelScope.launch {
            exerciseRepository.createExercise(exercise, sets)
        }
    }

    fun insertExercise(exercise: Exercise) {
        viewModelScope.launch {
            exerciseRepository.insertExercise(exercise)
        }
    }
    fun getExerciseSetByParentId(parentId:Long) {
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
    class ExerciseViewModelFactory(private val exerciseRepository: ExerciseRepository) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ExerciseViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return ExerciseViewModel(exerciseRepository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
