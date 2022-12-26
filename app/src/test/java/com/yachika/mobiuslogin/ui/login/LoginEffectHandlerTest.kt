package com.yachika.mobiuslogin.ui.login

import com.spotify.mobius.Connection
import com.spotify.mobius.test.RecordingConsumer
import com.yachika.mobiuslogin.ui.login.InputValidationErrors.EMPTY_USERNAME
import org.junit.Test

class LoginEffectHandlerTest {

    private lateinit var connection: Connection<LoginEffect>
    private val outputEvents = RecordingConsumer<LoginEvent>()

    @Test
    fun when_validate_input_is_received_then_validate_the_entered_username() {
        // given
        val effectHandler = LoginEffectHandler()
        connection = effectHandler.connect(outputEvents)

        // when
        val effect = ValidateInput(username = "", password = "12345678")
        connection.accept(effect)

        // then
        outputEvents.assertValues(ValidationFailed(setOf(EMPTY_USERNAME)))
    }
}