package com.example.nomad.domain.models

import android.graphics.Bitmap
import com.google.firebase.firestore.DocumentReference

data class ProductModel(
    var id: Long,
    var nameEng: String,
    var nameRus: String,
    var nameKaz: String,
    var overviewEng: String,
    var overviewRus: String,
    var overviewKaz: String,
    var image: Bitmap?,
    var price: Long,
    var foodType: DocumentReference,
    var countInBucket: Int = 0
)