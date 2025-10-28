package com.dailyui001.ui.presentation.dragitem.model

/**
 * Author: Irshaad Khan
 * Date: 28/10/2025
 */

sealed class DragIntent {
    data class OnDragStart(val item: DragItem, val index: Int) : DragIntent()
    object OnDragEnd : DragIntent()
    data class OnHoverIndexChange(val index: Int) : DragIntent()
    data class OnDragEndWithTargetIndex(val fromIndex: Int, val toIndex: Int) : DragIntent()

}