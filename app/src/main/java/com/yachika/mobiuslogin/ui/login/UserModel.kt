package com.yachika.mobiuslogin.ui.login

interface FakeLogin {
    fun loginUser(username: String, password: String): LoginResponse
}