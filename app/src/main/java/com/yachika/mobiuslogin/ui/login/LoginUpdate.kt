package com.yachika.mobiuslogin.ui.login

import com.spotify.mobius.Next
import com.spotify.mobius.Next.next
import com.spotify.mobius.Update

class LoginUpdate : Update<LoginModel, LoginEvent, LoginEffect> {
    override fun update(model: LoginModel, event: LoginEvent): Next<LoginModel, LoginEffect> {
        return when (event) {
            is UserNameChanged -> next(model.usernameChanged(event.username))
            is PasswordChanged -> next(model.passwordChanged(event.password))
        }
    }
}