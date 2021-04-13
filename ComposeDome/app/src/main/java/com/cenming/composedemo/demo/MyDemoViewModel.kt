package com.cenming.composedemo.demo

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cenming.composedemo.demo.data.DemoData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


/**
 * Created by Cenming on 2021/4/2.
 * 功能:
 */
class MyDemoViewModel : ViewModel() {

	val mDataList by mutableStateOf<ArrayList<DemoData>>(arrayListOf())

	var mIsEmpty by mutableStateOf(this.mDataList.isEmpty())
	private set

	var mIsRefresh by mutableStateOf(true)
	var mIsLoading by mutableStateOf(true)

	fun getDemoData(isRefresh : Boolean = true){
		val startIndex = if(isRefresh) 0 else this.mDataList.size
		this.mIsRefresh = isRefresh
		this.mIsLoading = !isRefresh
		viewModelScope.launch(Dispatchers.Default) {
			val tempList = arrayListOf<DemoData>()
			for(i in startIndex until startIndex + 30){
				delay(1 * 100)
				tempList.add(DemoData(i, "msg$i").also {
					Log.e("demo", "DemoData = $it")
				})
			}
			if(startIndex == 0) mDataList.clear()
			mDataList.addAll(tempList)
			mIsEmpty = mDataList.isEmpty()
			mIsRefresh = false
			mIsLoading = false
		}
	}


	fun clear(){
		this.mDataList.clear()
		this.mIsEmpty = this.mDataList.isEmpty()
	}

}