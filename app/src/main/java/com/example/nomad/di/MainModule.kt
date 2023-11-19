package com.example.nomad.di

import com.example.nomad.data.local.NomadDataBase
import com.example.nomad.data.network.INomadNetwork
import com.example.nomad.data.network.NomadNetwork
import com.example.nomad.data.repository.IMenuRepository
import com.example.nomad.data.repository.MenuRepositoryImpl
import com.example.nomad.presentation.main.MainViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainModule = module {

    single<IMenuRepository> { MenuRepositoryImpl(get(), get()) }

    single { MenuRepositoryImpl(get(), get()) }

    single { NomadDataBase(androidContext()) }

    single<INomadNetwork> { NomadNetwork() }

    viewModel { MainViewModel(get()) }

}