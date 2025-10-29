package com.dailyui001.ui.presentation.dragitem

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.dailyui001.R
import com.dailyui001.base.BaseViewModel
import com.dailyui001.ui.presentation.dragitem.model.DragIntent
import com.dailyui001.ui.presentation.dragitem.model.DragItem
import com.dailyui001.ui.presentation.dragitem.model.DragState

/**
 * Author: Irshaad Khan
 * Date: 28/10/2025
 */

class DragViewModel : BaseViewModel() {

    var state by mutableStateOf(DragState())
        private set

    init {
        val start = DragItem(0, "Item A", R.drawable.ic_one)
        val middle = listOf(
            DragItem(1, "Item B", R.drawable.ic_two),
            DragItem(2, "Item C", R.drawable.ic_three),
            DragItem(3, "Item D", R.drawable.ic_four)
        )
        val end = DragItem(4, "Item E", R.drawable.ic_five)

        val indexMap = middle.mapIndexed { index, item -> item.index to index }.toMap()

        state = DragState(
            fixedStart = start,
            middleItems = middle,
            fixedEnd = end,
            originalIndexMap = indexMap
        )
    }

    fun intent(intent: DragIntent) {
        when (intent) {
            is DragIntent.OnDragStart -> {
                state = state.copy(
                    draggedItem = intent.item,
                    draggedOriginalIndex = intent.index,
                    hoverIndex = intent.index
                )
            }

            is DragIntent.OnHoverIndexChange -> {
                val draggedItem = state.draggedItem ?: return
                val draggedIndex = state.middleItems.indexOf(draggedItem)
                val hoverIndex = intent.index.coerceIn(0, state.middleItems.lastIndex)

                if (hoverIndex != draggedIndex) {
                    val newList = state.middleItems.toMutableList()
                    val temp = newList[hoverIndex]
                    newList[hoverIndex] = newList[draggedIndex]
                    newList[draggedIndex] = temp

                    val newIndexMap = newList.mapIndexed { index, dragItem ->
                        dragItem.index to index
                    }.toMap()

                    state = state.copy(
                        middleItems = newList,
                        hoverIndex = hoverIndex,
                        originalIndexMap = newIndexMap
                    )
                }
            }

            is DragIntent.OnDragEnd -> {
                state = state.copy(
                    draggedItem = null,
                    draggedOriginalIndex = null,
                    hoverIndex = null
                )
            }

            is DragIntent.OnDragEndWithTargetIndex -> {
                val from = intent.fromIndex
                val to = intent.toIndex

                if (from != to) {
                    val newList = state.middleItems.toMutableList()

                    // Simple swap instead of remove and add
                    val temp = newList[from]
                    newList[from] = newList[to]
                    newList[to] = temp

                    state = state.copy(
                        middleItems = newList,
                        draggedItem = null,
                        draggedOriginalIndex = null,
                        hoverIndex = null
                    )
                } else {
                    // No change if dropped at same position
                    state = state.copy(
                        draggedItem = null,
                        draggedOriginalIndex = null,
                        hoverIndex = null
                    )
                }
            }
        }
    }
}
