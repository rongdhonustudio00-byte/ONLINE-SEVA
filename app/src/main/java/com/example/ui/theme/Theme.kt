package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme =
  darkColorScheme(
    primary = RongdhonuPurpleLight,
    onPrimary = Color.White,
    primaryContainer = RongdhonuPurpleDark,
    onPrimaryContainer = RongdhonuPurpleContainer,
    secondary = RongdhonuAccentCyan,
    onSecondary = Color.Black,
    surface = Color(0xFF1E1729),
    onSurface = Color(0xFFECE5F2),
    background = Color(0xFF15101F),
    onBackground = Color(0xFFECE5F2),
    surfaceVariant = Color(0xFF2C233B),
    onSurfaceVariant = Color(0xFFCCC2D9),
  )

private val LightColorScheme =
  lightColorScheme(
    primary = RongdhonuPurplePrimary,
    onPrimary = Color.White,
    primaryContainer = RongdhonuPurpleContainer,
    onPrimaryContainer = RongdhonuOnPurpleContainer,
    secondary = RongdhonuAccentCyan,
    onSecondary = Color.White,
    tertiary = RongdhonuAccentOrange,
    background = SurfaceCanvas,
    onBackground = TextPrimary,
    surface = SurfaceCard,
    onSurface = TextPrimary,
    surfaceVariant = Color(0xFFF1EEF8),
    onSurfaceVariant = TextSecondary,
    outline = BorderSubtle,
  )

@Composable
fun MyApplicationTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  dynamicColor: Boolean = false, // Keep consistent Rongdhonu purple branding
  content: @Composable () -> Unit,
) {
  val colorScheme =
    when {
      dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
        val context = LocalContext.current
        if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
      }
      darkTheme -> DarkColorScheme
      else -> LightColorScheme
    }

  MaterialTheme(colorScheme = colorScheme, typography = Typography, content = content)
}
