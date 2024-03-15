package com.github.zottaa.authorization.registration

interface RegistrationResult {

    fun message(): String
    object Success : RegistrationResult {
        override fun message(): String = "User created!"
    }

    data class Error(
        private val errorMessage: String
    ) : RegistrationResult {
        override fun message(): String = errorMessage
    }
}