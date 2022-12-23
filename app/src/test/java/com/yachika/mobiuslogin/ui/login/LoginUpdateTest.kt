package com.yachika.mobiuslogin.ui.login

import com.spotify.mobius.test.NextMatchers.hasModel
import com.spotify.mobius.test.NextMatchers.hasNoEffects
import com.spotify.mobius.test.UpdateSpec
import com.spotify.mobius.test.UpdateSpec.assertThatNext
import org.junit.Test

class LoginUpdateTest {

    private val defaultModel = LoginModel(username = null)

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
}