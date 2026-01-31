package com.example.ourbook.di

import androidx.room.Room
import com.example.ourbook.data.local.* // Puxa a Database e os DAOs
import com.example.ourbook.data.repository.OurBookRepositoryLocal // Importe do local novo
import com.example.ourbook.ui.screen.rewards.RewardsViewModel
import com.example.ourbook.ui.screen.login.LoginViewModel
import com.example.ourbook.ui.screen.home.HomeViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    // 1. Banco de Dados
    single {
        Room.databaseBuilder(
            androidContext(),
            OurBookDatabase::class.java,
            "ourbook_database"
        ).build()
    }

    // 2. DAOs (Agora vindo da OurBookDatabase)
    single { get<OurBookDatabase>().bookDao() }
    single { get<OurBookDatabase>().notificationDao() }
    single { get<OurBookDatabase>().rewardDao() }
    single { get<OurBookDatabase>().userDao() }

    // 3. O Repositório (Passando os DAOs injetados acima)
    single {
        OurBookRepositoryLocal(
            userDao = get(),
            bookDao = get(),
            notificationDao = get(),
            rewardDao = get()
        )
    }

    // 4. ViewModels (Usando o Repositório Local)
    viewModel { HomeViewModel(repository = get()) }
    viewModel { RewardsViewModel(repository = get()) }
    viewModel { LoginViewModel(repository = get()) }
}