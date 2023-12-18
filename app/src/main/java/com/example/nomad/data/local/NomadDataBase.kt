package com.example.nomad.data.local

import android.content.Context
import com.example.nomad.additional.Result
import com.example.nomad.data.local.db.MainDataBase
import com.example.nomad.data.local.entity.FoodTypeEntity
import com.example.nomad.data.local.entity.MainMenuEntity
import com.example.nomad.data.local.entity.ProductEntity
import com.example.nomad.domain.use_case.LanguageController

class NomadDataBase(
    val context: Context
) {

    private val db = MainDataBase.getDataBase(context)

    suspend fun getMainMenuEng(): Result {
        val result = db.mainMenuDao().getAllMainMenu()
        return if (result.isEmpty()) {
            return Result.Failure("Local DataBase is Empty")
        } else Result.Success(result)
    }

    suspend fun insertMainMenu(data: MainMenuEntity) {
        db.mainMenuDao().insertMainMenuItem(data)
    }

    suspend fun getFoodTypeByType(type: String): Result {
        val result = db.foodTypeDao().getFoodTypeByType(type)
        return if (result.isEmpty()) {
            return Result.Failure("Local DataBase is Empty")
        } else Result.Success(result)
    }

    suspend fun insertFoodType(data: FoodTypeEntity) {
        db.foodTypeDao().insertFoodTypeItem(data)
    }

    suspend fun getProductByType(productID: String): Result {
        val result = db.productDao().getProductByType(productID)
        return if (result.isEmpty()) {
            return Result.Failure("Local DataBase is Empty")
        } else Result.Success(result)
    }

    suspend fun getAllProducts(): Result {
        val result = db.productDao().getAllProduct()
        return if (result.isEmpty()) {
            return Result.Failure("Local DataBase is Empty")
        } else Result.Success(result)
    }

    suspend fun insertProduct(data: ProductEntity) {
        db.productDao().insertProduct(data)
    }

    suspend fun getFoodTypePosition(documentID: String): Int {
        return db.productDao().getProductID("FoodTypeEnglish/$documentID")
    }

    suspend fun getProductByPattern(pattern: String): Result {
        val result = when (LanguageController.getLanguage()) {
            LanguageController.Language.RUS -> db.productDao().getProductsRus(pattern)
            LanguageController.Language.KAZ -> db.productDao().getProductsKaz(pattern)
            LanguageController.Language.ENG -> db.productDao().getProductsEng(pattern)
        }
        return Result.Success(result)
    }
}