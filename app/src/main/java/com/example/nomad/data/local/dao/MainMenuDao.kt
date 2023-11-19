package com.example.nomad.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.nomad.data.local.entity.MainMenuEntity

@Dao
interface MainMenuDao {

    @Insert(onConflict = 5)
    suspend fun insertMainMenuItem(data: MainMenuEntity)

    @Query("SELECT * FROM main_menu_table ORDER BY id ASC")
    suspend fun getAllMainMenu(): MutableList<MainMenuEntity>
}