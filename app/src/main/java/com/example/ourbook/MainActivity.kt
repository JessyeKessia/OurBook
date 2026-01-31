package com.example.ourbook

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.lifecycleScope
import com.example.ourbook.data.repository.RepositorioSeed
import com.example.ourbook.navigation.OurBookNavHost
import com.example.ourbook.ui.theme.OurBookTheme
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject // Necessário para o inject funcionar

class MainActivity : ComponentActivity() {

    // Deixe o Koin entregar o Seed configurado
    private val seed: RepositorioSeed by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Rodamos o Seed dentro de uma coroutine
        lifecycleScope.launch {
            seed.seedAll()
        }

        setContent {
            OurBookTheme {
                OurBookNavHost()
            }
        }
    }
}