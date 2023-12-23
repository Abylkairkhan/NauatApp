package com.example.nomad.data.use_case

import android.content.Context
import com.example.nomad.data.local.entity.ProductEntity
import com.example.nomad.data.network.models.ProductNetwork
import com.example.nomad.domain.models.ProductModel

object ProductConverter {

    fun entityToModel(entity: ProductEntity): ProductModel {
        return ProductModel(
            id = entity.id,
            nameEng = entity.nameEng,
            nameRus = entity.nameRus,
            nameKaz = entity.nameKaz,
            overviewEng = entity.overviewEng,
            overviewRus = entity.overviewRus,
            overviewKaz = entity.overviewKaz,
            image = entity.image,
            price = entity.price,
            foodType = entity.foodType
        )
    }

    fun modelToEntity(model: ProductModel): ProductEntity {
        return ProductEntity(
            id = model.id,
            nameEng = model.nameEng,
            nameRus = model.nameRus,
            nameKaz = model.nameKaz,
            overviewEng = model.overviewEng,
            overviewRus = model.overviewRus,
            overviewKaz = model.overviewKaz,
            image = model.image,
            price = model.price,
            foodType = model.foodType
        )
    }

    suspend fun networkToModel(network: ProductNetwork, context: Context): ProductModel {
        val image = ImageConverter.getBitmap(context, network.image)
        return ProductModel(
            id = network.id,
            nameEng = network.nameEng,
            nameRus = network.nameRus,
            nameKaz = network.nameKaz,
            overviewEng = network.overviewEng,
            overviewRus = network.overviewRus,
            overviewKaz = network.overviewKaz,
            image = image,
            price = network.price,
            foodType = network.foodType
        )
    }

}