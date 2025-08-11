package com.kavi.pbc.droid.lib.common.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.kavi.droid.color.palette.KvColorPalette

@Composable
fun PBCAppTheme(
    isDarkTheme: Boolean = isSystemInDarkTheme(),
    // If you want to ignore the color theme generation and use the dynamic color according to the device colors, then make this true.
    isDynamicColor: Boolean = false,
    appColorScheme: MutableState<ColorScheme?> = remember { mutableStateOf(null) },
    content: @Composable () -> Unit
) {
    val dynamicColor = isDynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S

    appColorScheme.value?.let { colorScheme ->
        MaterialTheme(
            colorScheme = colorScheme,
            typography = AppTypography,
            content = content
        )
    }?: run {
        val newColorScheme = when {
            dynamicColor && isDarkTheme -> {
                dynamicDarkColorScheme(LocalContext.current)
            }
            dynamicColor && !isDarkTheme -> {
                dynamicLightColorScheme(LocalContext.current)
            }
            isDarkTheme -> KvColorPalette.colorSchemeThemePalette.darkColorScheme
            else -> KvColorPalette.colorSchemeThemePalette.lightColorScheme
        }

        MaterialTheme(
            colorScheme = newColorScheme,
            typography = AppTypography,
            content = content
        )
    }
}