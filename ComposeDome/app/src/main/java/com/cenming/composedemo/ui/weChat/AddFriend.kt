package com.cenming.composedemo.ui.weChat

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cenming.composedemo.R
import com.cenming.composedemo.ui.weChat.data.AddFriendManner
import com.cenming.composedemo.ui.weChat.data.User
import com.cenming.composedemo.ui.weChat.theme.WeTheme

@Composable
fun AddFriend(
	modifier: Modifier = Modifier,
	onBack: () -> Unit,
	onSearchNumber : () -> Unit
) {
	val mannerList = arrayListOf(
		AddFriendManner(icon = R.drawable.ic_radar,
			functionName = "雷达加朋友",msg = "添加身边的朋友"),
		AddFriendManner(icon = R.drawable.ic_contact_group,
			functionName = "面对面建群",msg = "与身边的朋友进入同一个群聊"),
		AddFriendManner(icon = R.drawable.ic_sweep,
			functionName = "扫一扫",msg = "扫描二维码名片"),
		AddFriendManner(icon = R.drawable.ic_contact_add,
			functionName = "手机联系人",msg = "添加或邀请通讯录钟的朋友"),
		AddFriendManner(icon = R.drawable.ic_contact_official,
			functionName = "公众号",msg = "获取更多资讯和服务"),
		AddFriendManner(icon = R.drawable.ic_enterprise,
			functionName = "企业微信联系人",msg = "通过手机号搜索企业微信用户"),
	)
	Box(
		modifier
			.background(WeTheme.colors.background)
			.fillMaxWidth()
			.fillMaxHeight()
	) {
		LazyColumn {
			item {
				WeFriendTopBar(msg = "添加朋友", onBack = onBack, onAddFriend = null)
				WeFriendSearch(onSearchNumber)
				AddFriendAccountNum()
				Spacer(modifier = Modifier.height(30.dp))
			}
			itemsIndexed(mannerList){ _, manner ->
				AddFriendFunctionItem(
					icon = manner.icon,
					functionName = manner.functionName,
					msg = manner.msg
				) { }
			}
		}
	}
}


@Composable
fun AddFriendAccountNum(modifier : Modifier = Modifier) {
	Row(
		modifier
			.fillMaxWidth()
			.padding(top = 10.dp)
		) {
		Spacer(modifier = Modifier.weight(1F))
		Text(
			text = "我的微信号：${User.Me.accountNum}",
			color = WeTheme.colors.onBackground,
			fontSize = 18.sp
		)
		Icon(
			painterResource(R.drawable.ic_qrcode), contentDescription = "qrcode", Modifier
				.align(Alignment.CenterVertically)
				.padding(start = 10.dp)
				.size(28.dp),
			tint = WeTheme.colors.onBackground
		)
		Spacer(modifier = Modifier.weight(1F))
	}
}

@Composable
fun AddFriendFunctionItem(
	@DrawableRes icon: Int, functionName: String, msg: String, onFunction: () -> Unit
) {
	Row(
		Modifier
			.clickable(onClick = onFunction)
			.fillMaxWidth()
			.background(WeTheme.colors.listItem)
			.padding(20.dp)
	) {
		Image(
			painter = painterResource(id = icon),
			contentDescription = functionName, Modifier
				.background(Color(0X673AB7))
				.size(48.dp)
				.align(Alignment.CenterVertically)
				.clip(RoundedCornerShape(5.dp))
		)
		Column(
			Modifier.padding(start = 10.dp)
		) {
			Text(
				text = functionName,
				fontSize = 20.sp,
				color = WeTheme.colors.icon
			)
			Text(
				text = msg,
				fontSize = 17.sp,
				color = WeTheme.colors.textSecondary
			)
		}

		Spacer(modifier = Modifier.weight(1f))

		Icon(
			painter = painterResource(id = R.drawable.ic_back_right),
			contentDescription = functionName,
			modifier = Modifier
				.align(Alignment.CenterVertically)
				.width(10.dp)
				.height(18.dp),
			tint = WeTheme.colors.more
		)
	}
	Divider(
		startIndent = 68.dp,
		color = WeTheme.colors.chatListDivider,
		thickness = 0.8F.dp
	)
}

@Preview
@Composable
fun PAddFriendFunctionItem() {
	val mannerList = arrayListOf(
		AddFriendManner(icon = R.drawable.ic_radar,
						functionName = "雷达加朋友",msg = "添加身边的朋友"),
		AddFriendManner(icon = R.drawable.ic_contact_group,
						functionName = "面对面建群",msg = "与身边的朋友进入同一个群聊"),
		AddFriendManner(icon = R.drawable.ic_sweep,
						functionName = "扫一扫",msg = "扫描二维码名片"),
		AddFriendManner(icon = R.drawable.ic_contact_add,
						functionName = "手机联系人",msg = "添加或邀请通讯录钟的朋友"),
		AddFriendManner(icon = R.drawable.ic_contact_official,
						functionName = "公众号",msg = "获取更多资讯和服务"),
		AddFriendManner(icon = R.drawable.ic_enterprise,
						functionName = "企业微信联系人",msg = "通过手机号搜索企业微信用户"),
	)
	LazyColumn {
		itemsIndexed(mannerList){ _, manner ->
			AddFriendFunctionItem(
				icon = manner.icon,
				functionName = manner.functionName,
				msg = manner.msg
			) { }
		}
	}
}

@Preview
@Composable
fun PAddFriendAccountNum() {
	LazyColumn(Modifier.fillMaxWidth()) {
		item {
			AddFriendAccountNum()
		}
	}
}