package com.example.stackusers.presentation.feature.userslist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stackusers.domain.model.Result
import com.example.stackusers.domain.usecase.GetTopUsersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class UsersListViewModel @Inject constructor(
    private val getTopUsersUseCase: GetTopUsersUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<Any>>(UiState.Loading)
    val uiState: StateFlow<UiState<Any>> = _uiState.asStateFlow()

    init {
        loadUsers()
    }

    fun loadUsers() {
        _uiState.value = UiState.Loading
        getTopUsersUseCase().onEach { result ->
            _uiState.value = when (result) {
                is Result.Success -> {
                    if (result.data.isEmpty()) {
                        UiState.Loading
                    } else {
                        UiState.Success(result.data)
                    }
                }

                is Result.Error -> UiState.Error(result.message)
            }
        }.catch { exc ->
            _uiState.value = UiState.Error(
                exc.message ?: "Unexpected error"
            )
        }.launchIn(viewModelScope)
    }

}
