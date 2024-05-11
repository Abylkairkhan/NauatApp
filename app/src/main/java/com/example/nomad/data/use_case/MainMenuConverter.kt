package com.example.nomad.data.use_case

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import com.example.nomad.data.local.entity.MainMenuEntity
import com.example.nomad.data.network.models.MainMenuNetwork
import com.example.nomad.domain.models.MainMenuModel

object MainMenuConverter {

    fun entityToModel(entity: MainMenuEntity): MainMenuModel {
        return MainMenuModel(
            id = entity.id,
            documentId = entity.documentId,
            nameEng = entity.nameEng,
            nameRus = entity.nameRus,
            nameKaz = entity.nameKaz,
            image = entity.image
        )
    }

    fun modelToEntity(model: MainMenuModel): MainMenuEntity {
        return MainMenuEntity(
            id = model.id,
            documentId = model.documentId,
            nameEng = model.nameEng,
            nameRus = model.nameRus,
            nameKaz = model.nameKaz,
            image = model.image
        )
    }

    suspend fun networkToModel(network: MainMenuNetwork, context: Context): MainMenuModel {
        val image = ImageConverter.getBitmap(context, network.image)
        return MainMenuModel(
            id = network.id,
            documentId = network.documentId,
            nameEng = network.nameEng,
            nameRus = network.nameRus,
            nameKaz = network.nameKaz,
            image = image!!
        )
    }
}