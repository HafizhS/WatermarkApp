package com.example.watermarkapp.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Picture
import android.os.Environment
import java.io.File


fun Picture.toBitmap(): Bitmap {
    val bitmap = Bitmap.createBitmap(
        this.width,
        this.height,
        Bitmap.Config.ARGB_8888
    )

    val canvas = android.graphics.Canvas(bitmap)
    canvas.drawColor(android.graphics.Color.WHITE)
    canvas.drawPicture(this)
    return bitmap
}

fun File.writeBitmap(bitmap: Bitmap, format: Bitmap.CompressFormat, quality: Int) {
    outputStream().use { out ->
        bitmap.compress(format, quality, out)
        out.flush()
    }
}

fun Bitmap.saveToDisk() {
    val file = File(
        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
        "att-${System.currentTimeMillis()}.png"
    )

    file.writeBitmap(this, Bitmap.CompressFormat.PNG, 100)
}
