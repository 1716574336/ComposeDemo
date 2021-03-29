package com.cenming.composedemo.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cenming.composedemo.ui.NavigationViewModel


/**
 * Created by Cenming on 2021/3/4.
 * 功能:主题处理
 * MaterialTheme：将 XML主题转换为 Compose
 */
@Composable
fun JetNewsTheme(
	darkTheme: Boolean = isSystemInDarkTheme(),
	content: @Composable () -> Unit
){
	MaterialTheme(
		colors = if(darkTheme) DarkThemeColors else LightThemeColors,
		typography = JetnewsTypography,
		shapes = JetnewsShapes,
		content = content
	)
}

/**
 * 浅色主题颜色色
 */
private val LightThemeColors = lightColors(
	primary = Red700,
	primaryVariant = Red900,
	onPrimary = Color.White,
	secondary = Red700,
	secondaryVariant = Red900,
	onSecondary = Color.White,
	error = Red800,
)

/**
 * 深色主题颜色色
 */
private val DarkThemeColors = darkColors(
	primary = Red300,
	primaryVariant = Red700,
	onPrimary = Color.Black,
	secondary = Red300,
	onSecondary = Color.White,
	error = Red200
)