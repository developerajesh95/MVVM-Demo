package com.demo.mvvmdemo.presentation

import com.demo.mvvmdemo.domain.model.User

data class UserLoginState(
    val isLoading: Boolean = false,
    val user: User? = null,
    val errorMessage: String? = null,
)
