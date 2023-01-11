package com.colledk.mobilehelp.ui.start

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.colledk.mobilehelp.domain.model.User
import com.colledk.mobilehelp.domain.usecase.GetAllUsersUseCase
import com.colledk.mobilehelp.domain.usecase.GetUserUseCase
import com.colledk.mobilehelp.domain.usecase.ListenToUsersUseCase
import com.colledk.mobilehelp.domain.usecase.SaveUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    private val saveUserUseCase: SaveUserUseCase,
    private val getAllUsersUseCase: GetAllUsersUseCase,
    private val listenToUsersUseCase: ListenToUsersUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<MainScreenState>(MainScreenState())
    val uiState: StateFlow<MainScreenState> = _uiState.asStateFlow()

    init {
        getUser()
        getAllUsers()
        setupListener()
    }

    fun saveUser(user: User) {
        viewModelScope.launch {
            saveUserUseCase(user = user)
            getUser()
        }
    }

    private fun getUser() {
        viewModelScope.launch {
            val user = getUserUseCase("07BG4eImViP3RRpPV8jo")
            updateUiState(user = user)
        }
    }

    private fun getAllUsers() {
        viewModelScope.launch {
            val users = getAllUsersUseCase()
            updateUiState(users = users)
        }
    }

    private fun updateUiState(
        user: User? = null,
        users: List<User>? = null
    ) {
        val currentState = _uiState.value
        _uiState.value = currentState.copy(
            currentUser = user ?: currentState.currentUser,
            users = users ?: currentState.users
        )
    }

    private fun setupListener(){
        viewModelScope.launch {
            listenToUsersUseCase().collectLatest {
                updateUiState(users = it)
            }
        }
    }
}