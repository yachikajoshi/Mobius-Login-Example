package com.yachika.mobiuslogin.ui.login

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.spotify.mobius.Connection
import com.spotify.mobius.Mobius
import com.spotify.mobius.MobiusLoop
import com.spotify.mobius.android.MobiusAndroid
import com.spotify.mobius.functions.Consumer
import com.yachika.mobiusintro.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity(), UiActions {

    private lateinit var binding: ActivityLoginBinding

    private val mobiusLoop: MobiusLoop.Builder<LoginModel, LoginEvent, LoginEffect> =
        Mobius.loop(
            LoginUpdate(),
            LoginEffectHandler(uiActions = this, loginApi = FakeLoginApi())
        )

    private val controller: MobiusLoop.Controller<LoginModel, LoginEvent> =
        MobiusAndroid.controller(mobiusLoop, LoginModel.create())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        controller.connect { events ->
            connectEvents(events)
        }
    }

    private fun connectEvents(events: Consumer<LoginEvent>): Connection<LoginModel> {

        binding.username.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                events.accept(UserNameChanged(p0.toString()))
            }

        })
        binding.password.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                events.accept(PasswordChanged(p0.toString()))
            }

        })
        binding.login.setOnClickListener {
            events.accept(LoginButtonClicked)
        }

        return object : Connection<LoginModel> {
            override fun dispose() {

            }

            override fun accept(value: LoginModel) {
            }
        }
    }

    override fun showInvalidErrors(errors: Set<InputValidationErrors>) {
        // do when and show text depending on error
        if (InputValidationErrors.EMPTY_USERNAME in errors) {
            binding.usernameTL.error = "Invalid"
        }else if (InputValidationErrors.EMPTY_PASSWORD in errors){

        }
    }

    override fun onPause() {
        super.onPause()
        controller.stop()
    }

    override fun onResume() {
        super.onResume()
        controller.start()
    }

    override fun onDestroy() {
        controller.disconnect()
        super.onDestroy()
    }
}