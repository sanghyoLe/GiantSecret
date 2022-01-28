package com.example.giantsecret.viewModel

import androidx.lifecycle.*
import com.example.giantsecret.lib.model.Exercise
import com.example.giantsecret.lib.model.ExerciseSet
import com.example.giantsecret.lib.repository.ExerciseRepository
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class ExerciseViewModel(private val exerciseRepository: ExerciseRepository) : ViewModel() {

    val readAllData: LiveData<List<Exercise>>
    val addId: Long

    init {
        readAllData = exerciseRepository.readAllData
        addId = exerciseRepository.addId
    }

    fun createExercise(exercise: Exercise, sets: List<ExerciseSet>) = viewModelScope.launch {
        exerciseRepository.createExercise(exercise,sets)
    }

    fun insertExercise(exercise: Exercise) = viewModelScope.launch {
        exerciseRepository.insertExercise(exercise)
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
