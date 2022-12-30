package com.yachika.mobiuslogin.ui.login

import com.spotify.mobius.Connection
import com.spotify.mobius.test.RecordingConsumer
import com.yachika.mobiuslogin.ui.login.InputValidationErrors.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import com.nhaarman.mockitokotlin2.*

class LoginEffectHandlerTest {

    private lateinit var connection: Connection<LoginEffect>
    private val outputEvents = RecordingConsumer<LoginEvent>()
    private val uiActions = mock<UiActions>()

    @Test
    fun when_validate_input_is_received_then_validate_the_entered_username() {
        // given

        // when
        val effect = ValidateInput(username = "", password = "12345678")
        connection.accept(effect)

        // then
        outputEvents.assertValues(ValidationFailed(setOf(EMPTY_USERNAME)))
    }

    @Test
    fun `when validate input is received, then validate the entered password`() {
        //given

        //when
        val effect = ValidateInput(username = "Yachika", password = "")
        connection.accept(effect)

        //then
        outputEvents.assertValues(ValidationFailed(setOf(EMPTY_PASSWORD)))
    }

    @Test
    fun `when validate input is received, then check the entered password length`() {
        //given

        //when
        val effect = ValidateInput(username = "Yachika", password = "1234567")
        connection.accept(effect)

        //then
        outputEvents.assertValues(ValidationFailed(setOf(PASSWORD_LESS_THAN_8_CHARACTER)))
    }

    @Test
    fun `when validate input is received with valid username and password, then show validation success`() {
        //given

        //when
        val effect = ValidateInput("Yachika", "12345678")
        connection.accept(effect)

        //then
        outputEvents.assertValues(ValidationSuccessful)
    }

    @Test
    fun when_show_input_validation_error_effect_is_received_then_show_error() {
        // given
        val error = setOf(EMPTY_USERNAME)

        // when
        val effect = ShowInvalidInputError(error)
        connection.accept(effect)

        // then
        verify(uiActions).showInvalidErrors(error)
        verifyNoMoreInteractions(uiActions)
    }

    @Test
    fun when_logging_in_effect_is_received_then_login_is_failed() {
        // given
        val service = mock<FakeLogin>()
        val userName = "yachika"
        val password = "1234"

        // when
        connection.accept(LoggingIn(userName, password))

        whenever(service.loginUser(username = userName, password = password))
            .thenThrow()

        // then
        outputEvents.assertValues(LoginFailure)
    }

    @Test
    fun `when logging in effect is received then login is successful`() {
        //given
        val service = mock<FakeLogin>()
        val userName = "rohit"
        val password = "1234"

        //when
        connection.accept(LoggingIn(userName, password))

        whenever(service.loginUser(username = userName, password = password))
            .thenReturn(LoginResponse(""))

        //then
        outputEvents.assertValues(LoginSuccessful)
    }

    @Test
    fun `when logging_in effect is failed then show login error message`() {
        //then

        //given
        connection.accept(ShowLoginError)

        //then
        uiActions.showLoginError()

    }

    @Test
    fun `when loggin_in effect is successful then save the user`() {
        //then

        //given
        connection.accept(SaveUserData("Yachika", "12345678"))

        //then
        outputEvents.assertValues(UserSaved)
    }

    @Test
    fun `when user data is saved then go to home screen`() {

        //then

        //given
        connection.accept(GoToHomeScreen)

        //then
        uiActions.goToHome()
    }

    @After
    fun dispose() {
        connection.dispose()
    }

    @Before
    fun setup() {
        val effectHandler = LoginEffectHandler(uiActions, FakeLoginApi())
        connection = effectHandler.connect(outputEvents)
    }

}