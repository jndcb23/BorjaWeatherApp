package com.example.borjaweatherapp.ui.registration

import android.content.Intent
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.borjaweatherapp.BaseScreen
import com.example.borjaweatherapp.R
import com.example.borjaweatherapp.databinding.ActivityCompletionScreenBinding
import com.example.borjaweatherapp.ui.LoginScreen
import com.example.borjaweatherapp.viewmodel.RegistrationViewModel
import com.example.borjaweatherapp.viewmodelfactory.RegistrationFactory

class CompletionScreen : BaseScreen() {

    private lateinit var binding: ActivityCompletionScreenBinding
    private lateinit var viewModel: RegistrationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_completion_screen)

        viewModel = ViewModelProvider(
            this, RegistrationFactory(
                this.application,
                this)).get(RegistrationViewModel::class.java)

        binding.toolbar.imgLogout.isVisible = false
        binding.toolbar.txtToolbarTitle.text = resources.getString(R.string.success)
        binding.toolbar.imgBack.isVisible = false

        binding.txtMessage.text = getString(R.string.registration_success)

        binding.btnHome.setOnClickListener {
            startActivity(Intent(applicationContext, LoginScreen::class.java))
        }
    }
}