package com.paradoxo.hifood.ui.login

data class LoginFormState(
    val email: String = "",
    val password: String = "",
    val name: String = "",
    val isFirstAccess: Boolean = false,
    val allFieldIsOk: Boolean = false,
    val onNameChange: (String) -> Unit = {},
    val onEmailChange: (String) -> Unit = {},
    val onPasswordChange: (String) -> Unit = {},
)