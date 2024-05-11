package com.example.nomad.data.use_case

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import coil.ImageLoader
import coil.request.ErrorResult
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.example.nomad.additional.Credentials

object ImageConverter {

    suspend fun getBitmap(context: Context, url: String): Bitmap? {

        if (url.isEmpty()) return null

        val loading = ImageLoader(context)
        val request = ImageRequest.Builder(context)
            .data(Credentials.BASE_URL + url)
            .build()

        return when (val result = loading.execute(request)) {
            is SuccessResult -> (result.drawable as BitmapDrawable).bitmap
            is ErrorResult -> null
        }
    }
}