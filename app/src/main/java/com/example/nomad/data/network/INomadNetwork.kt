package com.example.nomad.data.network

import com.example.nomad.additional.Result

interface INomadNetwork {

    suspend fun getMainMenu() : Result

    suspend fun getFoodType(): Result

    //    Думаю нужно удалить
    suspend fun getProductByType(id: String): Result
    suspend fun insertProduct()

    suspend fun getAllProducts(): Result

}