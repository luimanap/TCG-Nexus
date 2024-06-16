package com.pixelperfectsoft.tcg_nexus.ui.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

enum class PasswordStrength(val percentage: Float) {
    WEAK(0.25f),
    MEDIUM(0.50f),
    STRONG(0.75f),
    VERY_STRONG(1.0f)
}

@Composable
fun PasswordStrengthBar(password: String) {
    val strength = calculatePasswordStrength(password)
    val strengthColor = when (strength) {
        PasswordStrength.WEAK -> Color.Red
        PasswordStrength.MEDIUM -> Color.Yellow
        PasswordStrength.STRONG -> Color.Green
        PasswordStrength.VERY_STRONG -> Color.Blue
    }
    val strengthText = when (strength) {
        PasswordStrength.WEAK -> "Weak"
        PasswordStrength.MEDIUM -> "Medium"
        PasswordStrength.STRONG -> "Strong"
        PasswordStrength.VERY_STRONG -> "Very Strong"
    }

    Column(modifier = Modifier.padding(horizontal = 30.dp, vertical = 4.dp)) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(5.dp)
                .background(Color.LightGray)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(strength.percentage)
                    .height(5.dp)
                    .background(strengthColor)
            )
        }
        Text(
            text = "Strength: $strengthText",
            style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 12.sp),
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}



fun calculatePasswordStrength(password: String): PasswordStrength {
    var score = 0

    if (password.length >= 6) score++
    if (password.length >= 8) score++
    if (password.any { it.isDigit() }) score++
    if (password.any { it.isUpperCase() }) score++
    if (password.any { "!@#\$%^&*()-_=+[{]}\\|;:'\",<.>/?`~".contains(it) }) score++

    return when (score) {
        0, 1 -> PasswordStrength.WEAK
        2, 3 -> PasswordStrength.MEDIUM
        4 -> PasswordStrength.STRONG
        else -> PasswordStrength.VERY_STRONG
    }
}