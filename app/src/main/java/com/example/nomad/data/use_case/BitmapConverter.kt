package com.example.nomad.data.use_case

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.room.TypeConverter
import java.io.ByteArrayOutputStream

class BitmapConverter {

    @TypeConverter
    fun fromBitmap(bitmap: Bitmap?): ByteArray {
        if (bitmap == null) return ByteArray(1)
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        return outputStream.toByteArray()
    }

    @TypeConverter
    fun fromByteArray(byteArray: ByteArray): Bitmap? {
        if (byteArray.contentEquals(ByteArray(1))) return null
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
    }
}