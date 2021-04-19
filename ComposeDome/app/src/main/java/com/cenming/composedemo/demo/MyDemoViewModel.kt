package com.cenming.composedemo.demo

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.cenming.composedemo.demo.data.DemoData
import com.cenming.composedemo.demo.paging.DemoPagingSource
import com.cenming.composedemo.ui.weChat.Pager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


/**
 * Created by Cenming on 2021/4/2.
 * 功能:
 */
class MyDemoViewModel : ViewModel() {

	val mProjects = Pager(PagingConfig(pageSize = 20)){
		DemoPagingSource{ page ->
			getDemoData(page = page)
		}
	}.flow.cachedIn(viewModelScope)

	var mIsRefresh by mutableStateOf(false)

	private suspend fun getDemoData(page : Int) : ArrayList<DemoData>{
		this.mIsRefresh = page == 1
		val tempList = arrayListOf<DemoData>()
		for(i in (page - 1)*20 until page*20){
			delay(5 * 100)
			tempList.add(DemoData(i, "msg$i"))
		}
		if(this.mIsRefresh) this.mIsRefresh = false
		return tempList
	}
}