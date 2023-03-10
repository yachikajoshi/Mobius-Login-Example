package com.yachika.mobiuslogin.ui.login

sealed class LoginEvent

data class UserNameChanged(val username: String) : LoginEvent()

data class PasswordChanged(val password: String) : LoginEvent()

object LoginButtonClicked : LoginEvent()

data class ValidationFailed(val loginEnum: Set<InputValidationErrors>) : LoginEvent()

object ValidationSuccessful : LoginEvent()

object LoginFailure : LoginEvent()

object LoginSuccessful : LoginEvent()

object UserSaved : LoginEvent()
