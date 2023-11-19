package com.example.nomad.data.network.models

import com.google.firebase.firestore.DocumentReference

data class FoodTypeNetwork(
    val id: Long,
    val documentId: String,
    val nameEng: String,
    val nameRus: String,
    val nameKaz: String,
    val menuType: DocumentReference?
)