package com.yachika.mobiuslogin.ui.login

data class LoginModel(
    val username: String,
    val password: String
) {

    fun usernameChanged(username: String) = copy(username = username)

    fun passwordChanged(password: String) = copy(password = password)
}
