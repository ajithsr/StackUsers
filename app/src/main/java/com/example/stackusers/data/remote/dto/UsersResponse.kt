package com.example.stackusers.data.remote.dto

import com.google.gson.annotations.SerializedName

data class UsersResponse(
    @SerializedName("items") val users: List<UserDto>
)
