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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cenming.composedemo.R
import com.cenming.composedemo.ui.second.data.Module
import com.cenming.composedemo.ui.second.data.User
import com.cenming.composedemo.ui.second.theme.WeTheme


@Composable
fun ContactList(viewModel: SecondViewModel = viewModel()) {
	Column(Modifier.fillMaxSize()) {
		ContactListTopBar()
		Box(
			Modifier
				.background(WeTheme.colors.background)
				.fillMaxSize()
		) {
			ContactList(viewModel.mContacts, viewModel)
		}
	}
}

@Composable
fun ContactList(contacts: List<User>, viewModel: SecondViewModel) {
	LazyColumn(
		Modifier
			.background(WeTheme.colors.listItem)
			.fillMaxWidth()
	) {
		val buttons = listOf(
			User("contact_add", "新的朋友", R.drawable.ic_contact_add),
			User("contact_chat", "仅聊天", R.drawable.ic_contact_chat),
			User("contact_group", "群聊", R.drawable.ic_contact_group),
			User("contact_tag", "标签", R.drawable.ic_contact_tag),
			User("contact_official", "公众号", R.drawable.ic_contact_official),
		)
		itemsIndexed(buttons) { index, item ->
			ContactListItem(item, viewModel)
			if (index < buttons.size - 1) {
				Divider(
					startIndent = 56.dp,
					color = WeTheme.colors.chatListDivider,
					thickness = 0.8f.dp
				)
			}
		}
		item {
			Text(
				"朋友", Modifier
					.background(WeTheme.colors.background)
					.fillMaxWidth()
					.padding(12.dp, 8.dp),
				fontSize = 14.sp,
				color = WeTheme.colors.onBackground
			)
		}
		itemsIndexed(contacts) { index, item ->
			ContactListItem(item, viewModel)
			if(index < buttons.size - 1){
				Divider(
					startIndent = 56.dp,
					color = WeTheme.colors.chatListDivider,
					thickness = 0.8f.dp
				)
			}
		}
	}
}

@Composable
fun ContactListItem(contact: User, viewModel: SecondViewModel){
	Row(
		Modifier
			.fillMaxWidth()
			.clickable {
				if(contact.id == "contact_add"){
					viewModel.startModule(Module.NewFriendStart)
				}
			}
	) {
		Image(
			painterResource(contact.avatar),
			"avatar", Modifier
				.padding(12.dp, 8.dp, 8.dp, 8.dp)
				.size(36.dp)
				.clip(RoundedCornerShape(4.dp))
		)
		Text(
			contact.name, Modifier
				.weight(1f)
				.align(Alignment.CenterVertically),
			fontSize = 17.sp,
			color = WeTheme.colors.textPrimary
		)
	}
}


@Composable
fun ContactListTopBar() {
	WeTopBar(title = "通讯录")
}