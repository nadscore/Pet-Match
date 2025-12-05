package com.example.petmatch.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Base64
import java.io.ByteArrayOutputStream

object ImageUtils {

    fun uriToBase64(context: Context, uri: Uri): String? {
        return try {
            // 1. Abre o arquivo da imagem
            val inputStream = context.contentResolver.openInputStream(uri)
            val bitmap = BitmapFactory.decodeStream(inputStream)

            // 2. Comprime a imagem
            val scaledBitmap = Bitmap.createScaledBitmap(bitmap, 800, 800, true)

            // 3. Converte para Bytes
            val byteArrayOutputStream = ByteArrayOutputStream()
            scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 70, byteArrayOutputStream)
            val byteArray = byteArrayOutputStream.toByteArray()

            // 4. Converte Bytes para String Base64
            // O prefixo "data:image/jpeg;base64," ajuda o Coil a entender que é uma imagem
            "data:image/jpeg;base64," + Base64.encodeToString(byteArray, Base64.NO_WRAP)

        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}