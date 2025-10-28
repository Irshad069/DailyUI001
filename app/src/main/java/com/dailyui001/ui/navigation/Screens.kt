package com.dailyui001.ui.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Screens {

    @Serializable
    data object LoginDisplay : Screens()

    @Serializable
    data object DragItemDisplay : Screens()

}