package com.yachika.mobiuslogin.ui.login

import com.spotify.mobius.test.NextMatchers.*
import com.spotify.mobius.test.UpdateSpec
import com.spotify.mobius.test.UpdateSpec.assertThatNext
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

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

    @Test
    fun `when input validation is failed, then show the error`() {
        val error = setOf(InputValidationErrors.EMPTY_PASSWORD)
        UpdateSpec(LoginUpdate())
            .given(defaultModel)
            .whenEvent(ValidationFailed(error))
            .then(
                assertThatNext(
                    hasNoModel(),
                    hasEffects(ShowInvalidInputError(error))
                )
            )
    }

    @Test
    fun `when input validation is successful, then login the user`() {
        UpdateSpec(LoginUpdate())
            .given(defaultModel)
            .whenEvent(ValidationSuccessful)
            .then(
                assertThatNext(
                    hasNoModel(),
                    hasEffects(LoggingIn(defaultModel.username, defaultModel.password))
                )
            )
    }

    @Test
    fun `when login failed, then show the error`() {
        UpdateSpec(LoginUpdate())
            .given(defaultModel)
            .whenEvent(LoginFailure)
            .then(
                assertThatNext(
                    hasNoModel(),
                    hasEffects(ShowLoginError)
                )
            )
    }

    @Test
    fun `when login success, then save the user data`() {
        UpdateSpec(LoginUpdate())
            .given(defaultModel)
            .whenEvent(LoginSuccessful)
            .then(
                assertThatNext(
                    hasNoModel(),
                    hasEffects(SaveUserData(defaultModel.username, defaultModel.password))
                )
            )
    }

    @Test
    fun `when user data save, then go to home screen`() {
        UpdateSpec(LoginUpdate())
            .given(defaultModel)
            .whenEvent(UserSaved)
            .then(
                assertThatNext(
                    hasNoModel(),
                    hasEffects(GoToHomeScreen)
                )
            )
    }
}