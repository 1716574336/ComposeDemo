package com.cenming.composedemo.ui.second

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cenming.composedemo.ui.second.theme.WeTheme
import com.cenming.composedemo.R


@Composable
fun DiscoveryList() {
	Column(Modifier.fillMaxSize()) {
		DiscoveryListTopBar()
		Box(
			Modifier
				.background(WeTheme.colors.background)
				.fillMaxSize()
		) {
			Column(
				Modifier
					.background(WeTheme.colors.listItem)
					.fillMaxWidth()
			) {
				DiscoveryListItem(R.drawable.ic_moments, "朋友圈", badge = {
					Box(
						Modifier
							.padding(8.dp)
							.clip(RoundedCornerShape(50))
							.size(18.dp)
							.background(WeTheme.colors.badge)
					) {
						Text(
							"3",
							Modifier.align(Alignment.Center),
							fontSize = 12.sp,
							color = WeTheme.colors.onBadge
						)
					}
				}, endBadge = {
					Image(
						painterResource(R.drawable.avatar_diuwuxian),
						"avatar", Modifier
							.padding(8.dp, 0.dp)
							.size(32.dp)
							.unread(false, WeTheme.colors.badge)
							.clip(RoundedCornerShape(4.dp))
					)
				})
				WeSpacer()
				DiscoveryListItem(R.drawable.ic_channels, "视频号", endBadge = {
					Image(painterResource(R.drawable.avatar_3), "avatar", Modifier
						.padding(8.dp, 0.dp)
						.size(32.dp)
						.unread(false, WeTheme.colors.badge)
						.clip(RoundedCornerShape(4.dp))
					)
					Text(
						"赞过", Modifier.padding(0.dp, 0.dp, 4.dp, 0.dp),
						fontSize = 14.sp, color = WeTheme.colors.textSecondary
					)
				})
				WeSpacer()
				DiscoveryListItem(R.drawable.ic_ilook, "看一看")
				Divider(startIndent = 56.dp, color = WeTheme.colors.chatListDivider, thickness = 0.8f.dp)
				DiscoveryListItem(R.drawable.ic_isearch, "搜一搜")
				WeSpacer()
				DiscoveryListItem(R.drawable.ic_nearby, "直播和附近")
			}
		}
	}
}

@Composable
fun DiscoveryListItem(
	@DrawableRes icon: Int, title: String, badge: @Composable (() -> Unit)? = null,
	endBadge: @Composable (() -> Unit)? = null
) {
	Row(
		Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
	) {
		Image(
			painterResource(icon), "title", Modifier
				.padding(12.dp, 8.dp, 8.dp, 8.dp)
				.size(36.dp)
				.padding(8.dp)
		)
		Text(
			title, fontSize = 17.sp, color = WeTheme.colors.textPrimary
		)
		badge?.invoke()
		Spacer(Modifier.weight(1F))
		endBadge?.invoke()
		Icon(
			painterResource(R.drawable.ic_arrow_more), "更多", Modifier
				.padding(0.dp, 0.dp, 12.dp, 0.dp)
				.size(16.dp), tint = WeTheme.colors.more
		)
	}
}

@Composable
fun DiscoveryListTopBar() {
	WeTopBar(title = "发现")
}