package com.example.nomad.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.firestore.DocumentReference

@Entity(tableName = "food_type_table")
data class FoodTypeEntity (
    @PrimaryKey
    val id: Long,
    val documentId: String,
    val nameEng: String,
    val nameRus: String,
    val nameKaz: String,
    val menuType: DocumentReference?
)