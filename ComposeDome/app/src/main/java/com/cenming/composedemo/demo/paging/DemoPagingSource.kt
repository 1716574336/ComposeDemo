package com.cenming.composedemo.demo.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.cenming.composedemo.demo.data.DemoData


/**
 * Created by Cenming on 2021/4/16.
 * 功能:
 */
class DemoPagingSource constructor(
	private val pagedDataFetcher: suspend (Int) -> List<DemoData>
): PagingSource<Int, DemoData>() {

	override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DemoData> {
		val page = params.key ?: 1
		val data = pagedDataFetcher(page)
		return LoadResult.Page(
			data = data,
			prevKey = if (page == 1) null else page,
			nextKey = if (data.isEmpty()) null else page + 1
		)
	}

	override fun getRefreshKey(state: PagingState<Int, DemoData>): Int? = null
}