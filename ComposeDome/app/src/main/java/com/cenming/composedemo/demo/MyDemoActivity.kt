package com.cenming.composedemo.demo

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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
			MyTheme {
				val viewModel = viewModel<MyDemoViewModel>()

				DemoDataContent(
					viewModel = viewModel,
					emptyContentLayout = { lazyItems ->
						Box(
							modifier = Modifier.fillMaxSize(),
							contentAlignment = Alignment.Center
						) {
							Button(onClick = {
								lazyItems.refresh()
							}) {
								Text(text = "暂无数据,点击刷新!")
							}
						}
					},
					onRefresh = {
						it.refresh()
					},
					refreshLayout = { Text(text = "获取数据中...") },
					itemErrorLayout = { lazyItems ->
						Row(
							modifier = Modifier
								.fillMaxWidth()
								.padding(8.dp),
							verticalAlignment = Alignment.CenterVertically,
							horizontalArrangement = Arrangement.Center,
						){
							Button(onClick = {
								lazyItems.retry()
							}) {
								Text(text = "重试")
							}
						}

					},
					itemLoadingLayout = {
						Text(text = "加载中...")
					},
					errorLayout = { lazyItems ->
						Box(
							modifier = Modifier.fillMaxSize(),
							contentAlignment = Alignment.Center
						) {
							Button(onClick = {
								lazyItems.refresh()
							}) {
								Text(text = "加载出错")
							}
						}
					},
					loadingLayout = {
						Box(
							modifier = Modifier.fillMaxSize(),
							contentAlignment = Alignment.Center
						) {
							Text(text = "loading...")
						}
					}
				)
			}
		}
	}
}