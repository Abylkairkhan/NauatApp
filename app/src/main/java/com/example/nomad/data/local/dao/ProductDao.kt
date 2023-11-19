package com.example.nomad.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.nomad.data.local.entity.ProductEntity

@Dao
interface ProductDao {

    @Insert(onConflict = 5)
    suspend fun insertProduct(data: ProductEntity)

    @Query("SELECT * FROM product_table ORDER BY id ASC")
    suspend fun getAllProduct(): List<ProductEntity>

    @Query("SELECT * FROM product_table WHERE food_type = :foodType")
    suspend fun getProductByType(foodType: String): List<ProductEntity>

    @Query("SELECT id FROM product_table WHERE food_type = :foodType ORDER BY id ASC LIMIT 1")
    suspend fun getProductID(foodType: String): Int

    @Query("SELECT * FROM product_table WHERE nameEng LIKE '%' || :searchPattern || '%'")
    suspend fun getProductsEng(searchPattern: String): List<ProductEntity>

    @Query("SELECT * FROM product_table WHERE nameRus LIKE '%' || :searchPattern || '%'")
    suspend fun getProductsRus(searchPattern: String): List<ProductEntity>

    @Query("SELECT * FROM product_table WHERE nameKaz LIKE '%' || :searchPattern || '%'")
    suspend fun getProductsKaz(searchPattern: String): List<ProductEntity>
}