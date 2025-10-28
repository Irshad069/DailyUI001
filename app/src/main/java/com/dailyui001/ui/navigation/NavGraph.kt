package com.dailyui001.ui.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dailyui001.ui.presentation.dragitem.DragRowScreen
import com.dailyui001.ui.presentation.dragitem.DragViewModel
import com.dailyui001.ui.presentation.login.SignUpScreen
import com.dailyui001.ui.presentation.login.SignUpViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun SetUpNavGraph(
    navController: NavHostController = rememberNavController()
) {
    val startDestination = Screens.DragItemDisplay
    NavHost(
        navController = navController,
        startDestination = startDestination,

        enterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(700)
            )
        },
        exitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(700)
            )
        },
        popEnterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(700)
            )
        },
        popExitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(700)
            )
        }
    ) {
        composable<Screens.LoginDisplay> {
            val viewModel: SignUpViewModel = koinViewModel()
            SignUpScreen(
                baseUIEvents = viewModel.baseUIEvents,
                navigation = {
                    handleNavigation(
                        action = it,
                        navController = navController
                    )
                },
                state = viewModel.state,
                actionEvent = viewModel::actionEvent
            )
        }
        composable<Screens.DragItemDisplay> {
            val viewModel: DragViewModel = koinViewModel()
            DragRowScreen(
                baseUIEvents = viewModel.baseUIEvents,
                navigation = {
                    handleNavigation(
                        action = it,
                        navController = navController
                    )
                },
                state = viewModel.state,
                intent = viewModel::intent
            )
        }
    }
}