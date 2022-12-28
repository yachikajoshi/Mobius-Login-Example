package com.yachika.mobiuslogin.ui.login

import java.io.IOException
import java.net.SocketTimeoutException

class FakeLoginApi : FakeLogin {
    override fun loginUser(username: String, password: String): LoginResponse {
        return when (username) {
            "yachika" -> throw IOException()
            "honey" -> throw SocketTimeoutException()
            else -> LoginResponse("access granted")
        }
    }
}