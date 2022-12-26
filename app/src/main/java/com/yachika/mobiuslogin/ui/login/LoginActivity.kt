package com.yachika.mobiuslogin.ui.login

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.yachika.mobiusintro.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity(), UiActions {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun showInvalidErrors(errors: Set<InputValidationErrors>) {
        // do when and show text depending on error
    }
}