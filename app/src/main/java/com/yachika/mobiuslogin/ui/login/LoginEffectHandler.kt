package com.yachika.mobiuslogin.ui.login

import com.spotify.mobius.Connectable
import com.spotify.mobius.Connection
import com.spotify.mobius.functions.Consumer
import com.yachika.mobiuslogin.ui.login.InputValidationErrors.*

class LoginEffectHandler :
    Connectable<LoginEffect, LoginEvent> {
    override fun connect(output: Consumer<LoginEvent>): Connection<LoginEffect> {
        return object : Connection<LoginEffect> {
            override fun dispose() {

            }

            override fun accept(value: LoginEffect) {
                when (value) {
                    is ValidateInput -> validateInput(value, output)

                    // add the validation if username is blank
                    // and password is bank and pass the necessary events
                }
            }
        }
    }

    private fun validateInput(value: ValidateInput, output: Consumer<LoginEvent>) {
        val username = value.username
        val password = value.password

        val validationErrors = when {
            username.isEmpty() -> setOf(EMPTY_USERNAME)
            password.isEmpty() -> setOf(EMPTY_PASSWORD)
            password.length < 8 -> setOf(PASSWORD_LESS_THAN_8_CHARACTER)
            else -> emptySet()
        }

        val events = if (validationErrors.isNotEmpty()) {
            ValidationFailed(validationErrors)
        } else {
            ValidationFailed(validationErrors)
        }
        output.accept(events)
    }
}