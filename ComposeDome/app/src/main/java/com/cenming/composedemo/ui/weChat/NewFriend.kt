package com.cenming.composedemo.ui.weChat

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.colorspace.ColorSpaces
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cenming.composedemo.R
import com.cenming.composedemo.ui.weChat.data.AddState
import com.cenming.composedemo.ui.weChat.data.User
import com.cenming.composedemo.ui.weChat.theme.WeTheme
import com.cenming.composedemo.ui.weChat.theme.black5


@Composable
fun NewFriend(
	modifier: Modifier = Modifier,
	newFriends: List<User>,
	onBack: () -> Unit,
	onAddFriend: () -> Unit,
	toPhoneAddressList: () -> Unit,
	onSearchNumber : () -> Unit
) {
	LazyColumn(
		modifier
			.background(WeTheme.colors.background)
			.fillMaxWidth()
			.fillMaxHeight()
	) {
		item {
			WeFriendTopBar("新的朋友", onBack = onBack, onAddFriend = onAddFriend)
			WeFriendSearch(onSearchNumber = onSearchNumber)
			NewFriendPhone(toPhoneAddressList)
		}
		newFriendContent(
			this@LazyColumn,
			newFriends,
			onFriendInfo = {

			},
			onAddClick = {

			}
		)
	}
}

@Composable
fun NewFriendPhone(onPhoneAddressList: () -> Unit) {
	Box(
		Modifier
			.padding(0.dp, 1.dp, 0.dp, 0.dp)
			.clickable(onClick = onPhoneAddressList)
			.fillMaxWidth()
			.background(color = Color(0xFF949393).convert(ColorSpaces.CieXyz))
	) {
		Row(
			Modifier
				.fillMaxWidth()
				.background(Color.White)
				.padding(20.dp, 15.dp)
		) {
			val text = "添加手机联系人"
			Icon(
				painter = painterResource(id = R.drawable.ic_phone), contentDescription = text,
				Modifier.align(Alignment.CenterVertically), tint = Color(0xFF1afa29)
			)

			Text(
				text = text,
				modifier = Modifier
					.align(Alignment.CenterVertically)
					.padding(10.dp, 0.dp),
				fontSize = 17.sp
			)

			Spacer(modifier = Modifier.weight(1F))

			Icon(
				painter = painterResource(id = R.drawable.ic_back_right), contentDescription = text,
				modifier = Modifier
					.align(Alignment.CenterVertically)
					.width(10.dp)
					.height(18.dp),
				tint = WeTheme.colors.more
			)
		}
	}
}

fun newFriendContent(
	lazyColumn : LazyListScope,
	newFriends: List<User>,
	onFriendInfo:(User) -> Unit,
	onAddClick:(User) -> Unit
) {
	lazyColumn.item {
		Text(
			text = "三天前",
			Modifier.padding(20.dp, 10.dp),
			fontSize = 20.sp
		)
	}
	lazyColumn.itemsIndexed(newFriends) { index, item ->
		NewFriendItem(item, onFriendInfo, onAddClick)
		if (index < newFriends.size - 1) {
			Divider(
				startIndent = 90.dp,
				color = WeTheme.colors.chatListDivider,
				thickness = 0.8F.dp
			)
		}
	}
}

@Composable
fun NewFriendItem(
	newFriend: User,
	onFriendInfo:(User) -> Unit,
	onAddClick:(User) -> Unit
) {
	Row(Modifier
			.fillMaxWidth()
			.clickable { onFriendInfo(newFriend) }
			.background(WeTheme.colors.listItem)
			.padding(end = 12.dp)
	) {
		val fontSizeDefault = 17.sp;
		Image(
			painter = painterResource(id = newFriend.avatar),
			contentDescription = newFriend.name, Modifier
				.padding(10.dp)
				.size(48.dp)
				.clip(RoundedCornerShape(4.dp))
		)

		Column(
			Modifier
				.weight(1f)
				.align(Alignment.CenterVertically)
		) {
			Text(
				text = newFriend.name,
				fontSize = fontSizeDefault,
				color = WeTheme.colors.textPrimary
			)
			Text(
				text = newFriend.remarks,
				fontSize = 14.sp,
				color = WeTheme.colors.textSecondary
			)
		}

		when (newFriend.addState) {
			AddState.Default -> {
			}
			AddState.Add -> Text(
				text = "添加", Modifier
					.clickable { onAddClick(newFriend) }
					.align(Alignment.CenterVertically)
					.clip(RoundedCornerShape(10))
					.background(Color(0xFF1afa29))
					.padding(10.dp, 4.dp, 10.dp, 4.dp),
				fontSize = fontSizeDefault,
				color = black5
			)
			AddState.Added -> Text(
				text = "已添加", Modifier
					.align(Alignment.CenterVertically)
					.padding(8.dp, 8.dp, 0.dp, 8.dp),
				fontSize = fontSizeDefault,
				color = WeTheme.colors.textSecondary
			)
			AddState.Expired -> Text(
				text = "已过期", Modifier
					.align(Alignment.CenterVertically)
					.padding(8.dp, 8.dp, 0.dp, 8.dp),
				fontSize = fontSizeDefault,
				color = WeTheme.colors.textSecondary
			)
		}
	}
}

@Preview
@Composable
fun NewFriendTopBarPreview(){
	Text(
		text = "添加朋友",
		Modifier
			.background(Color.White)
			.width(100.dp)
			.height(60.dp)
			.wrapContentHeight(),
		color = WeTheme.colors.textPrimary,
		fontSize = 20.sp,
		textAlign = TextAlign.Center,
	)
}

@Preview
@Composable
fun addTextDemo() {
	Text(
		text = "添加", Modifier
			.clip(RoundedCornerShape(10))
			.background(Color(0xFF1afa29))
			.padding(10.dp, 4.dp, 10.dp, 4.dp), fontSize = 17.sp, color = black5
	)
}
