package com.example.nomad.data.repository

import android.content.Context
import com.example.nomad.additional.Result
import com.example.nomad.data.local.entity.FoodTypeEntity
import com.example.nomad.data.local.entity.MainMenuEntity
import com.example.nomad.data.local.entity.ProductEntity
import com.example.nomad.domain.models.ProductModel

interface IMenuRepository {

    suspend fun fetchMainMenu(context: Context): Result

    suspend fun fetchFoodTypeByType(type: String): Result

    suspend fun fetchProductByType(type: String, context: Context): Result

    suspend fun insertMainMenuDataBase(mainMenuEntity: MainMenuEntity)

    suspend fun insertFoodTypeDataBase(foodTypeEntity: FoodTypeEntity)

    suspend fun insertProductDataBase(productEntity: ProductEntity)

    suspend fun fetchProduct(context: Context): Result

    suspend fun fetchProductByPattern(pattern: String): MutableList<ProductModel>

    suspend fun fetchProductByID(id: Long, context: Context): Result

    suspend fun fetchPercentageForServe(): Result

    suspend fun insert()
}