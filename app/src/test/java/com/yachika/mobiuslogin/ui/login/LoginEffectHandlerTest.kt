package com.yachika.mobiuslogin.ui.login

import android.accounts.NetworkErrorException
import com.spotify.mobius.Connection
import com.spotify.mobius.test.RecordingConsumer
import com.yachika.mobiuslogin.ui.login.InputValidationErrors.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import com.nhaarman.mockitokotlin2.*
import org.mockito.MockitoAnnotations
import java.io.IOException
import java.net.SocketTimeoutException

class LoginEffectHandlerTest {

    private lateinit var connection: Connection<LoginEffect>
    private val outputEvents = RecordingConsumer<LoginEvent>()

    @Mock
    lateinit var userRepository: FakeLoginApi

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

        // when
        val effect = ShowInvalidInputError(setOf(EMPTY_USERNAME))
        connection.accept(effect)

        // then
        // ask Noyal on asserting ui actions here,
        // one possible way is mocking ui actions,
        // is there another way to use the fake one that we've created here
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
    fun `when logging_in effect is failed then show login error message`(){
        //then

        //given
        connection.accept(ShowLoginError)

        //then
        //todo UI mocking
    }

    @After
    fun dispose() {
        connection.dispose()
    }

    @Before
    fun setup() {
        val effectHandler = LoginEffectHandler(FakeLoginApi())
        connection = effectHandler.connect(outputEvents)
    }

}