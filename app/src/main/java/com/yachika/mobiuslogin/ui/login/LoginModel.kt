package com.yachika.mobiuslogin.ui.login

data class LoginModel(
    val username: String?
) {
    fun usernameChanged(username: String) = copy(username = username)
}
