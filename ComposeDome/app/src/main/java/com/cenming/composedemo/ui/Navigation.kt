package com.cenming.composedemo.ui

import android.app.Application
import android.content.Intent
import android.os.Bundle
import androidx.annotation.MainThread
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.core.os.bundleOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.cenming.composedemo.ui.Screen.Home
import com.cenming.composedemo.ui.Screen.Interests
import com.cenming.composedemo.ui.Screen.Article
import com.cenming.composedemo.ui.ScreenName.HOME
import com.cenming.composedemo.ui.ScreenName.INTERESTS
import com.cenming.composedemo.ui.ScreenName.ARTICLE
import com.cenming.composedemo.ui.second.SecondActivity
import com.cenming.composedemo.utils.getMutableStateOf


/**
 * 屏幕名称（用于序列化）
 */
enum class ScreenName { HOME, INTERESTS, ARTICLE }

/**
 * 定义应用程序中屏幕的类：主页，文章详细信息和兴趣
 */
sealed class Screen(val id: ScreenName) {
	object Home : Screen(HOME)
	object Interests : Screen(INTERESTS)
	data class Article(val postId: String) : Screen(ARTICLE)
}

/**
 * 用于将[Screen]对象保存和加载到[Bundle]的助手
 *
 * 这使我们能够在整个过程中持续导航，例如，由于长时间的视频通话而导致的死亡。
 */
private const val SIS_SCREEN = "sis_screen"
private const val SIS_NAME = "screen_name"
private const val SIS_POST = "post"
private const val SIS_BUNDLE = "sis_bundle"

/**
 * 将屏幕转换为可以存储在[SavedStateHandle]中的包
 */
private fun Screen.toBundle(): Bundle {
	return bundleOf(SIS_NAME to id.name, ).also {
		// add extra keys for various types here
		if (this is Article) {
			it.putString(SIS_POST, postId)
		}
	}
}

/**
 * 读取[Screen.toBundle]存储的包并返回所需的屏幕
 *
 * @return 解析的 [Screen]
 * @throws IllegalArgumentException 如果[Bundle]无法解析
 */
private fun Bundle.toScreen(): Screen {
	return when (ScreenName.valueOf(getStringOrThrow(SIS_NAME))) {
		HOME -> Home
		INTERESTS -> Interests
		ARTICLE -> {
			val postId = getStringOrThrow(SIS_POST)
			Article(postId)
		}
	}
}

/**
 * 如果密钥不在[Bundle]中，则抛出[IllegalArgumentException]。
 *
 * @see Bundle.getBundle
 */
private fun Bundle.getBundleOrThrow(key: String) =
		requireNotNull(getBundle(key)) { "Missing key '$key' in $this" }

/**
 * 如果密钥不在[Bundle]中，则抛出[IllegalArgumentException]。
 *
 * @see Bundle.getString
 */
private fun Bundle.getStringOrThrow(key: String) =
		requireNotNull(getString(key)) { "Missing key '$key' in $this" }

/**
 * 预计它将被导航组件取代，但现在可以手动处理导航。
 * 在完全负责导航的范围内实例化此ViewModel，在此应用程序中为[MainActivity]。
 * 这个程序简化了导航；后排堆栈始终为[Screen.Home]或[Home，dest]，并且不允许使用更多级别。要在较长的堆栈中使用类似的模式，请使用[StateList]保持堆栈的状态
 */
class NavigationViewModel (private val mContext: Application, savedStateHandle: SavedStateHandle)
	: AndroidViewModel(mContext){
	/**
	 * 将当前屏幕保持在可观察状态，并在进程终止后从 saveStateHandle 恢复。
	 * mutableStateOf 是类似于 LiveData 的可观察到的对象，旨在通过 compose 读取。
	 * 它通过属性委托语法支持可观察性，如下所示
	 */
	var mCurrentScreen : Screen by savedStateHandle.getMutableStateOf<Screen>(
		key = SIS_SCREEN,
		default = Home,
		save = { it.toBundle() },
		restore = { it.toScreen() }
	)
		private set // 将写入限制为仅在此类内

	/**
	 * 返回（始终返回[Home]）。
	 * 如果此调用引起用户可见的导航，则返回true。当[mCurrentScreen]为[Home]时将始终返回false。
	 */
	@MainThread
	fun onBack():Boolean{
		val wasHandle = this.mCurrentScreen != Home
		this.mCurrentScreen = Home
		return wasHandle
	}

	/**
	 * 导航至请求的[Screen]
	 *
	 * 如果请求的屏幕不是[Home]，它将始终创建具有一个元素的后退堆栈：（[Home]-> [screen]）。
	 * 此应用程序不支持更多的回溯条目。
	 */
	@MainThread
	fun navigateTo(screen: Screen){
		this.mCurrentScreen = screen
	}

	/**
	 * 页面跳转
	 */
	fun startActivity(){
		this.mContext.startActivity(Intent(this.mContext, SecondActivity::class.java))
	}
}