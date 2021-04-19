package com.cenming.composedemo.demo

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemsIndexed
import com.cenming.composedemo.demo.data.DemoData
import com.cenming.composedemo.ui.SwipeToRefreshLayout


/**
 * @param viewModel MyDemoViewModel
 * @param itemErrorLayout 上拉加载出错后的布局 PS：最好加个点击事件重新进行上拉加载处理
 * @param itemLoadingLayout 上拉加载加载中的布局
 * @param errorLayout 获取初始数据出错的布局
 * @param loadingLayout 获取初始数据时的加载界面
 */
@Composable
fun DemoDataContent(
	viewModel: MyDemoViewModel,
	emptyContentLayout: @Composable (LazyPagingItems<DemoData>) -> Unit,
	onRefresh: (LazyPagingItems<DemoData>) -> Unit,
	refreshLayout: @Composable () -> Unit,
	itemErrorLayout: @Composable (LazyPagingItems<DemoData>) -> Unit,
	itemLoadingLayout: @Composable () -> Unit,
	errorLayout: @Composable (LazyPagingItems<DemoData>) -> Unit,
	loadingLayout: @Composable () -> Unit,
) {
	val items = viewModel.mProjects.collectAsLazyPagingItems()
	RefreshLayout(
		refreshingState = viewModel.mIsRefresh,
		empty = items.itemCount == 0,
		emptyContent = { emptyContentLayout(items) },
		onRefresh = { onRefresh(items) },
		refreshLayout = { refreshLayout() }
	) {
		when (items.loadState.refresh) {
			is LoadState.NotLoading -> LazyColumn(
				Modifier.fillMaxSize()
			) {
				demoDataItemIndex(items)
				when (items.loadState.append) {
					is LoadState.NotLoading -> demoDataItemIndex(items)
					is LoadState.Error -> item { itemErrorLayout(items) }
					is LoadState.Loading -> item { itemLoadingLayout() }
				}
			}
			is LoadState.Error -> errorLayout(items)
			is LoadState.Loading -> loadingLayout()
		}
	}
}

@Composable
fun RefreshLayout(
	refreshingState: Boolean,
	empty: Boolean,
	emptyContent: @Composable () -> Unit,
	onRefresh: () -> Unit,
	refreshLayout: @Composable () -> Unit,
	content: @Composable () -> Unit
) {
	if (empty) {
		emptyContent()
	} else {
		SwipeToRefreshLayout(
			refreshingState = refreshingState, onRefresh = onRefresh,
			refreshIndicator = refreshLayout, content = content
		)
	}
}

fun LazyListScope.demoDataItemIndex(
	dataList: LazyPagingItems<DemoData>
) {
	itemsIndexed(dataList) { index: Int, value: DemoData? ->
		value?.let { data ->
			DemoDataItem(data)
			if (index < dataList.itemCount - 1) {
				Divider(color = MaterialTheme.colors.onSurface)
			}
		}
	}
}

@Composable
fun DemoDataItem(item: DemoData) {
	Row(
		Modifier
			.fillMaxWidth()
			.padding(30.dp)
	) {
		Text(
			text = item.id.toString(), modifier = Modifier.align(Alignment.CenterVertically)
		)
		Spacer(modifier = Modifier.weight(1f))

		Text(
			text = item.msg, modifier = Modifier.align(Alignment.CenterVertically)
		)
	}
}
