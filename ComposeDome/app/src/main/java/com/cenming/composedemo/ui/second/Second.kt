package com.cenming.composedemo.ui.second

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.cenming.composedemo.R
import com.cenming.composedemo.ui.second.data.*
import com.cenming.composedemo.ui.second.theme.WeTheme


class SecondViewModel : ViewModel() {

	var mTheme by mutableStateOf(WeTheme.Theme.Light)

	var mOpenModule: Module? by mutableStateOf(null)
	var mLastModule: ArrayList<Module> by mutableStateOf(ArrayList())

	var mCurrentChat: Chat? by mutableStateOf(null)
		private set


	val mContacts by mutableStateOf(
		listOf(
			User("gaolaoshi", "高老师", R.drawable.avatar_gaolaoshi),
			User("diuwuxian", "丢物线", R.drawable.avatar_diuwuxian)
		)
	)

	var mChats by mutableStateOf(
		listOf(
			Chat(
				friend = User("gaolaoshi", "高老师", R.drawable.avatar_gaolaoshi), mutableStateListOf(
					Msg(User("gaolaoshi", "高老师", R.drawable.avatar_gaolaoshi), "锄禾日当午"),
					Msg(User.Me, "汗滴禾下土"),
					Msg(User("gaolaoshi", "高老师", R.drawable.avatar_gaolaoshi), "谁知盘中餐"),
					Msg(User.Me, "粒粒皆辛苦"),
					Msg(
						User("gaolaoshi", "高老师", R.drawable.avatar_gaolaoshi),
						"唧唧复唧唧，木兰当户织。不闻机杼声，惟闻女叹息。"
					),
					Msg(User.Me, "双兔傍地走，安能辨我是雄雌？"),
					Msg(User("gaolaoshi", "高老师", R.drawable.avatar_gaolaoshi), "床前明月光，疑是地上霜。"),
					Msg(User.Me, "吃饭吧？"),
				)
			),
			Chat(
				friend = User("diuwuxian", "丢物线", R.drawable.avatar_diuwuxian), mutableStateListOf(
					Msg(User("diuwuxian", "丢物线", R.drawable.avatar_diuwuxian), "哈哈哈"),
					Msg(User.Me, "你笑个屁呀"),
				)
			),
		)
	)

	var mNewFriend by mutableStateOf(
		getNewFriend()
	)

	private fun getNewFriend(): List<User> {
		val list = ArrayList<User>()
		for (i in 0 until 10) {
			list.add(
				User(
					"id$i", "新朋友$i", if (i % 2 == 0) {
						R.drawable.avatar_diuwuxian
					} else {
						R.drawable.avatar_gaolaoshi
					}, "remarks$i",
					when(i % 3){
						0 -> AddState.Add
						1 -> AddState.Added
						2 -> AddState.Expired
						else -> AddState.Default
					}
				)
			)
		}
		return list
	}


	fun startChat(chat: Chat) {
		this.mCurrentChat = chat
		startModule(Module.Chat)
	}

	fun startModule(module: Module){
		if(this.mOpenModule != null){
			this.mLastModule.add(this.mOpenModule!!)
		}
		this.mOpenModule = module
	}

	fun endModule(module: Module? = null) {
		this.mOpenModule = module
		this.mLastModule.remove(module)
	}

	fun read(chat: Chat) {
		for (msg in chat.msgs) {
			msg.read = true
		}
	}

	fun boom(chat: Chat) {
		chat.msgs.add(Msg(User.Me, "\uD83D\uDCA3").apply { read = true })
	}

	/**
	 * 根距[Module]获取偏移值
	 */
	fun getOffsetFloat(module: Module): Float {
		this.mOpenModule ?: return 1F
		if(this.mLastModule.size > 0){
			this.mLastModule[this.mLastModule.size - 1].let {
				if(it == module) return -1F
			}
		}
		if(this.mOpenModule == module) return 0F
		return 1F
	}
}