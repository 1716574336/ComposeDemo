package com.cenming.composedemo.demo.myTheme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val blue = Color(0xFF2772F3)
val blueDark = Color(0xFF0B182E)

val Purple300 = Color(0xFFCD52FC)
val Purple700 = Color(0xFF8100EF)

@Composable
fun MyTheme(
	darkTheme: Boolean = isSystemInDarkTheme(),
	content: @Composable () -> Unit
){
	val color = if (darkTheme) myThemeDark else myThemeLight

	MaterialTheme(
		colors = color,
		typography = typography,
		content = content
	)
}

private val myThemeLight = lightColors(
	primary = blue,
	onPrimary = Color.White,
	primaryVariant = blue,
	secondary = blue
)

private val myThemeDark = darkColors(
	primary = blueDark,
	onPrimary = Color.White,
	primaryVariant = blueDark,
	secondary = blueDark
)
