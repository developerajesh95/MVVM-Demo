package com.demo.mvvmdemo.data.dto

import com.demo.mvvmdemo.domain.model.User

data class UserDto(
    val email: String,
    val password: String,
    val username: String
)

fun UserDto.toUser(): User {
    return User(
        password = password,
        email = email,
        username = username
    )
}
