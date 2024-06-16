package com.pixelperfectsoft.tcg_nexus.ui.theme

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

public val PrimaryBlue = Color(92, 115, 255)
public val Light_PrimaryRed = Color(255,92,92)
public val Light_PrimaryGreen = Color(92, 255, 92)
public val Light_PrimaryYellow = Color(254, 255, 92)


fun createGradientBrush(colors: List<Color>, isVertical: Boolean = true): Brush {
    val endOffset = if (isVertical) {
        Offset(0f, Float.POSITIVE_INFINITY)
    } else {
        Offset(Float.POSITIVE_INFINITY, 0f)
    }
    return Brush.linearGradient(
        colors = colors,
        start = Offset.Zero,
        end = endOffset,
        tileMode = TileMode.Clamp
    )
}