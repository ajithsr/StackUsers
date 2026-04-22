package com.example.stackusers.data.repository

import com.example.stackusers.data.local.FollowLocalDataSource
import com.example.stackusers.data.remote.api.StackOverflowApi
import com.example.stackusers.data.remote.dto.UserDto
import com.example.stackusers.data.remote.dto.UsersResponse
import com.example.stackusers.domain.model.Result
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.io.IOException

class UserRepositoryImplTest {

    private lateinit var api: StackOverflowApi
    private lateinit var localDataSource: FollowLocalDataSource
    private lateinit var repository: UserRepositoryImpl

    private val fakeUserDtos = listOf(
        UserDto(
            userId = 1L,
            displayName = "Arun Xavier",
            reputation = 50000,
            profileImage = "https://img/arun"
        ),
        UserDto(
            userId = 2L,
            displayName = "Mark Peters",
            reputation = 40000,
            profileImage = "https://img/mark"
        )
    )

    @Before
    fun setUp() {
        api = mockk()
        localDataSource = mockk()
        repository = UserRepositoryImpl(api, localDataSource)
    }

    @Test
    fun `getTopUsers returns Success with users data on successful response`() = runTest {
        coEvery { api.getTopUsers(any(), any()) } returns UsersResponse(fakeUserDtos)

        val result = repository.getTopUsers()

        assertTrue(result is Result.Success)
        val users = (result as Result.Success).data
        assertEquals(2, users.size)
        assertEquals("Arun Xavier", users[0].displayName)
        assertEquals(50000, users[0].reputation)
        assertEquals("Mark Peters", users[1].displayName)
        assertEquals(40000, users[1].reputation)
    }

    @Test
    fun `getTopUsers returns Error on IOException`() = runTest {
        coEvery { api.getTopUsers(any(), any()) } throws IOException("No internet")

        val result = repository.getTopUsers()

        assertTrue(result is Result.Error)
        assertTrue((result as Result.Error).message.isNotEmpty())
    }

    @Test
    fun `getTopUsers returns Error on runtime exception`() = runTest {
        coEvery { api.getTopUsers(any(), any()) } throws RuntimeException("Server error")

        val result = repository.getTopUsers()

        assertTrue(result is Result.Error)
    }
}
