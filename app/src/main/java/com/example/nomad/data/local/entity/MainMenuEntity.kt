package com.example.nomad.data.local.entity

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "main_menu_table")
data class MainMenuEntity(
    @PrimaryKey
    val id: Long,
    val documentId: String,
    val nameEng: String,
    val nameRus: String,
    val nameKaz: String,
    val image: Bitmap
)
