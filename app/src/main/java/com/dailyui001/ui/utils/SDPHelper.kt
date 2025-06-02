package com.app.theme.utils

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp

val Int.sdp: Dp
    @Composable
    get() = this.sdpGet()

val Int.ssp: TextUnit
    @Composable get() = this.sspGet()

@Composable
fun Int.sdpGet(): Dp {
    val minValue = kotlin.math.min(getScreenHeight(), getScreenWidth())
    val ratio = minValue / 412.0 // Adjusted baseline for mobile (common 360dp width)
    return (this * ratio).dp
}

@Composable
fun Int.sspGet(): TextUnit {
    return this.textSdp(density = LocalDensity.current)
}

@Composable
private fun Int.textSdp(density: Density): TextUnit = with(density) {
    this@textSdp.sdp.toSp()
}

@Composable
fun getScreenWidth(): Float {
    val configuration = LocalConfiguration.current
    return configuration.screenWidthDp.toFloat()
}

@Composable
fun getScreenHeight(): Float {
    val configuration = LocalConfiguration.current
    return configuration.screenHeightDp.toFloat()
}

