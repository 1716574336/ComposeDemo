package com.cenming.composedemo.ui.weChat.second

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.cenming.composedemo.R
import com.cenming.composedemo.ui.weChat.data.*
import com.cenming.composedemo.ui.weChat.theme.WeTheme


class SecondViewModel : ViewModel() {

	var mTheme by mutableStateOf(WeTheme.Theme.Light)

	var mOpenModule: Module by mutableStateOf(Module.Home)
	private var mLastModule: ArrayList<Module> by mutableStateOf(arrayListOf())

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

	private val mPhoneList = arrayListOf(
		User(
			id = "pUser0", name = "pUser0",
			avatar = R.drawable.avatar_diuwuxian,
			phone = "13600000000"
		),
		User(
			id = "pUser1", name = "pUser1",
			avatar = R.drawable.avatar_gaolaoshi,
			phone = "13610000001"
		),
		User(
			id = "pUser2", name = "pUser2",
			avatar = R.drawable.avatar_diuwuxian,
			phone = "13620000001"
		),
		User(
			id = "pUser3", name = "pUser3",
			avatar = R.drawable.avatar_gaolaoshi,
			phone = "13630000003"
		),
		User(
			id = "pUser4", name = "pUser4",
			avatar = R.drawable.avatar_diuwuxian,
			phone = "13640000004"
		),
		User(
			id = "pUser5", name = "pUser5",
			avatar = R.drawable.avatar_gaolaoshi,
			phone = "13650000005"
		),
		User(
			id = "pUser6", name = "pUser6",
			avatar = R.drawable.avatar_diuwuxian,
			phone = "13660000006"
		),
		User(
			id = "pUser7", name = "pUser7",
			avatar = R.drawable.avatar_gaolaoshi,
			phone = "13670000007"
		),
		User(
			id = "pUser8", name = "pUser8",
			avatar = R.drawable.avatar_diuwuxian,
			phone = "13680000008"
		),
		User(
			id = "pUser9", name = "pUser9",
			avatar = R.drawable.avatar_gaolaoshi,
			phone = "13690000009"
		)
	)

	fun getPhoneList(str : String): ArrayList<User> {
		val phoneList = arrayListOf<User>()
		for (item in this.mPhoneList){
			val length = str.length
			val subStr = item.phone.substring(0, length)
			if(subStr == str){
				phoneList.add(item)
			}
		}
		return phoneList
	}

	private fun getNewFriend(): List<User> {
		val list = ArrayList<User>()
		for (i in 0 until 10) {
			list.add(
				User(
					"id$i", "新朋友$i", if (i % 2 == 0) {
						R.drawable.avatar_diuwuxian
					} else {
						R.drawable.avatar_gaolaoshi
					}, "remarks$i", when (i % 3) {
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

	fun startModule(module: Module) {
		this.mLastModule.add(this.mOpenModule)
		this.mOpenModule = module
	}

	fun endModule(module: Module = this.getLastModule()) {
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
		if(this.mLastModule.size > 0){
			this.mLastModule.last().let {
				if (it == module) return -1F
			}
		}
		if (this.mOpenModule == module) return 0F
		return 1F
	}

	/**
	 * 获取最后一个 Module
	 */
	private fun getLastModule(): Module = this.mLastModule.last()
}