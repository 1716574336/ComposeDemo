package com.cenming.composedemo.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.cenming.composedemo.JetNewApp


/**
 * Created by Cenming on 2021/3/4.
 * 功能:
 */
class MainActivity : AppCompatActivity() {

	private val mViewModel by viewModels<NavigationViewModel>()

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		val appContainer = (this.application as JetNewApp).mContainer

		setContent {
			JetNewApp(appContainer, this.mViewModel)
		}
	}

	override fun onBackPressed() {
		if(!this.mViewModel.onBack()){
			super.onBackPressed()
		}
	}
}