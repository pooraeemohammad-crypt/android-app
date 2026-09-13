package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection

private val DarkColorScheme = darkColorScheme(
    primary = GoldPrimary,
    onPrimary = BackgroundDark,
    primaryContainer = GoldMuted,
    onPrimaryContainer = GoldLight,
    secondary = TechCyan,
    onSecondary = BackgroundDark,
    secondaryContainer = TechCyanMuted,
    onSecondaryContainer = TechCyan,
    tertiary = GoldLight,
    onTertiary = BackgroundDark,
    background = BackgroundDark,
    onBackground = TextWhite,
    surface = SurfaceDark,
    onSurface = TextWhite,
    surfaceVariant = SurfaceVariantDark,
    onSurfaceVariant = TextLight,
    outline = BorderSubtle,
    outlineVariant = BorderActive,
    error = StatusError,
    onError = TextWhite
)

@Composable
fun MohammadBrandTheme(
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        MaterialTheme(
            colorScheme = DarkColorScheme,
            typography = Typography,
            content = content
        )
    }
}
