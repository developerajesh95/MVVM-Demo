package com.demo.mvvmdemo.data.repository

import com.demo.mvvmdemo.data.dto.UserDto
import com.demo.mvvmdemo.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UserRepositoryImpl : UserRepository {

    private var users = mutableListOf(
        UserDto("john@abc.com", "john123", "password123"),
        UserDto("mike@def.com", "mike789", "password789"),
        UserDto("paul@xyz.com", "paul01", "paulxyz01"),
        UserDto("jane@xyz.com", "jane456", "pass456")
    )

    override suspend fun getUsers(): Flow<List<UserDto>>  = flow {
        emit(users)
    }

}