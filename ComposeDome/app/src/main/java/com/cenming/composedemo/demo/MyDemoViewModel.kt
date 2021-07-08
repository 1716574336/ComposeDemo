package com.cenming.composedemo.demo

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.cenming.composedemo.demo.data.DemoData
import com.cenming.composedemo.demo.paging.DemoPagingSource
import kotlinx.coroutines.delay


/**
 * Created by Cenming on 2021/4/2.
 * 功能:
 */
class MyDemoViewModel : ViewModel() {

	var mIsRefresh : MutableState<Boolean> = mutableStateOf(false)

	val mProjects = Pager(PagingConfig(pageSize = 20)){
		DemoPagingSource{ page ->
			getDemoData(page = page)
		}
	}.flow.cachedIn(viewModelScope)


	private suspend fun getDemoData(page : Int) : ArrayList<DemoData>{
//		this.mIsRefresh.value = page == 1
		val tempList = arrayListOf<DemoData>()
		for(i in (page - 1)*20 until page*20){
			delay(5 * 10)
			tempList.add(DemoData(i, "msg$i"))
		}
		// 会触发两次?未找到原因
//		if(this.mIsRefresh.value) this.mIsRefresh.value = false
//		this.mIsRefresh.value = page == 1
//		println("page: $page ; mIsRefresh:${this.mIsRefresh.value}")
		return tempList
	}
}