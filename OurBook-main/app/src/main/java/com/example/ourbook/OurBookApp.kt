package com.example.ourbook

import android.app.Application
import com.example.ourbook.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import android.app.Application

class OurBookApp : Application() {
    override fun onCreate() {
        super.onCreate()

        // Inicialização do Koin
        startKoin {
            androidContext(this@OurBookApp) //
            modules(appModule) //
        }
    }
