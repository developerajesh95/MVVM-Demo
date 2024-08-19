package com.demo.mvvmdemo.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.demo.mvvmdemo.data.dto.UserDto
import com.demo.mvvmdemo.data.dto.toUser
import com.demo.mvvmdemo.domain.repository.UserRepository
import com.demo.mvvmdemo.presentation.UserLoginState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    application: Application,
    private val userRepository: UserRepository
) : AndroidViewModel(application) {

    private val _currentUser = MutableStateFlow(UserLoginState(isLoading = true))
    val currentUser: StateFlow<UserLoginState>
        get() = _currentUser.asStateFlow()

    fun loginUser(emailOrUsername: String, password: String) {
        viewModelScope.launch {
            userRepository.getUsers().collect { users ->
                if (users.isNotEmpty()) {
                    checkUser(users, emailOrUsername, password)
                } else {
                    _currentUser.value = UserLoginState(errorMessage = "Something went wrong...!!!")
                }
            }
        }
    }

    private fun checkUser(users: List<UserDto>, emailOrUsername: String, password: String) {
        var userData: UserDto? = null
        for (user in users) {
            if ((user.email == emailOrUsername || user.username == emailOrUsername) && user.password == password) {
                userData = user
            }
        }
        if (userData == null) {
            _currentUser.value = UserLoginState(errorMessage = "User not found...!!!")
        } else {
            _currentUser.value = UserLoginState(user = userData.toUser())
        }
    }

}