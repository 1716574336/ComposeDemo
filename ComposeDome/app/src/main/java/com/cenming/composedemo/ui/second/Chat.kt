package com.cenming.composedemo.ui.second

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cenming.composedemo.ui.second.data.Chat
import com.cenming.composedemo.ui.second.theme.WeTheme


@Composable
fun ChatList(viewModel : SecondViewModel = viewModel()){
	Column(Modifier.fillMaxSize()) {
		ChatListTopBar()
		Box(
			Modifier
				.background(WeTheme.colors.background)
				.fillMaxSize()
		) {
			ChatList(viewModel.mChats)
		}
	}
}

@Preview
@Composable
fun ChatListTopBarPreview(){
	WeTopBar(title = "扔信")
}

@Composable
fun ChatListTopBar(){
	WeTopBar(title = "扔信")
}

@Composable
fun ChatList(chats : List<Chat>){
	LazyColumn(
		Modifier
			.background(WeTheme.colors.listItem)
			.fillMaxWidth()
	){
		itemsIndexed(chats) { index, item ->
			ChatListItem(chat = item)
			if(index < chats.size - 1){
				Divider(
					startIndent = 68.dp,
					color = WeTheme.colors.chatListDivider,
					thickness = 0.8F.dp
				)
			}
		}
	}
}

@Composable
fun ChatListItem(
	chat: Chat,
	modifier: Modifier = Modifier,
	viewModel: SecondViewModel = viewModel()
){
	Row(
		modifier
			.fillMaxWidth()
			.clickable { viewModel.startChat(chat) }
	) {
		Image(
			painterResource(chat.friend.avatar),
			contentDescription = "头像", Modifier
				.padding(12.dp, 8.dp, 8.dp, 8.dp)
				.size(48.dp)
				.unread(!chat.msgs.last().read, WeTheme.colors.badge)
				.clip(RoundedCornerShape(4.dp))
		)
		Column(
			Modifier
				.weight(1f)
				.align(Alignment.CenterVertically)
		) {
			Text(
				chat.friend.name,
				fontSize = 17.sp,
				color = WeTheme.colors.textPrimary
			)
			Text(
				chat.msgs.last().text,
				fontSize = 14.sp,
				color = WeTheme.colors.textSecondary
			)
		}
		Text(
			"13:48",
			Modifier.padding(8.dp, 8.dp, 12.dp, 8.dp),
			fontSize = 11.sp,
			color = WeTheme.colors.textSecondary
		)
	}

}