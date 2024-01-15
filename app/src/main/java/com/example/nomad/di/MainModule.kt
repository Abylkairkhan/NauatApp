package com.example.nomad.di

import com.example.nomad.data.local.NomadDataBase
import com.example.nomad.data.network.INomadNetwork
import com.example.nomad.data.network.NomadNetwork
import com.example.nomad.data.repository.IMenuRepository
import com.example.nomad.data.repository.MenuRepositoryImpl
import com.example.nomad.domain.usecase.GetFoodTypeByType
import com.example.nomad.domain.usecase.GetMainMenuData
import com.example.nomad.domain.usecase.GetProductByID
import com.example.nomad.domain.usecase.GetProductByPattern
import com.example.nomad.domain.usecase.GetProductByType
import com.example.nomad.domain.usecase.GetProducts
import com.example.nomad.domain.usecase.InsertLocalData
import com.example.nomad.presentation.detail.ProductDetailViewModel
import com.example.nomad.presentation.main.MainViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainModule = module {

    single<IMenuRepository> { MenuRepositoryImpl(get(), get()) }

    single { MenuRepositoryImpl(get(), get()) }

    single { NomadDataBase(androidContext()) }

    single<INomadNetwork> { NomadNetwork() }

    single { GetFoodTypeByType(get()) }

    single { GetMainMenuData(get()) }

    single { GetProductByPattern(get()) }

    single { GetProductByType(get()) }

    single { GetProducts(get()) }

    single { GetProductByID(get()) }

    single { InsertLocalData(get()) }

    viewModel { MainViewModel(get(), get(), get(), get(), get(), get()) }

    viewModel { ProductDetailViewModel(get()) }

}