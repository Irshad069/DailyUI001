package com.dailyui001.ui.presentation.dragitem

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.zIndex
import com.app.theme.utils.sdp
import com.app.theme.utils.ssp
import com.dailyui001.base.BaseScreen
import com.dailyui001.base.BaseViewModel
import com.dailyui001.ui.navigation.NavigationAction
import com.dailyui001.ui.presentation.dragitem.model.DragIntent
import com.dailyui001.ui.presentation.dragitem.model.DragItem
import com.dailyui001.ui.presentation.dragitem.model.DragState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

/**
 * Author: Irshaad Khan
 * Date: 28/10/2025
 */

@Composable
fun DragRowScreen(
    baseUIEvents: SharedFlow<BaseViewModel.BaseViewModelEvents> = MutableSharedFlow(),
    state: DragState = DragState(),
    navigation: (NavigationAction) -> Unit = {},
    intent: (DragIntent) -> Unit = {}
) {
    BaseScreen(
        baseUIEvents = baseUIEvents,
        navigation = navigation,
        bgColor = Color.White
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.sdp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            state.fixedStart?.let { FixedItem(it) }

            val maxIndex = state.middleItems.lastIndex

            state.middleItems.forEachIndexed { index, item ->
                val isDragging = state.draggedItem == item

                DraggableItem(
                    item = item,
                    currentIndex = index,
                    maxIndex = maxIndex,
                    isDragging = isDragging,
                    hoverIndex = state.hoverIndex,
                    draggedOriginalIndex = state.draggedOriginalIndex,
                    onDragStart = {
                        intent.invoke(DragIntent.OnDragStart(item, index))
                    },
                    onDragEnd = { newIndex ->
                        val fromIndex = state.draggedOriginalIndex ?: index
                        intent.invoke(DragIntent.OnDragEndWithTargetIndex(fromIndex, newIndex))
                    }
                )
            }

            state.fixedEnd?.let { FixedItem(it) }
        }
    }
}


@Composable
fun FixedItem(item: DragItem) {
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

//@Composable
//fun DraggableItem(
//    item: DragItem,
//    currentIndex: Int,
//    maxIndex: Int,
//    isDragging: Boolean,
//    hoverIndex: Int?,
//    draggedOriginalIndex: Int?,
//    onDragStart: () -> Unit,
//    onDragEnd: (Int) -> Unit,
//) {
//    val offsetX = remember { Animatable(0f) }
//    val coroutineScope = rememberCoroutineScope()
//    var totalDragX by remember { mutableFloatStateOf(0f) }
//    val itemWidthPx = 86f
//
//    Box(
//        modifier = Modifier
//            .offset { IntOffset(offsetX.value.roundToInt(), 0) }
//            .zIndex(if (isDragging) 10f else 0f)
//            .size(70.sdp)
//            .graphicsLayer {
//                if (isDragging) {
//                    scaleX = 1.1f
//                    scaleY = 1.1f
//                }
//            }
//            .background(
//                if (isDragging) Color(0xFFFF5252) else Color(0xFF2196F3),
//                RoundedCornerShape(12.sdp)
//            )
//            .pointerInput(item.index) {
//                detectDragGestures(
//                    onDragStart = {
//                        totalDragX = 0f
//                        onDragStart()
//                    },
//                    onDrag = { change, dragAmount ->
//                        change.consume()
//                        totalDragX += dragAmount.x
//
//                        coroutineScope.launch {
//                            offsetX.snapTo(totalDragX.coerceIn(-itemWidthPx * maxIndex, itemWidthPx * maxIndex))
//                        }
//                    },
//                    onDragEnd = {
//                        val movedItems = (totalDragX / itemWidthPx).roundToInt()
//                        val finalIndex = (currentIndex + movedItems).coerceIn(0, maxIndex)
//
//                        coroutineScope.launch {
//                            offsetX.animateTo(
//                                0f,
//                                animationSpec = spring(
//                                    dampingRatio = Spring.DampingRatioMediumBouncy,
//                                    stiffness = Spring.StiffnessLow
//                                )
//                            )
//                        }
//
//                        onDragEnd(finalIndex)
//                    },
//                    onDragCancel = {
//                        coroutineScope.launch {
//                            offsetX.animateTo(
//                                0f,
//                                animationSpec = spring(
//                                    dampingRatio = Spring.DampingRatioMediumBouncy,
//                                    stiffness = Spring.StiffnessLow
//                                )
//                            )
//                        }
//                        onDragEnd(currentIndex)
//                    }
//                )
//            },
//        contentAlignment = Alignment.Center
//    ) {
//        Column(
//            horizontalAlignment = Alignment.CenterHorizontally,
//            modifier = Modifier.padding(8.sdp)
//        ) {
//            Image(
//                painter = painterResource(id = item.imageRes),
//                contentDescription = item.title,
//                modifier = Modifier.size(32.sdp)
//            )
//            Spacer(modifier = Modifier.height(4.sdp))
//            Text(text = item.title, color = Color.White, fontSize = 12.ssp)
//        }
//    }
//}

@Composable
fun DraggableItem(
    item: DragItem,
    currentIndex: Int,
    maxIndex: Int,
    isDragging: Boolean,
    hoverIndex: Int?,
    draggedOriginalIndex: Int?,
    onDragStart: () -> Unit,
    onDragEnd: (Int) -> Unit,
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
                if (isDragging) Color(0xFFFF5252) else Color(0xFF2196F3),
                RoundedCornerShape(12.sdp)
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
                            // Use dragStartIndex for calculating bounds
                            val maxLeftDrag = -itemWidthPx * dragStartIndex
                            val maxRightDrag = itemWidthPx * (maxIndex - dragStartIndex)

                            offsetX.snapTo(totalDragX.coerceIn(maxLeftDrag, maxRightDrag))
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
