package com.yachika.mobiuslogin.ui.login

import com.spotify.mobius.test.NextMatchers.*
import com.spotify.mobius.test.UpdateSpec
import com.spotify.mobius.test.UpdateSpec.assertThatNext
import org.junit.Test

class LoginUpdateTest {

    private val defaultModel = LoginModel(username = "", password = "")

    @Test
    fun `when the user changes the username, UI should be updated`() {
        val username = "Lorem ipsum"
        UpdateSpec(LoginUpdate())
            .given(defaultModel)
            .whenEvent(UserNameChanged(username = username))
            .then(
                assertThatNext(
                    hasModel(defaultModel.usernameChanged(username = username)),
                    hasNoEffects()
                )
            )
    }

    @Test
    fun `when the user changes the password, UI should be updated`() {
        val password = "Password"
        UpdateSpec(LoginUpdate())
            .given(defaultModel)
            .whenEvent(PasswordChanged(password = password))
            .then(
                assertThatNext(
                    hasModel(defaultModel.passwordChanged(password)),
                    hasNoEffects()
                )
            )
    }

    @Test
    fun `when login button is clicked, check if the input is valid`() {
        UpdateSpec(LoginUpdate())
            .given(defaultModel)
            .whenEvent(LoginButtonClicked)
            .then(
                assertThatNext(
                    hasNoModel(),
                    hasEffects(ValidateInput(defaultModel.username, defaultModel.password))
                )
            )
    }
}