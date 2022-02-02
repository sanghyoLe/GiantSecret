package com.example.giantsecret.viewModel


import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.giantsecret.data.repository.RoutineRepository
import com.example.giantsecret.data.model.Routine
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

    suspend fun insertRoutine(routine: Routine) = viewModelScope.launch {
        repository.insert(routine)
    }


}

