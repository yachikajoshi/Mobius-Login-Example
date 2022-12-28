package com.yachika.mobiuslogin.ui.login

class UserRepository {
    val userList = listOf(
        UserModel("John", "jo@yahoo.com", "2hsgbnu8s"),
        UserModel("Tim", "tim12@gmail.com", "2h6n8nu8s"),
        UserModel("Peter", "peterS@gmail.com", "2h00nu8s"),
    )

    fun loginUser(email: String, password: String): LoginStatus {
        val users = userList.filter { it.email == email }
        return if (users.size == 1) {
            if (users[0].password == password) {
                LoginStatus.SUCCESS
            } else {
                LoginStatus.INVALID_PASSWORD
            }
        } else {
            LoginStatus.INVALID_USER
        }

    }
}