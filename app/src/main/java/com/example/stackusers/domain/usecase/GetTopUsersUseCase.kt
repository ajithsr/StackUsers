package com.example.stackusers.domain.usecase

import com.example.stackusers.domain.model.Result
import com.example.stackusers.domain.model.User
import com.example.stackusers.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetTopUsersUseCase @Inject constructor(
    private val repository: UserRepository
) {
    operator fun invoke(): Flow<Result<List<User>>> = flow {
        emit(Result.Success(emptyList())) // initial loading state
        val result = repository.getTopUsers()
        emit(result)
    }
}
