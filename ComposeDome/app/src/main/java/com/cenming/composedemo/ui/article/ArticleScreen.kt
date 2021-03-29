/*
 * Copyright 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.cenming.composedemo.ui.article

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cenming.composedemo.data.posts.PostsRepository
import com.cenming.composedemo.data.posts.impl.BlockingFakePostsRepository
import com.cenming.composedemo.data.posts.impl.post3
import com.cenming.composedemo.data.Result
import com.cenming.composedemo.model.Post
import com.cenming.composedemo.ui.ThemedPreview
import com.cenming.composedemo.ui.home.BookmarkButton
import com.cenming.composedemo.utils.produceUiState
import com.cenming.composedemo.R
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * 使用[produceUiState]管理状态的有状态文章屏幕
 *
 * @param postId (state) 要显示的帖子
 * @param postsRepository 此屏幕的数据源
 * @param onBack (event) 请求返回导航
 */
@Suppress("DEPRECATION") // 允许ViewModelLifecycleScope调用
@Composable
fun ArticleScreen(
    postId: String,
    postsRepository: PostsRepository,
    onBack: () -> Unit
) {
    val (post) = produceUiState(postsRepository, postId) {
        getPost(postId)
    }
    // TODO: 当存储库能够创建错误时处理错误
    val postData = post.value.data ?: return

    // [collectAsState]将自动收集Flow <T>并返回一个State <T>对象，
    // 该对象在Flow发出值时更新。从组成树中删除[collectAsState]时，将取消收集
    val favorites by postsRepository.observeFavorites().collectAsState(setOf())
    val isFavorite = favorites.contains(postId)

    // 返回范围为[ArticleScreen]生命周期的[CoroutineScope]。从合成中删除此屏幕时，合并范围将被取消
    val coroutineScope = rememberCoroutineScope()

    ArticleScreen(
        post = postData,
        onBack = onBack,
        isFavorite = isFavorite,
        onToggleFavorite = {
            coroutineScope.launch { postsRepository.toggleFavorite(postId) }
        }
    )
}

/**
 * 显示单个帖子的无状态文章屏幕.
 *
 * @param post 要显示的（状态）项
 * @param onBack (event) 请求返回
 * @param isFavorite (state) 当前是该商品的最爱
 * @param onToggleFavorite (event) 请求此帖子切换到收藏夹状态
 */
@Composable
fun ArticleScreen(
    post: Post,
    onBack: () -> Unit,
    isFavorite: Boolean,
    onToggleFavorite: () -> Unit
) {

    var showDialog by rememberSaveable { mutableStateOf(false) }
    if (showDialog) {
        FunctionalityNotAvailablePopup { showDialog = false }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Published in: ${post.publication?.name}",
                        style = MaterialTheme.typography.subtitle2,
                        color = LocalContentColor.current
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.cd_navigate_up)
                        )
                    }
                }
            )
        },
        content = { innerPadding ->
            val modifier = Modifier.padding(innerPadding)
            PostContent(post, modifier)
        },
        bottomBar = {
            BottomBar(
                post = post,
                onUnimplementedAction = { showDialog = true },
                isFavorite = isFavorite,
                onToggleFavorite = onToggleFavorite
            )
        }
    )
}

/**
 * 文章屏幕的底部栏
 *
 * @param post (state) 在共享表中用于分享帖子
 * @param onUnimplementedAction (event) 在用户执行未执行的操作时调用
 * @param isFavorite (state) 如果这则帖子目前是我的最爱
 * @param onToggleFavorite (event) 请求此帖子切换其收藏夹状态
 */
@Composable
fun BottomBar(
    post: Post,
    onUnimplementedAction: () -> Unit,
    isFavorite: Boolean,
    onToggleFavorite: () -> Unit
) {
    Surface(elevation = 2.dp) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .height(56.dp)
                .fillMaxWidth()
        ) {
            IconButton(onClick = onUnimplementedAction) {
                Icon(
                    imageVector = Icons.Filled.FavoriteBorder,
                    contentDescription = stringResource(R.string.cd_add_to_favorites)
                )
            }
            BookmarkButton(
                isBookmarked = isFavorite,
                onClick = onToggleFavorite
            )
            val context = LocalContext.current
            IconButton(onClick = { sharePost(post, context) }) {
                Icon(
                    imageVector = Icons.Filled.Share,
                    contentDescription = stringResource(R.string.cd_share)
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            IconButton(onClick = onUnimplementedAction) {
                Icon(
                    painter = painterResource(R.drawable.ic_text_settings),
                    contentDescription = stringResource(R.string.cd_text_settings)
                )
            }
        }
    }
}

/**
 * 显示一个弹出窗口，说明功能不可用.
 *
 * @param onDismiss (event) 请求关闭弹出窗口
 */
@Composable
fun FunctionalityNotAvailablePopup(onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        text = {
            Text(
                text = "Functionality not available \uD83D\uDE48",
                style = MaterialTheme.typography.body2
            )
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text(text = "CLOSE")
            }
        }
    )
}

/**
 * 显示帖子的分享表
 *
 * @param post 分享
 * @param context Android上下文以显示共享表
 */
private fun sharePost(post: Post, context: Context) {
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TITLE, post.title)
        putExtra(Intent.EXTRA_TEXT, post.url)
    }
    context.startActivity(Intent.createChooser(intent, "Share post"))
}

@Preview("Article screen")
@Composable
fun PreviewArticle() {
    ThemedPreview {
        val post = loadFakePost(post3.id)
        ArticleScreen(post, {}, false, {})
    }
}

@Preview("Article screen dark theme")
@Composable
fun PreviewArticleDark() {
    ThemedPreview(darkTheme = true) {
        val post = loadFakePost(post3.id)
        ArticleScreen(post, {}, false, {})
    }
}

@Composable
fun loadFakePost(postId: String): Post {
    val context = LocalContext.current
    return runBlocking {
        (BlockingFakePostsRepository(context).getPost(postId) as Result.Success).data
    }
}
