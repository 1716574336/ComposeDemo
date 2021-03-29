package com.cenming.composedemo.ui

import android.util.Log
import androidx.compose.animation.Crossfade
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import com.cenming.composedemo.data.AppContainer
import com.cenming.composedemo.data.interests.InterestsRepository
import com.cenming.composedemo.data.posts.PostsRepository
import com.cenming.composedemo.ui.article.ArticleScreen
import com.cenming.composedemo.ui.home.HomeScreen
import com.cenming.composedemo.ui.interests.InterestsScreen
import com.cenming.composedemo.ui.theme.JetNewsTheme


/**
 * Created by Cenming on 2021/3/4.
 * 功能: MainActivity 的 Composable 设置
 */
@Composable
fun JetNewApp(
	appContainer: AppContainer,
	viewModel: NavigationViewModel
){
	JetNewsTheme {
		AppContent(
			viewModel = viewModel,
			interestsRepository = appContainer.interestsRepository,
			postsRepository = appContainer.postsRepository
		)
	}
}

@Composable
fun AppContent(
	viewModel: NavigationViewModel,
	postsRepository: PostsRepository,
	interestsRepository: InterestsRepository
){
	Crossfade(targetState = viewModel.mCurrentScreen){ screen ->
		Surface(color = MaterialTheme.colors.background) {
			when(screen){
				is Screen.Home -> HomeScreen(
					startActivity = viewModel::startActivity,
					navigateTo = viewModel::navigateTo,
					postsRepository = postsRepository
				)
				is Screen.Interests -> InterestsScreen(
					navigateTo = viewModel::navigateTo,
					interestsRepository = interestsRepository
				)
				is Screen.Article -> ArticleScreen(
					postId = screen.postId,
					postsRepository = postsRepository,
					onBack = { viewModel.onBack() }
				)
			}
		}
	}
}

