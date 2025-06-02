package com.dailyui001.extention

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController

@Composable
fun Modifier.safeDrawingPaddingTop(): Modifier {
    return this.padding(
        WindowInsets.safeDrawing
            .only(WindowInsetsSides.Top)
            .asPaddingValues()
    )
}

@Composable
fun Modifier.safeDrawingPaddingBottom(): Modifier {
    return this.padding(
        WindowInsets.safeDrawing
            .only(WindowInsetsSides.Bottom)
            .asPaddingValues()
    )
}

@Composable
fun Modifier.hideKeyboardOnTap(): Modifier {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    return this.pointerInput(Unit) {
        detectTapGestures(
            onTap = {
                keyboardController?.hide()
                focusManager.clearFocus(true)
            }
        )
    }
}
