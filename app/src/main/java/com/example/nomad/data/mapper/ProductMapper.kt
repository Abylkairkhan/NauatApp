package com.example.nomad.data.mapper

import com.example.nomad.data.local.entity.ProductEntity
import com.example.nomad.domain.models.ProductModel

fun ProductEntity.toProductModel(): ProductModel {
    return ProductModel(
        id = id,
        nameEng = nameEng,
        nameRus = nameRus,
        nameKaz = nameKaz,
        overviewEng = overviewEng,
        overviewRus = overviewRus,
        overviewKaz = overviewKaz,
        image = image,
        price = price,
        foodType = foodType
    )
}

fun ProductModel.toProductEntity(): ProductEntity {
    return ProductEntity(
        id = id,
        nameEng = nameEng,
        nameRus = nameRus,
        nameKaz = nameKaz,
        overviewEng = overviewEng,
        overviewRus = overviewRus,
        overviewKaz = overviewKaz,
        image = image,
        price = price,
        foodType = foodType
    )
}