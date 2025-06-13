package com.dailyui001.ui.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.sp
import com.app.theme.utils.sdp
import com.dailyui001.ui.theme.DailyUI001Color

@Composable
fun BaseTextField(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    label: String,
    isError: Boolean = false,
    isReadOnly: Boolean = false,
    trailingIcon: @Composable (() -> Unit)? = null,
    modifier: Modifier = Modifier,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    visualTransformation: VisualTransformation = PasswordVisualTransformation(),
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = {
            Text(
                text = label,
                fontSize = 16.sp,
                lineHeight = 16.sp,
                color = DailyUI001Color.termTextColor
            )
        },
        textStyle = TextStyle(
            fontSize = 16.sp,
            lineHeight = 16.sp,
            color = DailyUI001Color.loginText
        ),
        singleLine = true,
        isError = isError,
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.Transparent),
        readOnly = isReadOnly,
        trailingIcon = trailingIcon,
        keyboardOptions = keyboardOptions,
        visualTransformation = visualTransformation,
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = DailyUI001Color.textColor,
            unfocusedTextColor = DailyUI001Color.textColor,
            disabledTextColor = DailyUI001Color.textColor,
            focusedContainerColor = DailyUI001Color.containerColor,
            unfocusedContainerColor = DailyUI001Color.containerColor,
            focusedBorderColor = DailyUI001Color.borderColor,
            unfocusedBorderColor = DailyUI001Color.borderColor,
            disabledBorderColor = DailyUI001Color.borderColor,
        ),
        shape = RoundedCornerShape(12.sdp)
    )
}
