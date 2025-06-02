package com.dailyui001.ui.di

import android.content.Context
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.module.Module

class DIManager private constructor(private val application: Context) {

    companion object {
        fun initialize(application: Context) {
            DIManager(application)
        }
    }

    init {
        startKoin {
            androidLogger()
            androidContext(application)
            modules(getModules())
        }
    }

    private val repoList
        get() = listOf(
            repoModule
        )

    private val viewModelList
        get() = listOf(
            viewModelModule
        )

    private val moduleList
        get() = listOf(
            repoList,
            viewModelList
        )

    private fun getModules(): List<Module> {
        return moduleList.flatten()
    }
}