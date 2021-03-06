package com.cenming.composedemo.ui.weChat

import androidx.annotation.DrawableRes
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cenming.composedemo.ui.weChat.theme.WeTheme
import com.cenming.composedemo.R
import com.cenming.composedemo.ui.weChat.data.Module
import com.cenming.composedemo.ui.weChat.second.SecondViewModel


@Composable
fun Home() {
	val viewModel = viewModel<SecondViewModel>()

	val homeOffset by animateFloatAsState(viewModel.getOffsetFloat(Module.Home))
	Box(Modifier.percentOffsetX(homeOffset)) {
		Column(
			Modifier
				.fillMaxSize()
		) {
			val pagerState: PagerState = run {
				remember(viewModel.mTheme){ PagerState(maxPage = 3) }
			}
			Pager(
				pagerState,
				Modifier.weight(1f)
			){
				when(page){
					0 -> ChatList()
					1 -> ContactList()
					2 -> DiscoveryList()
					3 -> MeList()
				}
			}
			HomeBottomBar(pagerState.currentPage){
				pagerState.currentPage = it
			}
		}
	}

	val chatOffset by animateFloatAsState(viewModel.getOffsetFloat(Module.Chat))
	viewModel.mCurrentChat?.let {
		ChatPage(
			Modifier.percentOffsetX(chatOffset),
			chat = viewModel.mCurrentChat
		) {
			viewModel.endModule()
		}
	}


	val newFriendOffset by animateFloatAsState(viewModel.getOffsetFloat(Module.NewFriend))
	NewFriend(
		Modifier.percentOffsetX(newFriendOffset),
		viewModel.mNewFriend,
		onBack = {
			viewModel.endModule()
		},
		onAddFriend = {
			viewModel.startModule(Module.AddFriend)
		},
		toPhoneAddressList = {

		},
		onSearchNumber = {
			viewModel.startModule(Module.SearchByNum)
		}
	)

	val addFriendOffset by animateFloatAsState(viewModel.getOffsetFloat(Module.AddFriend))
	AddFriend(
		Modifier.percentOffsetX(addFriendOffset),
		onBack = {
			viewModel.endModule(Module.NewFriend)
		},
		onSearchNumber = {
			viewModel.startModule(Module.SearchByNum)
		}
	)

	val searchByNumOffset by animateFloatAsState(viewModel.getOffsetFloat(Module.SearchByNum))
	SearchNumber(
		Modifier.percentOffsetX(searchByNumOffset),
		viewModel = viewModel,
		onBack = { viewModel.endModule() }
	)
}

@Composable
fun HomeBottomBar(current: Int, currentChanged : (Int) -> Unit) {
	WeBottomBar{
		HomeBottomItem(
			Modifier
				.weight(1f)
				.clickable { currentChanged(0) },
			if(current == 0) R.drawable.ic_chat_filled else R.drawable.ic_chat_outlined,
			"??????",
			IconColor(current == 0)
		)
		HomeBottomItem(
			Modifier
				.weight(1f)
				.clickable { currentChanged(1) },
			if(current == 1) R.drawable.ic_contacts_filled else R.drawable.ic_contacts_outlined,
			"?????????",
			IconColor(current == 1)
		)
		HomeBottomItem(
			Modifier
				.weight(1f)
				.clickable { currentChanged(2) },
			if(current == 2) R.drawable.ic_discover_filled else R.drawable.ic_discover_outlined,
			"??????",
			IconColor(current == 2)
		)
		HomeBottomItem(
			Modifier
				.weight(1f)
				.clickable { currentChanged(3) },
			if(current == 3) R.drawable.ic_me_filled else R.drawable.ic_me_outlined,
			"??????",
			IconColor(current == 3)
		)
	}
}

@Preview(showBackground = true)
@Composable
fun HomeBottomBarPreview() {
	var current by mutableStateOf(0)
	HomeBottomBar(current){ current = it }
}

@Composable
fun HomeBottomItem(
	modifier: Modifier = Modifier,
	@DrawableRes iconId: Int,
	title: String,
	tint: Color
){
	Column(
		modifier.padding(0.dp, 8.dp, 0.dp, 8.dp),
		horizontalAlignment = Alignment.CenterHorizontally
	) {
		Icon(painterResource(iconId), title, Modifier.size(24.dp), tint)
		Text(title, fontSize = 11.sp, color = tint)
	}
}

/**
 * ???????????????????????????
 */
@Composable
fun IconColor(isCurrent : Boolean) : Color =
	if(isCurrent) WeTheme.colors.iconCurrent else WeTheme.colors.icon