package com.example.nomad.data.local

import android.content.Context
import com.example.nomad.additional.Result
import com.example.nomad.data.local.db.MainDataBase
import com.example.nomad.data.local.entity.FoodTypeEntity
import com.example.nomad.data.local.entity.MainMenuEntity
import com.example.nomad.data.local.entity.ProductEntity
import com.example.nomad.domain.usecase.LanguageController

class NomadDataBase(
    val context: Context
) {

    private val db = MainDataBase.getDataBase(context)
    private val sharedPref = context.getSharedPreferences("ServePercent", Context.MODE_PRIVATE)

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

    suspend fun getProductByPattern(pattern: String): Result {
        val result = when (LanguageController.getLanguage()) {
            LanguageController.Language.RUS -> db.productDao().getProductsRus(pattern)
            LanguageController.Language.KAZ -> db.productDao().getProductsKaz(pattern)
            LanguageController.Language.ENG -> db.productDao().getProductsEng(pattern)
        }
        return if (result.isEmpty()) {
            Result.Failure("Didn't find any product by name")
        } else
            Result.Success(result)
    }

    suspend fun getProductByID(id: Long): Result {
        return try {
            val result = db.productDao().getProductByID(id)
            Result.Success(result)
        } catch (e: Exception) {
            Result.Failure(e.message)
        }
    }

    suspend fun insertPercentageForServe(percent: Long) {
        val editor = sharedPref.edit()
        editor.apply {
            putLong("percent", percent)
            apply()
        }
    }

    suspend fun getPercentageForServe(): Result {
        val percent = sharedPref.getLong("percent", 1)
        return if (percent.toInt() == 1)
            Result.Failure("Local storage empty")
        else
            Result.Success(percent)
    }
}