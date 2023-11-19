package com.example.nomad.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.nomad.data.local.entity.FoodTypeEntity

@Dao
interface FoodTypeDao {

    @Insert(onConflict = 5)
    suspend fun insertFoodTypeItem(data: FoodTypeEntity)

    @Query("SELECT * FROM food_type_table ORDER BY id ASC")
    suspend fun getAllFoodType(): List<FoodTypeEntity>

    @Query("SELECT * FROM food_type_table WHERE menuType = :menuType")
    suspend fun getFoodTypeByType(menuType: String): List<FoodTypeEntity>
}