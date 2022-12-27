package com.yachika.mobiuslogin.ui.login

import com.spotify.mobius.Connection
import com.spotify.mobius.test.RecordingConsumer
import com.yachika.mobiuslogin.ui.login.InputValidationErrors.*
import org.junit.After
import org.junit.Before
import org.junit.Test

class LoginEffectHandlerTest {

    private lateinit var connection: Connection<LoginEffect>
    private val outputEvents = RecordingConsumer<LoginEvent>()

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

    interface FakeUiActions {
        fun showInvalidError(errors: Set<InputValidationErrors>)
    }

    @After
    fun dispose() {
        connection.dispose()
    }

    @Before
     fun setup() {
        val effectHandler = LoginEffectHandler()
        connection = effectHandler.connect(outputEvents)
    }
}