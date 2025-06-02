package com.dailyui001.ui.presentation.login

import androidx.compose.ui.text.input.TextFieldValue
import com.dailyui001.base.BaseState

data class SignUpScreenState(
    val title: String = "LogIn Screen",
    val buttonText: String = "Create Account",
    val email: TextFieldValue = TextFieldValue(),
    val password: TextFieldValue = TextFieldValue(),
    var passwordVisible: Boolean = false,
) : BaseState