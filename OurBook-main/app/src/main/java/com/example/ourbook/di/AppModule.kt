package com.example.ourbook.di

import androidx.room.Room
import com.example.ourbook.data.local.OurBookDatabase
import com.example.ourbook.data.remote.OurBookRepositoryRemote
import com.example.ourbook.ui.screen.home.HomeViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    // 1. Provedor do Banco de Dados Room
    single {
        Room.databaseBuilder(
            androidContext(), // Usa o contexto da aplicação
            OurBookDatabase::class.java,
            "ourbook_database"
        ).build()
    }

    // 2. Provedor do DAO (Essencial para a ViewModel acessar o Room)
    single { get<OurBookDatabase>().bookDao() }

    // 3. Provedor do Repositório (Fonte de dados remota/Firebase)
    single { OurBookRepositoryRemote }

    // 4. Declaração da ViewModel
    viewModel {
        HomeViewModel(
            remoteRepository = get(), // O Koin injeta o OurBookRepositoryRemote aqui
            bookDao = get()           // O Koin injeta o BookDao aqui
        )
    }
}