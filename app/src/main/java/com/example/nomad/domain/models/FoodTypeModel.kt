package com.example.nomad.domain.models

import com.google.firebase.firestore.DocumentReference

data class FoodTypeModel(
    val id: Long,
    val documentId: String,
    val nameEng: String,
    val nameRus: String,
    val nameKaz: String,
    val menuType: DocumentReference?
)
