package com.yachika.mobiuslogin.ui.login

import com.spotify.mobius.Next
import com.spotify.mobius.Next.*
import com.spotify.mobius.Update

class LoginUpdate : Update<LoginModel, LoginEvent, LoginEffect> {
    override fun update(model: LoginModel, event: LoginEvent): Next<LoginModel, LoginEffect> {
        return when (event) {
            is UserNameChanged -> next(model.usernameChanged(event.username))
            is PasswordChanged -> next(model.passwordChanged(event.password))
            is LoginButtonClicked -> dispatch(
                setOf(
                    ValidateInput(
                        username = model.username,
                        password = model.password
                    )
                )
            )
            is ValidationFailed -> dispatch(setOf(ShowInvalidInputError(event.loginEnum)))
            is ValidationSuccessful -> dispatch(setOf(LoggingIn(model.username, model.password)))
            LoginFailure -> dispatch(setOf(ShowLoginError))
            LoginSuccessful -> dispatch(setOf(SaveUserData(model.username, model.password)))
            UserSaved -> dispatch(setOf(GoToHomeScreen))
        }
    }
}
