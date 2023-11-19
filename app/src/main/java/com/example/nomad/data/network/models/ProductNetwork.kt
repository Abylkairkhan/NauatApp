package com.example.nomad.data.network.models

import com.google.firebase.firestore.DocumentReference

data class ProductNetwork (
    var id: Long,
    var nameEng: String,
    var nameRus: String,
    var nameKaz: String,
    var overviewEng: String,
    var overviewRus: String,
    var overviewKaz: String,
    var image: String,
    var price: Long,
    var food_type: DocumentReference
)