package com.yachika.mobiuslogin.ui.login

sealed class LoginEffect

data class ValidateInput(val username: String, val password: String): LoginEffect()