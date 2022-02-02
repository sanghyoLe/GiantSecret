package com.example.giantsecret.viewModel



import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.giantsecret.data.repository.UserRepository
import com.example.giantsecret.data.model.User
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class UserViewModel(private val repository: UserRepository) : ViewModel(){

    val userData: User = repository.user

    fun insert(user: User) = viewModelScope.launch {
        repository.insert(user)
    }
}

class UserViewModelFactory(private val repository: UserRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(UserViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return UserViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}