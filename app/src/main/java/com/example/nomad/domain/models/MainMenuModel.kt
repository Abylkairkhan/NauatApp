package com.example.nomad.domain.models

import android.graphics.Bitmap

data class MainMenuModel(
    val id: Long,
    val documentId: String,
    val nameEng: String,
    val nameRus: String,
    val nameKaz: String,
    val image: Bitmap
)
