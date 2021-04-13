package com.cenming.composedemo.demo

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cenming.composedemo.demo.myTheme.MyTheme


/**
 * Created by Cenming on 2021/4/1.
 * 功能:
 */
class MyDemoActivity : AppCompatActivity(){

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		
		this.setContent {
			val viewModel = viewModel<MyDemoViewModel>()
			viewModel.getDemoData()
			MyTheme {
				MyDemoContent(viewModel)
			}
		}
	}
}