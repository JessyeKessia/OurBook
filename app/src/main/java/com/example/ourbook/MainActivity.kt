package com.example.ourbook

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.ourbook.navigation.OurBookNavHost
import com.example.ourbook.ui.theme.OurBookTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OurBookTheme {
                OurBookNavHost()
            }
        }
    }
}