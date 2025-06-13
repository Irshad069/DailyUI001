package com.dailyui001

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.dailyui001.ui.navigation.SetUpNavGraph
import com.dailyui001.ui.theme.DailyUI001Theme
import org.koin.compose.KoinContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DailyUI001Theme {
                val navController = rememberNavController()
                KoinContext {
                    SetUpNavGraph(
                        navController = navController
                    )
                }
            }
        }
    }
}
