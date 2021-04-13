package com.cenming.composedemo.ui.weChat

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.colorspace.ColorSpaces
import androidx.compose.ui.layout.layout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cenming.composedemo.R
import com.cenming.composedemo.ui.weChat.second.SecondViewModel
import com.cenming.composedemo.ui.weChat.theme.WeTheme
import dev.chrisbanes.accompanist.insets.navigationBarsPadding
import dev.chrisbanes.accompanist.insets.statusBarsPadding
import kotlin.math.roundToInt


@Composable
fun WeTopBar(title: String, onBack:(() -> Unit)? = null){
	Box(
		Modifier
			.background(WeTheme.colors.background)
			.fillMaxWidth()
			.statusBarsPadding()
	){
		Row(
			Modifier.height(48.dp)
		){
			if(onBack != null){
				Icon(
					painterResource(R.drawable.ic_back),
					"退出", Modifier
						.clickable(onClick = onBack)
						.align(Alignment.CenterVertically)
						.size(36.dp)
						.padding(8.dp),
					tint = WeTheme.colors.icon
				)
			}
			Spacer(Modifier.weight(1F))
			val viewModel = viewModel<SecondViewModel>()
			Icon(
				painterResource(R.drawable.ic_palette),
				contentDescription = "切换主题", Modifier
					.clickable {
						viewModel.mTheme = when (viewModel.mTheme) {
							WeTheme.Theme.Light -> WeTheme.Theme.Dark
							WeTheme.Theme.Dark -> WeTheme.Theme.NewYear
							WeTheme.Theme.NewYear -> WeTheme.Theme.Light
						}
					}
					.align(Alignment.CenterVertically)
					.size(36.dp)
					.padding(8.dp),
				tint = WeTheme.colors.icon
			)
		}
		Text(title, Modifier.align(Alignment.Center), color = WeTheme.colors.textPrimary)
	}
}
@Composable
fun WeFriendTopBar(msg: String, onBack: () -> Unit, onAddFriend: (() -> Unit)? = null) {
	Box(
		Modifier
			.background(WeTheme.colors.background)
			.fillMaxWidth()
			.statusBarsPadding()
	) {
		Row(
			Modifier
				.height(48.dp)
		) {
			Icon(
				painter = painterResource(id = R.drawable.ic_back),
				contentDescription = msg, Modifier
					.clickable(onClick = onBack)
					.align(Alignment.CenterVertically)
					.size(36.dp)
					.padding(8.dp), tint = WeTheme.colors.icon
			)
			Text(
				text = msg, Modifier
					.align(Alignment.CenterVertically),
				color = WeTheme.colors.textPrimary, fontSize = 20.sp
			)

			Spacer(Modifier.weight(1F))
			var press by remember {
				mutableStateOf(false)
			}
			onAddFriend?.let { onClick ->
				Text(
					text = "添加朋友",
					Modifier
						.clickable(onClick = onClick)
						.width(100.dp)
						.height(48.dp)
						.wrapContentHeight()
						/*.pointerInteropFilter { event ->
							press = (event.action == MotionEvent.ACTION_DOWN)
							Log.d("text", "event = ${event};\npress = $press")
							false
						}*/,
					color = if(press){
						WeTheme.colors.listItem
					}else{
						WeTheme.colors.textPrimary
					},
					fontSize = 20.sp,
					textAlign = TextAlign.Center,
				)
			}
		}
	}
}

@Composable
fun WeFriendSearch(onSearchNumber : () -> Unit) {
//	val ctx = LocalContext.current
	val color = Color(0xFFCDCDCD).convert(ColorSpaces.CieXyz)
	Box(
		Modifier
			.padding(15.dp, 10.dp)
			.fillMaxWidth()
	) {
		Spacer(
			Modifier
				.fillMaxWidth()
				.height(35.dp)
				.clip(RoundedCornerShape(5.dp))
				.background(WeTheme.colors.listItem)
				.clickable(onClick = onSearchNumber)
				/*{
					ctx.startActivity(
						Intent(ctx, SearchNumberActivity::class.java).also {
							it.flags = Intent.FLAG_ACTIVITY_NEW_TASK
						})
				}*/
		)
		Row(
			Modifier.align(Alignment.Center)
		) {
			Icon(
				painter = painterResource(id = R.drawable.ic_search),
				modifier = Modifier.align(Alignment.CenterVertically),
				contentDescription = "微信号/手机号", tint = color
			)
			Text(
				text = "微信号/手机号", modifier = Modifier.align(Alignment.CenterVertically),
				color = color, fontSize = 20.sp
			)
		}
	}
}


/**
 * 增加未读小红点
 */
fun Modifier.unread(isShow: Boolean, badgeColor: Color) = this
	.drawWithContent {
		drawContent()
		if (isShow) {
			drawCircle(
				color = badgeColor,
				radius = 5.dp.toPx(),
				center = Offset(size.width - 1.dp.toPx(), 1.dp.toPx()),
			)
		}
	}

fun Modifier.percentOffsetX(percent: Float) = this.layout { measurable, constraints ->
	val placeable = measurable.measure(constraints)
	layout(placeable.width, placeable.height) {
		placeable.placeRelative(IntOffset((placeable.width * percent).roundToInt(), 0))
	}
}

@Composable
fun WeBottomBar(modifier: Modifier = Modifier, content: @Composable RowScope.() -> Unit) {
	Row(
		modifier
			.fillMaxWidth()
			.background(WeTheme.colors.bottomBar)
			.padding(4.dp, 0.dp)
			.navigationBarsPadding(),
		content = content
	)
}

@Composable
fun WeSpacer(modifier: Modifier = Modifier){
	Spacer(
		modifier
			.background(WeTheme.colors.background)
			.fillMaxWidth()
			.height(8.dp)
	)
}

@Composable
fun BasicTextFieldWithHint(
	value: String,
	hint:@Composable BoxScope.() -> Unit,
	onValueChange: (String) -> Unit,
	modifier: Modifier = Modifier,
	containerModifier: Modifier = Modifier,
	enabled: Boolean = true,
	readOnly: Boolean = false,
	textStyle: TextStyle = TextStyle.Default,
	keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
	keyboardActions: KeyboardActions = KeyboardActions.Default,
	singleLine: Boolean = true,
	maxLines: Int = Int.MAX_VALUE,
	visualTransformation: VisualTransformation = VisualTransformation.None,
	onTextLayout: (TextLayoutResult) -> Unit = {},
	interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
	cursorBrush: Brush = SolidColor(Color.Black),
	decorationBox: @Composable (innerTextField: @Composable () -> Unit) -> Unit =
		@Composable { innerTextField -> innerTextField() }
){
	Box(containerModifier){
		BasicTextField(
			value = value,
			onValueChange = onValueChange,
			modifier = modifier,
			enabled = enabled,
			readOnly = readOnly,
			textStyle = textStyle,
			keyboardOptions = keyboardOptions,
			keyboardActions = keyboardActions,
			singleLine = singleLine,
			maxLines = maxLines,
			visualTransformation = visualTransformation,
			onTextLayout = onTextLayout,
			interactionSource = interactionSource,
			cursorBrush = cursorBrush,
			decorationBox = decorationBox
		)
		if(value.isEmpty()) hint()
	}
}