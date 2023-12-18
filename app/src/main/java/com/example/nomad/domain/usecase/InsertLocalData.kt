package com.example.nomad.domain.usecase

import com.example.nomad.data.local.entity.FoodTypeEntity
import com.example.nomad.data.local.entity.MainMenuEntity
import com.example.nomad.data.local.entity.ProductEntity
import com.example.nomad.data.repository.IMenuRepository

class InsertLocalData(
    private val repository: IMenuRepository
) {
    suspend fun insertMainMenuItem(mainMenuEntity: MainMenuEntity) = repository.insertMainMenuDataBase(mainMenuEntity)

    suspend fun insertFoodTypeItem(foodTypeEntity: FoodTypeEntity) = repository.insertFoodTypeDataBase(foodTypeEntity)

    suspend fun insertProductItem(productEntity: ProductEntity) = repository.insertProductDataBase(productEntity)
}