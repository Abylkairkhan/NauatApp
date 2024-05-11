package com.example.nomad.data.network

import com.example.nomad.additional.Result

interface INomadNetwork {

    suspend fun getMainMenu(): Result

    suspend fun getFoodTypeByType(documentID: String): Result

    suspend fun getFoodType(): Result

    suspend fun getProductByType(foodTypeID: String): Result

    suspend fun getAllProducts(): Result

    suspend fun getPercentageForServe(): Result

    suspend fun insertProduct()

}