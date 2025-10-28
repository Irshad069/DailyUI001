package com.dailyui001.ui.presentation.dragitem.model

import com.dailyui001.base.BaseState

/**
 * Author: Irshaad Khan
 * Date: 28/10/2025
 */

data class DragState(
    val fixedStart: DragItem? = null,
    val middleItems: List<DragItem> = emptyList(),
    val fixedEnd: DragItem? = null,
    val draggedItem: DragItem? = null,
    val draggedOriginalIndex: Int? = null,
    val hoverIndex: Int? = null,
    val originalIndexMap: Map<Int, Int> = emptyMap()
) : BaseState
