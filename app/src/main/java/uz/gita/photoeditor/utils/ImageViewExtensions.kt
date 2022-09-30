package uz.gita.photoeditor.utils

import android.graphics.Bitmap
import android.graphics.Color
import android.widget.ImageView
import androidx.core.graphics.blue
import androidx.core.graphics.green
import androidx.core.graphics.red


// Created by Jamshid Isoqov an 9/30/2022


fun ImageView.toBitmapImageView(color: String) {

    val d = this.drawable
    val src = Bitmap.createBitmap(d.intrinsicWidth, d.intrinsicHeight, Bitmap.Config.ARGB_8888);

    val color = Color.parseColor("#FF0000")
    val red = color.red
    val green = color.green
    val blue = color.blue

    val width = src.width
    val height = src.height
    val bmOut = Bitmap.createBitmap(width, height, src.config)

    var pixel = 0

    var A = 0
    var R = 0
    var G = 0
    var B = 0


    for (x in 0 until width) {
        for (y in 0 until height) {
            pixel = src.getPixel(x, y)
            A = Color.alpha(pixel)
            R = (Color.red(pixel) * red)
            G = (Color.green(pixel) * green)
            B = (Color.blue(pixel) * blue)
            bmOut.setPixel(x, y, Color.argb(A, R, G, B))
        }
    }
    this.setImageBitmap(bmOut)
}