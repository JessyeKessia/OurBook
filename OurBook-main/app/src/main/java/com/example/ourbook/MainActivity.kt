package com.example.ourbook

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.ourbook.navigation.OurBookNavHost
import com.example.ourbook.ui.theme.OurBookTheme
import com.example.ourbook.data.repository.RepositorioSeed

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Chama a função para popular dados
        // lifecycleScope.launch {
        //    val seed = RepositorioSeed()
        //    seed.seedAll()
        //}

        setContent {
            OurBookTheme {
                OurBookNavHost()
            }
        }
    }
}