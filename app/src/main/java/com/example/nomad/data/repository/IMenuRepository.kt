package com.example.nomad.data.repository

import android.content.Context
import com.example.nomad.additional.Result
import com.example.nomad.domain.models.FoodTypeModel
import com.example.nomad.domain.models.ProductModel

interface IMenuRepository {

    suspend fun fetchMainMenu(context: Context): Result

    suspend fun fetchFoodType(): Result

    suspend fun fetchProduct(context: Context): Result

    suspend fun fetchFoodTypeByType(type: String): MutableList<FoodTypeModel>

    suspend fun getFoodTypePosition(documentID: String): Int

    suspend fun fetchProductByPattern(pattern: String): MutableList<ProductModel>

    suspend fun insert()
}