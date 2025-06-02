package com.dailyui001

import android.app.Application
import com.dailyui001.ui.di.DIManager

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        DIManager.initialize(this)
    }
}