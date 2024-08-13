package com.demo.mvvmdemo.domain.repository

import com.demo.mvvmdemo.data.dto.UserDto
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun getUsers(): Flow<List<UserDto>>
}