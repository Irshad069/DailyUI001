package com.dailyui001.ui.theme

import android.annotation.SuppressLint
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.platform.LocalContext
import com.dailyui001.ui.theme.color.DarkColorPalette
import com.dailyui001.ui.theme.color.LightColorPalette
import com.dailyui001.ui.theme.color.MyColors

private val LocalColors = staticCompositionLocalOf { LightColorPalette }

@SuppressLint("NewApi")
@Composable
fun DailyUI001Theme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    CompositionLocalProvider(
        LocalColors provides colors
    ) {
        MaterialTheme(
            content = content
        )
    }
}

val DailyUI001Color: MyColors
    @Composable
    @ReadOnlyComposable
    get() = LocalColors.current
