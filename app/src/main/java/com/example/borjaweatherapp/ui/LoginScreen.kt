package com.example.borjaweatherapp.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.borjaweatherapp.BaseScreen
import com.example.borjaweatherapp.R
import com.example.borjaweatherapp.databinding.ActivityLoginScreenBinding
import com.example.borjaweatherapp.ui.dashboard.DashBoardScreen
import com.example.borjaweatherapp.ui.registration.RegistrationScreen
import com.example.borjaweatherapp.viewmodel.LoginScreenViewModel
import com.example.borjaweatherapp.viewmodelfactory.LoginScreenFactory

class LoginScreen : BaseScreen() {

    private lateinit var binding: ActivityLoginScreenBinding
    private lateinit var viewModel: LoginScreenViewModel
    private lateinit var userIdNum: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login_screen)

        this.viewModelStore.clear()
        viewModel =
            ViewModelProvider(
                this,
                LoginScreenFactory(this.application, this)
            ).get(LoginScreenViewModel::class.java)

        loginScreenFunctions()
        allViewModelMethods()
    }

    private fun allViewModelMethods() {
        // show error dialog
        viewModel.getErrorMessage().observe(this, Observer {
            Toast.makeText(
                this,
                it,
                Toast.LENGTH_SHORT
            ).show()
            binding.tieMpin.setText("")
        })

        viewModel.navigateToHomeScreen().observe(this, Observer {
            if (it == true) {
                binding.tieMpin.setText("")
                startActivity(Intent(applicationContext, DashBoardScreen::class.java))
            }
        })
    }

    fun loginClick(view: View) {
    }

    private fun loginScreenFunctions() {
        // sign up flow
        binding.btnSignUp.setOnClickListener {
            startActivity(Intent(applicationContext, RegistrationScreen::class.java))
        }

        // on entering m-pin for logging into the app
        binding.tieMpin.setOnPinEnteredListener { pin ->
            if (pin.length == 5) {
                // validate data
                userIdNum = binding.tieMobile.text.toString().trim()
                try {
                    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
                } catch (e: Exception) {
                    e.printStackTrace()
                }

                viewModel.validateInputData(
                    userIdNum,
                    pin
                )
            } else {
                binding.tieMpin.text = null
            }
        }
    }

    override fun onBackPressed() {
    }
}