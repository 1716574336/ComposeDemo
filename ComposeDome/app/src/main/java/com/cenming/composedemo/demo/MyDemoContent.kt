package com.cenming.composedemo.demo

import android.os.Handler
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cenming.composedemo.demo.data.DemoData
import com.cenming.composedemo.ui.SwipeToRefreshAndLoadingLayout


@Composable
fun MyDemoContent(viewModel: MyDemoViewModel) {
	var isRefresh by remember { mutableStateOf(false) }
	var isLoading by remember { mutableStateOf(false) }
	Scaffold {
		LoadingAndRefresh(
			refreshingState = isRefresh,
			loadState = isLoading,
			empty = viewModel.mIsEmpty,
			emptyContent = {
				Box(
					Modifier.fillMaxSize()
				) {
					Text(
						text = "no data",
						fontSize = 17.sp,
						modifier = Modifier
							.align(Alignment.Center)
							.clickable { viewModel.getDemoData() }
					)
				}
			},
			onRefresh = {
				isRefresh = true
				viewModel.getDemoData()
				isRefresh = false
			},
			onLoading = {
				isLoading = true
				viewModel.getDemoData(false)
				isLoading = false
			},
			refreshLayout = {
				Surface(elevation = 10.dp, shape = CircleShape) {
					CircularProgressIndicator(
						modifier = Modifier
							.size(36.dp)
							.padding(4.dp),
						color = Color.DarkGray
					)
				}
			},
			loadingLayout = {
				Surface(elevation = 10.dp, shape = CircleShape) {
					Row {
						CircularProgressIndicator(
							modifier = Modifier
								.size(36.dp)
								.padding(4.dp),
							color = Color.Yellow
						)
						Text(text = "加载ing....")
					}
				}
			}
		) {
			LazyColumn {
				itemsIndexed(viewModel.mDataList) { index, item ->
					DemoDataItem(item)
					if(index < viewModel.mDataList.size - 1){
						Divider(color = MaterialTheme.colors.onSurface)
					}
				}
			}
		}
	}
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MyDemoContents(
	viewModel: MyDemoViewModel
){

	val state = rememberLazyListState()

	if (state.isScrollInProgress){
		// state.firstVisibleItemIndex 屏幕中显示内容的第一个 Item 的坐标
		// state.firstVisibleItemScrollOffset 屏幕中显示内容的第一个 Item 的偏移值，值越小，偏移越小
		if((state.firstVisibleItemIndex == 0) && (state.firstVisibleItemScrollOffset == 0)){
			viewModel.mIsRefresh = true
		}
		if(viewModel.mIsRefresh){
			viewModel.getDemoData()
		}
		if(viewModel.mIsLoading){
			viewModel.getDemoData(false)
		}
		Log.d("demo", "isRefresh = ${viewModel.mIsRefresh} \n" +
			"state = { \n" +
			"firstVisibleItemIndex = ${state.firstVisibleItemIndex}; \n" +
			"firstVisibleItemScrollOffset = ${state.firstVisibleItemScrollOffset}; \n" +
			/*"interactionSource = ${state.interactionSource}; \n" +
			"layoutInfo = { \n" +
			"viewportStartOffset = ${state.layoutInfo.viewportStartOffset} \n" +
			"totalItemsCount = ${state.layoutInfo.totalItemsCount} \n" +
			"viewportEndOffset = ${state.layoutInfo.viewportEndOffset} \n" +
			"visibleItemsInfo = ${state.layoutInfo.visibleItemsInfo} \n" +
			"}; \n" +*/
			"} \n")
	}
	LazyColumn(
		state = state
	) {
		if(viewModel.mIsRefresh){
			item{
				Surface(elevation = 10.dp, shape = CircleShape) {
					CircularProgressIndicator(
						modifier = Modifier
							.size(36.dp)
							.padding(4.dp),
						color = Color.DarkGray
					)
				}
			}
		}
		itemsIndexed(viewModel.mDataList) { index, item ->
			DemoDataItem(item)
			if(index < viewModel.mDataList.size - 1){
				Divider(color = MaterialTheme.colors.onSurface)
			}
		}
		if(viewModel.mIsLoading){
			item{
				Surface(elevation = 10.dp, shape = CircleShape) {
					Row {
						CircularProgressIndicator(
							modifier = Modifier
								.size(36.dp)
								.padding(4.dp),
							color = Color.Yellow
						)
						Text(text = "加载ing....")
					}
				}
			}
		}
	}
}

@Composable
fun LoadingAndRefresh(
	refreshingState: Boolean,
	loadState: Boolean,
	empty: Boolean,
	emptyContent: @Composable () -> Unit,
	onRefresh: () -> Unit,
	onLoading: () -> Unit,
	refreshLayout: @Composable () -> Unit,
	loadingLayout: @Composable () -> Unit,
	content: @Composable () -> Unit
) {
	if (empty) {
		emptyContent()
	} else {
//		SwipeToRefreshAndLoadLayout(
		SwipeToRefreshAndLoadingLayout(
			refreshingState = refreshingState,
			loadingState = loadState,
			onRefresh = onRefresh,
			onLoading = onLoading,
			refreshLayout = refreshLayout,
			loadingLayout = loadingLayout,
			content = content
		)
	}
}

@Composable
fun DemoDataItem(item : DemoData){
	Row(
		Modifier
			.fillMaxWidth()
			.padding(30.dp)
	) {
		Text(
			text = item.id.toString(),
			modifier = Modifier.align(Alignment.CenterVertically)
		)
		Spacer(modifier = Modifier.weight(1f))

		Text(
			text = item.msg,
			modifier = Modifier.align(Alignment.CenterVertically)
		)
	}
}
