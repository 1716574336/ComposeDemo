package com.cenming.composedemo.ui.second

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cenming.composedemo.ui.second.theme.WeTheme


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
		if (viewModel.mOpenModule != null) {
			viewModel.endModule()
		} else {
			super.onBackPressed()
		}
	}
}