package com.cenming.composedemo

import android.app.Application
import com.cenming.composedemo.data.AppContainer
import com.cenming.composedemo.data.AppContainerImpl


/**
 * Created by Cenming on 2021/3/3.
 * 功能:
 */
class JetNewApp : Application() {

	lateinit var mContainer: AppContainer

	override fun onCreate() {
		super.onCreate()
		this.mContainer = AppContainerImpl()
	}
}