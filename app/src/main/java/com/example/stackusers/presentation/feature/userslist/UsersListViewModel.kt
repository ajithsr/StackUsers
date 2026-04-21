package com.example.stackusers.presentation.feature.userslist

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stackusers.domain.model.Result
import com.example.stackusers.domain.usecase.FollowUserUseCase
import com.example.stackusers.domain.usecase.GetTopUsersUseCase
import com.example.stackusers.domain.usecase.UnfollowUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UsersListViewModel @Inject constructor(
    private val getTopUsersUseCase: GetTopUsersUseCase,
    private val followUserUseCase: FollowUserUseCase,
    private val unfollowUserUseCase: UnfollowUserUseCase
) : ViewModel() {

    companion object {
        private const val TAG = "UsersListViewModel"
    }
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

    fun toggleFollow(userId: Long, isFollowing: Boolean) {
        Log.d(TAG, "toggleFollow -> userId $userId   isFollowing: $isFollowing")
        viewModelScope.launch {
            if (isFollowing) {
                unfollowUserUseCase(userId)
            } else {
                followUserUseCase(userId)
            }
        }
    }
}
