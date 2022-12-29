package com.yachika.mobiuslogin.ui.login

data class LoginModel(
    val username: String, val password: String
) {

    companion object {
        fun create(): LoginModel = LoginModel(
            username = "",
            password = ""
        )
    }

    fun usernameChanged(username: String) = copy(username = username)

    fun passwordChanged(password: String) = copy(password = password)
}
