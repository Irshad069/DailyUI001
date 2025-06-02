package com.dailyui001.ui.presentation.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue
import com.dailyui001.base.BaseViewModel

class SignUpViewModel : BaseViewModel() {

    var state by mutableStateOf(SignUpScreenState())
        private set


    fun actionEvent(actionEvent: ActionEvent){
        when (actionEvent) {
            is ActionEvent.OnEmailChange -> {
                state = state.copy(email = actionEvent.email)
            }

            is ActionEvent.OnPasswordChange -> {
                state = state.copy(password = actionEvent.password)
            }

            is ActionEvent.TogglePasswordVisibility -> {
                state = state.copy(passwordVisible = !state.passwordVisible)
            }

            ActionEvent.OnCreateAccountClick -> {

            }
        }
    }

    sealed class ActionEvent{
        data class OnEmailChange(val email: TextFieldValue) : ActionEvent()
        data class OnPasswordChange(val password: TextFieldValue) : ActionEvent()
        data object TogglePasswordVisibility : ActionEvent()
        data object OnCreateAccountClick : ActionEvent()
    }
}