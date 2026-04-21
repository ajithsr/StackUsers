package com.example.stackusers.domain.usecase

import com.example.stackusers.domain.repository.UserRepository
import javax.inject.Inject

class FollowUserUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke(userId: Long) = repository.followUser(userId)
}
