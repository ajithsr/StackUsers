package com.example.stackusers.domain.usecase

import com.example.stackusers.domain.model.Result
import com.example.stackusers.domain.model.User
import com.example.stackusers.domain.repository.UserRepository
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class GetTopUsersUseCaseTest {

    private lateinit var repository: UserRepository
    private lateinit var useCase: GetTopUsersUseCase

    private val fakeUsers = listOf(
        User(
            userId = 1L,
            displayName = "Arun Xavier",
            reputation = 50000,
            profileImageUrl = "https://img/arun"
        ),
        User(
            userId = 2L,
            displayName = "Mark Peters",
            reputation = 40000,
            profileImageUrl = "https://img/mark"
        )
    )

    @Before
    fun setUp() {
        repository = mockk()
        useCase = GetTopUsersUseCase(repository)
    }

    @Test
    fun `usecase emits Success with users`() = runTest {
        coEvery { repository.getTopUsers() } returns Result.Success(fakeUsers)
        every { repository.getFollowedUserIds() } returns flowOf(emptySet())
        val results = useCase().toList()
        assertTrue(results[0] is Result.Success && (results[0] as Result.Success).data.isEmpty())
        assertEquals(Result.Success(fakeUsers), results[1])
    }

    @Test
    fun `followed users are set correctly`() = runTest {
        coEvery { repository.getTopUsers() } returns Result.Success(fakeUsers)
        every { repository.getFollowedUserIds() } returns flowOf(setOf(1L))

        val results = useCase().toList()
        val users = (results[1] as Result.Success).data
        assertTrue(users.first { it.userId == 1L }.isFollowed)
        assertTrue(!users.first { it.userId == 2L }.isFollowed)
    }

    @Test
    fun `usecase emits Error on failure`() = runTest {
        coEvery { repository.getTopUsers() } returns Result.Error("error")
        every { repository.getFollowedUserIds() } returns flowOf(emptySet())

        val results = useCase().toList()
        val error = results.last() as Result.Error
        assertEquals("error", error.message)
    }
}
