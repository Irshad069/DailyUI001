package com.dailyui001.base

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import com.dailyui001.extention.safeDrawingPaddingTop
import com.dailyui001.ui.navigation.NavigationAction
import com.dailyui001.ui.theme.DailyUI001Color
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest

@Composable
fun BaseScreen(
    modifier: Modifier = Modifier,
    backGroundImg: Int? = null,
    bgColor: Color? = null,
    baseUIEvents: SharedFlow<BaseViewModel.BaseViewModelEvents>,
    navigation: (NavigationAction) -> Unit = {},
    content: @Composable () -> Unit,
) {
    val context = LocalContext.current

    var showLoader by remember { mutableStateOf(false) }
    backGroundImg?.let {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            Image(
                painter = painterResource(backGroundImg),
                contentDescription = "",
                modifier = modifier,
                contentScale = ContentScale.Crop
            )
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = bgColor ?: DailyUI001Color.black)
            .safeDrawingPaddingTop()
    ) {
        content()
        if (showLoader) {
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.Center)
            )
        }
    }

    LaunchedEffect(Unit) {

        baseUIEvents.collectLatest { event ->
            when (event) {
                is BaseViewModel.BaseViewModelEvents.ShowError -> {
                    //TODO()
                }

                is BaseViewModel.BaseViewModelEvents.Navigate -> {
                    navigation(event.route)
                }

                is BaseViewModel.BaseViewModelEvents.ShowToast -> {
                    Toast.makeText(context, event.msg, Toast.LENGTH_SHORT).show()
                }

                is BaseViewModel.BaseViewModelEvents.ShowLoader -> {
                    showLoader = event.show
                }
            }
        }

    }

}