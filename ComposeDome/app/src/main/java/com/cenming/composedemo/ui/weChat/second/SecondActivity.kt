package com.cenming.composedemo.ui.weChat.second

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cenming.composedemo.ui.weChat.Home
import com.cenming.composedemo.ui.weChat.data.Module
import com.cenming.composedemo.ui.weChat.percentOffsetX
import com.cenming.composedemo.ui.weChat.theme.WeTheme
import dev.chrisbanes.accompanist.insets.statusBarsPadding


/**
 * Created by Cenming on 2021/3/5.
 * 功能:通过compose模仿微信做个界面
 */
class SecondActivity : AppCompatActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContent {
			val viewModel = viewModel<SecondViewModel>()
			WeTheme(theme = viewModel.mTheme) {
				Home()
			}
		}
	}

	override fun onBackPressed() {
		val viewModel: SecondViewModel by viewModels()
		if (viewModel.mOpenModule != Module.Home) {
			viewModel.endModule()
		} else {
			super.onBackPressed()
		}
	}
}