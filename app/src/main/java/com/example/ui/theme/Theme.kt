package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.config.SchoolConfig

@Composable
fun SchoolAppTheme(
    schoolConfig: SchoolConfig = SchoolConfig(),
    content: @Composable () -> Unit
) {
    val primary = schoolConfig.primaryColor
    val secondary = schoolConfig.secondaryColor

    val lightColorScheme = lightColorScheme(
        primary = primary,
        onPrimary = Color.White,
        primaryContainer = primary.copy(alpha = 0.10f),
        onPrimaryContainer = primary,
        secondary = secondary,
        onSecondary = Color.White,
        secondaryContainer = secondary.copy(alpha = 0.15f),
        onSecondaryContainer = Color(0xFF78350F),
        tertiary = Color(0xFF0D9488),
        onTertiary = Color.White,
        background = SchoolBackground,
        onBackground = TextPrimary,
        surface = SchoolSurface,
        onSurface = TextPrimary,
        surfaceVariant = SchoolSurfaceSubtle,
        onSurfaceVariant = TextSecondary,
        outline = SchoolBorder,
        outlineVariant = SchoolBorderFocus,
        error = StatusDanger,
        onError = Color.White
    )

    MaterialTheme(
        colorScheme = lightColorScheme,
        typography = Typography,
        content = content
    )
}
