package com.example.mad.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val AgriColors = lightColorScheme(
    primary = Color(0xFF245C3A),
    onPrimary = Color(0xFFFFFFFF),
    primaryContainer = Color(0xFFD7EAD9),
    onPrimaryContainer = Color(0xFF173D27),
    secondary = Color(0xFF657D45),
    onSecondary = Color(0xFFFFFFFF),
    tertiary = Color(0xFFB9782E),
    background = Color(0xFFF5F7F1),
    surface = Color(0xFFFFFFFF),
    onSurface = Color(0xFF202A21),
    onSurfaceVariant = Color(0xFF657067),
    outline = Color(0xFFDCE3D9)
)

@Composable
fun AgriTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = AgriColors,
        content = content
    )
}
