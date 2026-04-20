package com.example.stackusers.data.remote.api

import com.example.stackusers.data.remote.dto.UsersResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface StackOverflowApi {

    @GET("users?order=desc&\n" +
            "sort=reputation&site=stackoverflow")
    suspend fun getTopUsers(
        @Query("page") page: Int = 1,
        @Query("pagesize") pageSize: Int = 20
    ): UsersResponse
}
