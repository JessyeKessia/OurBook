package com.example.ourbook.di

import androidx.room.Room
import com.example.ourbook.data.local.*
import com.example.ourbook.data.repository.OurBookRepositoryLocal
import com.example.ourbook.data.repository.RepositorioSeed
import com.example.ourbook.ui.screen.rewards.RewardsViewModel
import com.example.ourbook.ui.screen.login.LoginViewModel
import com.example.ourbook.ui.screen.home.HomeViewModel
import com.example.ourbook.ui.screen.notifications.NotificationsViewModel
import com.example.ourbook.ui.screen.loan.LoanRegisterViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import com.example.ourbook.ui.screen.bookdetail.BookDetailViewModel



val appModule = module {

    // 1. Banco de Dados
    single {
        Room.databaseBuilder(
            androidContext(),
            OurBookDatabase::class.java,
            "ourbook_database"
        ).build()
    }

    // 2. DAOs
    single { get<OurBookDatabase>().bookDao() }
    single { get<OurBookDatabase>().notificationDao() }
    single { get<OurBookDatabase>().rewardDao() }
    single { get<OurBookDatabase>().userDao() }

    // 3. Repositório Local
    single {
        OurBookRepositoryLocal(
            userDao = get(),
            bookDao = get(),
            notificationDao = get(),
            rewardDao = get()
        )
    }

    // 4. Repositório Seed
    single {
        RepositorioSeed(
            userDao = get(),
            bookDao = get(),
            rewardDao = get(),
            notificationDao = get()
        )
    }

    // 5. ViewModels
    viewModel { HomeViewModel(repository = get()) }
    viewModel { RewardsViewModel(repository = get()) }
    viewModel { LoginViewModel(repository = get()) }
    viewModel { NotificationsViewModel(repository = get()) }
    viewModel { LoanRegisterViewModel(repository = get()) }
    viewModel { BookDetailViewModel(repository = get()) }
    viewModel { LoanRegisterViewModel(repository = get()) }
}