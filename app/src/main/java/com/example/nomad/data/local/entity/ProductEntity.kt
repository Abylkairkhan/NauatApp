package com.example.nomad.data.local.entity

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.firestore.DocumentReference

@Entity(tableName = "product_table")
data class ProductEntity(
    @PrimaryKey
    var id: Long,
    var nameEng: String,
    var nameRus: String,
    var nameKaz: String,
    var overviewEng: String,
    var overviewRus: String,
    var overviewKaz: String,
    var image: Bitmap?,
    var price: Long,
    var food_type: DocumentReference
)