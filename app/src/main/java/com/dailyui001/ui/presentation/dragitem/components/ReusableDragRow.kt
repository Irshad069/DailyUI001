package com.dailyui001.ui.presentation.dragitem.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.app.theme.utils.sdp
import com.app.theme.utils.ssp
import com.dailyui001.ui.presentation.dragitem.model.DragItem
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

/**
 * Author: Irshaad Khan
 * Date: 29/10/2025
 */

@Composable
fun ReusableDragRow(
    middleItems: List<DragItem>,
    fixedStart: DragItem? = null,
    fixedEnd: DragItem? = null,
    onListReordered: (List<DragItem>) -> Unit
) {
    var currentItems by remember { mutableStateOf(middleItems) }
    var draggedItem by remember { mutableStateOf<DragItem?>(null) }
    var draggedOriginalIndex by remember { mutableStateOf<Int?>(null) }
    var hoverIndex by remember { mutableStateOf<Int?>(null) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.sdp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        fixedStart?.let { FixedItem(it) }

        val maxIndex = currentItems.lastIndex

        currentItems.forEachIndexed { index, item ->
            val isDragging = draggedItem == item
            val isTargeted = !isDragging && hoverIndex == index && draggedItem != null

            DraggableItem(
                item = item,
                currentIndex = index,
                maxIndex = maxIndex,
                isDragging = isDragging,
                isTargeted = isTargeted,
                hoverIndex = hoverIndex,
                draggedOriginalIndex = draggedOriginalIndex,
                onDragStart = {
                    draggedItem = item
                    draggedOriginalIndex = index
                    hoverIndex = index
                },
                onHoverIndexChange = { hoverIndex = it },
                onDragEnd = { newIndex ->
                    val from = draggedOriginalIndex ?: index
                    val to = newIndex

                    val newList = currentItems.toMutableList()
                    val movedItem = newList.removeAt(from)
                    newList.add(to, movedItem)

                    currentItems = newList
                    draggedItem = null
                    draggedOriginalIndex = null
                    hoverIndex = null

                    onListReordered(newList)
                }
            )
        }

        fixedEnd?.let { FixedItem(it) }
    }
}


@Composable
private fun FixedItem(item: DragItem) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .size(70.sdp)
            .background(Color.Gray, RoundedCornerShape(12.sdp))
            .padding(8.sdp)
    ) {
        Image(
            painter = painterResource(id = item.imageRes),
            contentDescription = item.title,
            modifier = Modifier.size(32.sdp)
        )
        Spacer(modifier = Modifier.height(4.sdp))
        Text(text = item.title, color = Color.White, fontSize = 12.ssp)
    }
}

@Composable
private fun DraggableItem(
    item: DragItem,
    currentIndex: Int,
    maxIndex: Int,
    isDragging: Boolean,
    isTargeted: Boolean,
    hoverIndex: Int?,
    draggedOriginalIndex: Int?,
    onDragStart: () -> Unit,
    onDragEnd: (Int) -> Unit,
    onHoverIndexChange: (Int) -> Unit
) {
    val offsetX = remember { Animatable(0f) }
    val coroutineScope = rememberCoroutineScope()
    var totalDragX by remember { mutableFloatStateOf(0f) }
    var dragStartIndex by remember { mutableIntStateOf(currentIndex) }
    val itemWidthPx = 130f

    val shiftOffset = remember(isDragging, hoverIndex, draggedOriginalIndex, currentIndex) {
        if (!isDragging && hoverIndex != null && draggedOriginalIndex != null) {
            when {
                draggedOriginalIndex < hoverIndex && currentIndex in (draggedOriginalIndex + 1)..hoverIndex -> -itemWidthPx
                draggedOriginalIndex > hoverIndex && currentIndex in hoverIndex until draggedOriginalIndex -> itemWidthPx
                else -> 0f
            }
        } else {
            0f
        }
    }

    LaunchedEffect(shiftOffset) {
        if (!isDragging) {
            offsetX.animateTo(
                shiftOffset,
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessMedium
                )
            )
        }
    }

    Box(
        modifier = Modifier
            .offset { IntOffset(offsetX.value.roundToInt(), 0) }
            .zIndex(if (isDragging) 10f else 0f)
            .size(70.sdp)
            .graphicsLayer {
                if (isDragging) {
                    scaleX = 1.1f
                    scaleY = 1.1f
                }
            }
            .background(
                when {
                    isDragging -> Color(0xFFFF5252)
                    isTargeted -> Color(0xFFFFAB40)
                    else -> Color(0xFF2196F3)
                },
                RoundedCornerShape(12.sdp)
            )
            .then(
                if (isTargeted) {
                    Modifier.border(
                        width = 2.dp,
                        color = Color(0xFFFF6F00),
                        shape = RoundedCornerShape(12.sdp)
                    )
                } else Modifier
            )
            .pointerInput(item.index) {
                detectDragGestures(
                    onDragStart = {
                        totalDragX = 0f
                        dragStartIndex = currentIndex
                        onDragStart()
                    },
                    onDrag = { change, dragAmount ->
                        change.consume()
                        totalDragX += dragAmount.x

                        coroutineScope.launch {
                            val maxLeftDrag = -itemWidthPx * dragStartIndex
                            val maxRightDrag = itemWidthPx * (maxIndex - dragStartIndex)
                            offsetX.snapTo(totalDragX.coerceIn(maxLeftDrag, maxRightDrag))

                            val movedItems = (totalDragX / itemWidthPx).roundToInt()
                            val targetIndex = (dragStartIndex + movedItems).coerceIn(0, maxIndex)
                            onHoverIndexChange(targetIndex)
                        }
                    },
                    onDragEnd = {
                        val movedItems = (totalDragX / itemWidthPx).roundToInt()
                        val finalIndex = (dragStartIndex + movedItems).coerceIn(0, maxIndex)

                        coroutineScope.launch {
                            offsetX.animateTo(
                                0f,
                                animationSpec = spring(
                                    dampingRatio = Spring.DampingRatioMediumBouncy,
                                    stiffness = Spring.StiffnessLow
                                )
                            )
                        }

                        onDragEnd(finalIndex)
                    },
                    onDragCancel = {
                        coroutineScope.launch {
                            offsetX.animateTo(
                                0f,
                                animationSpec = spring(
                                    dampingRatio = Spring.DampingRatioMediumBouncy,
                                    stiffness = Spring.StiffnessLow
                                )
                            )
                        }
                        onDragEnd(dragStartIndex)
                    }
                )
            },
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(8.sdp)
        ) {
            Image(
                painter = painterResource(id = item.imageRes),
                contentDescription = item.title,
                modifier = Modifier.size(32.sdp)
            )
            Spacer(modifier = Modifier.height(4.sdp))
            Text(text = item.title, color = Color.White, fontSize = 12.ssp)
        }
    }
}