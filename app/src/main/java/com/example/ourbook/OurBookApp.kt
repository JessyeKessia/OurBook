package com.example.ourbook

import android.app.Application
import com.example.ourbook.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class OurBookApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            // Inicializa o contexto do Android para o Koin
            androidContext(this@OurBookApp)
            // Carrega as definições de dependência do seu appModule
            modules(appModule)
        }
    }
}