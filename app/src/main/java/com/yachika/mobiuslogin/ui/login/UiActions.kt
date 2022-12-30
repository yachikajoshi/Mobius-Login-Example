package com.yachika.mobiuslogin.ui.login

interface UiActions {
    fun showInvalidErrors(errors: Set<InputValidationErrors>)
    fun showLoginError()
    fun goToHome()
}