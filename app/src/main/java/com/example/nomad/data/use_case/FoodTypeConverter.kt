package com.example.nomad.data.use_case

import com.example.nomad.data.local.entity.FoodTypeEntity
import com.example.nomad.data.network.models.FoodTypeNetwork
import com.example.nomad.domain.models.FoodTypeModel

object FoodTypeConverter {

    fun entityToModel(entity: FoodTypeEntity): FoodTypeModel {
        return FoodTypeModel(
            id = entity.id,
            documentId = entity.documentId,
            nameEng = entity.nameEng,
            nameRus = entity.nameRus,
            nameKaz = entity.nameKaz,
            menuType = entity.menuType
        )
    }

    fun modelToEntity(model: FoodTypeModel): FoodTypeEntity {
        return FoodTypeEntity(
            id = model.id,
            documentId = model.documentId,
            nameEng = model.nameEng,
            nameRus = model.nameRus,
            nameKaz = model.nameKaz,
            menuType = model.menuType
        )
    }

    fun networkToModel(network: FoodTypeNetwork): FoodTypeModel {
        return FoodTypeModel(
            id = network.id,
            documentId = network.documentId,
            nameEng = network.nameEng,
            nameRus = network.nameRus,
            nameKaz = network.nameKaz,
            menuType = network.menuType
        )
    }

}