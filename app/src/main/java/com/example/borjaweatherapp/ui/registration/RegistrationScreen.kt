package com.example.borjaweatherapp.ui.registration

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.borjaweatherapp.BaseScreen
import com.example.borjaweatherapp.R
import com.example.borjaweatherapp.databinding.ActivityRegistrationScreenBinding
import com.example.borjaweatherapp.viewmodel.RegistrationViewModel
import com.example.borjaweatherapp.viewmodelfactory.RegistrationFactory

class RegistrationScreen : BaseScreen() {

    private lateinit var binding: ActivityRegistrationScreenBinding
    private lateinit var viewModel: RegistrationViewModel

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_registration_screen)

        viewModel = ViewModelProvider(
            this, RegistrationFactory(
                this.application,
                this)).get(RegistrationViewModel::class.java)

        binding.toolbar.imgLogout.isVisible = false
        binding.toolbar.txtToolbarTitle.text = resources.getString(R.string.registration)
        binding.toolbar.imgBack.setOnClickListener {
            finish()
        }

        binding.fabProceed.setOnClickListener {
            viewModel.validateUserInfo(
                binding.tieFirstName.text.toString().trim(),
                binding.tieLastName.text.toString().trim(),
                binding.tieMobile.text.toString().trim(),
                binding.tieMpin.text
            )
        }

        // show error dialog
        viewModel.getErrorMessage().observe(this, {
            Toast.makeText(
                this,
                it,
                Toast.LENGTH_SHORT
            ).show()
        })

        viewModel.navigateToSuccessScreen().observe(this, {
            if (it == true) {
                startActivity(Intent(applicationContext, CompletionScreen::class.java))
            }
        })
    }
}