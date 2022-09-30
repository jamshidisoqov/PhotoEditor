package uz.gita.photoeditor.ui.tools.emoji

import android.graphics.Point
import androidx.annotation.DrawableRes

data class Emoji(
    val name: String,
    @DrawableRes
    val res: Int,
    val emojiPosition: Point
    = Point(0, 0),
    var tag: Int = 0
)