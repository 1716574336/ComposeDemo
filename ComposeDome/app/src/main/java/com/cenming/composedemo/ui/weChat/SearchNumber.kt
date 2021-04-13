package com.cenming.composedemo.ui.weChat

import android.text.TextUtils
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.colorspace.ColorSpaces
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cenming.composedemo.R
import com.cenming.composedemo.ui.weChat.data.User
import com.cenming.composedemo.ui.weChat.second.SecondViewModel
import com.cenming.composedemo.ui.weChat.theme.WeTheme
import dev.chrisbanes.accompanist.insets.statusBarsPadding


@Composable
fun SearchNumber(
	modifier:Modifier,
	onBack: () -> Unit,
	viewModel: SecondViewModel
){
	var editingText by remember { mutableStateOf("") }
	val color = Color(0xFFCDCDCD).convert(ColorSpaces.CieXyz)

	Scaffold(
		modifier = modifier
			.background(WeTheme.colors.background),
		topBar = {
			SearchNumberTopBar(
				editingText,
				color,
				onValueChange = {
					editingText = it
				},
				onBack = onBack)
		},
		content = {
			LazyColumn(
				Modifier
					.background(WeTheme.colors.background)
					.fillMaxSize()
			) {
				if(!TextUtils.isEmpty(editingText)){
					item { SearchNumberSearch(editingText) }
					searchNumberPhoneList(editingText, viewModel.getPhoneList(editingText))
				}
			}
		}
	)
}

@Composable
fun SearchNumberTopBar(
	editingText : String,
	color: Color,
	onValueChange: (String) -> Unit,
	onBack: () -> Unit
){
	Row(
		Modifier
			.fillMaxWidth()
			.background(WeTheme.colors.background)
			.statusBarsPadding()
			.padding(horizontal = 10.dp, vertical = 5.dp)
	) {
		Row(
			Modifier
				.weight(1f)
				.clip(RoundedCornerShape(5.dp))
				.align(Alignment.CenterVertically)
				.background(WeTheme.colors.textFieldBackground)
		) {
			Icon(
				painter = painterResource(id = R.drawable.ic_search),
				modifier = Modifier
					.align(Alignment.CenterVertically)
					.padding(horizontal = 10.dp),
				contentDescription = "微信号/手机号", tint = color
			)
			BasicTextFieldWithHint(
				value = editingText,
				hint = {
					Text(
						text = "微信号/手机号",
						color = color,
						modifier = Modifier
							.align(Alignment.CenterStart)
							.padding(4.dp, 8.dp),
						fontSize = 17.sp
					)
				},
				textStyle = TextStyle(fontSize = 17.sp),
				onValueChange = onValueChange,
				modifier = Modifier
					.fillMaxWidth()
					.padding(4.dp, 8.dp)
			)
		}
		Text(
			text = "取消",
			color = Color(0xFF03A9F4),
			fontSize = 17.sp,
			modifier = Modifier
				.padding(start = 10.dp)
				.clickable(onClick = onBack)
				.align(Alignment.CenterVertically)
				.padding(5.dp)
		)
	}
}

@Composable
fun SearchNumberSearch(
	editingText : String
){
	val text = "搜索:"
	Row(
		Modifier
			.fillMaxWidth()
			.background(WeTheme.colors.textFieldBackground)
			.padding(horizontal = 10.dp, vertical = 5.dp)
	) {
		Image(
			painter = painterResource(id = R.drawable.ic_contact_add),
			contentDescription = text + editingText,
			modifier = Modifier.size(48.dp)
		)
		val annString = AnnotatedString.Builder().apply {
			append("搜索:")
			pushStyle(style = SpanStyle(color = Color(0xFF117315)))
			append(editingText)
			// 去掉样式
			pop()
		}
		Text(
			text = annString.toAnnotatedString(),
			fontSize = 17.sp,
			modifier = Modifier
				.align(Alignment.CenterVertically)
				.padding(start = 15.dp)
		)
	}
}

fun LazyListScope.searchNumberPhoneList(
	editingText : String,
	phoneList : List<User>
){
	item {
		Text(
			text = "手机通讯录",
			fontSize = 15.sp,
			modifier = Modifier
				.fillMaxWidth()
				.background(WeTheme.colors.textFieldBackground)
				.padding(horizontal = 10.dp, vertical = 5.dp)
		)
		Divider(
			startIndent = 10.dp,
			color = WeTheme.colors.chatListDivider,
			thickness = 0.8f.dp
		)
	}
	itemsIndexed(phoneList){ index, item ->
		SearchNumberPhoneItem(editingText, item)
		if (index < phoneList.size - 1) {
			Divider(
				startIndent = 56.dp,
				color = WeTheme.colors.chatListDivider,
				thickness = 0.8f.dp
			)
		}
	}
}

@Composable
fun SearchNumberPhoneItem(editingText : String, user: User){
	val phoneStr = user.phone.replaceFirst(editingText, "")
	val annString = AnnotatedString.Builder().apply {
		append("手机号:")
		pushStyle(style = SpanStyle(color = Color(0xFF117315)))
		append(editingText)
		// 去掉样式
		pop()
		append(phoneStr)
	}
	Row(
		Modifier
			.fillMaxWidth()
			.background(WeTheme.colors.listItem)
	) {
		Image(
			painterResource(user.avatar),
			contentDescription = "头像", Modifier
				.padding(12.dp, 8.dp, 8.dp, 8.dp)
				.size(48.dp)
				.clip(RoundedCornerShape(4.dp))
		)
		Column(
			Modifier
				.weight(1f)
				.align(Alignment.CenterVertically)
		) {
			Text(
				user.name,
				fontSize = 17.sp,
				color = WeTheme.colors.textPrimary
			)
			Text(
				annString.toAnnotatedString(),
				fontSize = 14.sp,
				color = WeTheme.colors.textSecondary
			)
		}
	}
}

@Preview(showBackground = true)
@Composable
fun PreviewSearchNumber(){
	val color = Color(0xFFCDCDCD).convert(ColorSpaces.CieXyz)
	var editingText by remember {
		mutableStateOf("")
	}
	SearchNumberTopBar(editingText, color, onValueChange = {
		editingText = it
	}, onBack = {})
}