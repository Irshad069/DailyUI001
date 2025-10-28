package com.dailyui001.ui.di

import com.dailyui001.ui.presentation.dragitem.DragViewModel
import com.dailyui001.ui.presentation.login.SignUpViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { SignUpViewModel() }
    viewModel { DragViewModel() }
}