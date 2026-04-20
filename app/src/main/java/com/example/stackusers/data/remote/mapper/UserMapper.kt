package com.example.stackusers.data.remote.mapper

import com.example.stackusers.data.remote.dto.UserDto
import com.example.stackusers.domain.model.User

fun UserDto.toDomain(): User = User(
    userId = userId,
    displayName = displayName,
    reputation = reputation,
    profileImageUrl = profileImage
)
