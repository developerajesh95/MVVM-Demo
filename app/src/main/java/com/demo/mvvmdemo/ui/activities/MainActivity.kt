package com.demo.mvvmdemo.ui.activities

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import com.demo.mvvmdemo.R
import com.demo.mvvmdemo.data.repository.UserRepositoryImpl
import com.demo.mvvmdemo.databinding.ActivityMainBinding
import com.demo.mvvmdemo.domain.repository.UserRepository
import com.demo.mvvmdemo.ui.viewmodels.MainViewModel
import com.demo.mvvmdemo.ui.viewmodels.ViewModelFactory
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val repository = UserRepositoryImpl()
        val factory = ViewModelFactory(application, repository)
        viewModel = ViewModelProvider(this, factory)[MainViewModel::class.java]

        binding.loginButton.setOnClickListener {
            val emailOrUsername = binding.emailEditText.text.toString().trim()
            val password = binding.passwordEditText.text.toString().trim()
            if (emailOrUsername.isEmpty()) {
                binding.emailEditText.error = "Email or username required"
                return@setOnClickListener
            } else if (password.isEmpty()) {
                binding.passwordEditText.error = "Password required"
            } else {
                viewModel.loginUser(emailOrUsername, password)
                observeLogin()
            }
        }
    }

    private fun observeLogin() {
        lifecycleScope.launch {
            viewModel.currentUser.collect { state ->
                if (state.isLoading) {
                    binding.progressBar.visibility = View.VISIBLE
                } else if (state.user != null) {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(this@MainActivity, "Hello ${state.user.username}", Toast.LENGTH_SHORT).show()
                } else if (state.errorMessage != null) {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(this@MainActivity, state.errorMessage, Toast.LENGTH_SHORT).show()
                } else {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(this@MainActivity, "Something went wrong...!!!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}