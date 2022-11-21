package com.colledk.mobilehelp.ui.start

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.colledk.mobilehelp.domain.model.User
import com.colledk.mobilehelp.domain.usecase.GetUserUseCase
import com.colledk.mobilehelp.domain.usecase.SaveUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    private val saveUserUseCase: SaveUserUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<MainScreenState>(MainScreenState())
    val uiState: StateFlow<MainScreenState> = _uiState.asStateFlow()

    init {
        getUser()
    }

    fun saveUser(user: User) {
        viewModelScope.launch {
            saveUserUseCase(user = user)
            getUser()
        }
    }

    private fun getUser() {
        viewModelScope.launch {
            val user = getUserUseCase()
            updateUiState(user = user)
        }
    }

    private fun updateUiState(
        user: User? = null
    ) {
        val currentState = _uiState.value
        _uiState.value = currentState.copy(
            currentUser = user ?: currentState.currentUser
        )
    }
}