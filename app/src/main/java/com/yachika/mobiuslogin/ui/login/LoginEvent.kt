package com.yachika.mobiuslogin.ui.login

sealed class LoginEvent

data class UserNameChanged(val username: String) : LoginEvent()
