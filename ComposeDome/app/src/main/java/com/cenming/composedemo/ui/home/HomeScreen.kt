package com.cenming.composedemo.ui.home

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import com.cenming.composedemo.data.posts.PostsRepository
import com.cenming.composedemo.ui.Screen
import com.cenming.composedemo.utils.produceUiState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.android.style.LineHeightSpan
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cenming.composedemo.R
import com.cenming.composedemo.model.Post
import com.cenming.composedemo.data.Result
import com.cenming.composedemo.data.posts.impl.BlockingFakePostsRepository
import com.cenming.composedemo.ui.AppDrawer
import com.cenming.composedemo.ui.SwipeToRefreshLayout
import com.cenming.composedemo.ui.ThemedPreview
import com.cenming.composedemo.ui.state.UiState
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * 有状态的HomeScreen，它使用[produceUiState]管理状态
 * @param startActivity 跳转页面
 * @param navigateTo （事件）请求导航到[Screen]
 * @param postsRepository 此屏幕的数据源
 * @param scaffoldState 此屏幕上[Scaffold]组件的（状态）状态
 */
@Composable
fun HomeScreen(
	startActivity : (() -> Unit)? = null,
	navigateTo : (Screen) -> Unit,
	postsRepository: PostsRepository,
	scaffoldState: ScaffoldState = rememberScaffoldState()
){

	// 获取数据,刷新数据的操作回调，错误回调
	val (postUiState, refreshPost, clearError) = produceUiState(postsRepository){
		getPosts()
	}

	// [collectAsState]将自动收集Flow <T>并返回一个State <T>对象，该对象在Flow发出值时更新。
	// 从组成树中删除[collectAsState]时，将取消收集
	val favorites by postsRepository.observeFavorites().collectAsState(setOf())

	// 返回范围为[HomeScreen]生命周期的[CoroutineScope]。从合成中删除此屏幕时，合并范围将被取消
	val coroutineScope = rememberCoroutineScope()

	HomeScreen(
		startActivity = startActivity,
		posts = postUiState.value,
		favorites = favorites,
		onToggleFavorite = {
			coroutineScope.launch { postsRepository.toggleFavorite(it) }
		},
		onRefreshPosts = refreshPost,
		onErrorDismiss = clearError,
		navigateTo = navigateTo,
		scaffoldState = scaffoldState
	)
}

/**
 * 负责显示此应用程序的主屏幕.
 *
 * 无状态可组合不与任何特定状态管理耦合.
 *
 * @param posts (state) 屏幕上显示的数据
 * @param favorites (state) 最喜欢的帖子
 * @param onToggleFavorite (event) 切换帖子的收藏夹
 * @param onRefreshPosts (event) 请求更新帖子
 * @param onErrorDismiss (event) 要求消除当前错误
 * @param navigateTo (event) 请求导航到 [Screen]
 */
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(
	startActivity : (() -> Unit)? = null,
	posts: UiState<List<Post>>,
	favorites: Set<String>,
	onToggleFavorite: (String) -> Unit,
	onRefreshPosts: () -> Unit,
	onErrorDismiss: () -> Unit,
	navigateTo: (Screen) -> Unit,
	scaffoldState: ScaffoldState
) {
	if (posts.hasError) {
		val errorMessage = stringResource(id = R.string.load_error)
		val retryMessage = stringResource(id = R.string.retry)

		// 如果在LaunchedEffect运行时onRefreshPosts或onErrorDismiss发生更改，
		// 请不要重新启动效果并使用最新的Lambda值
		val onRefreshPostsState by rememberUpdatedState(onRefreshPosts)
		val onErrorDismissState by rememberUpdatedState(onErrorDismiss)

		// 使用协程显示底部提示栏，当协程取消时，底部提示栏将自动关闭。每当post.hasError为false（由于周围的if语句）
		// 或 scaffoldState 更改时，此协程将取消
		LaunchedEffect(scaffoldState) {
			val snackbarResult = scaffoldState.snackbarHostState.showSnackbar(
				message = errorMessage,
				actionLabel = retryMessage
			)
			when (snackbarResult) {
				SnackbarResult.ActionPerformed -> onRefreshPostsState()
				SnackbarResult.Dismissed -> onErrorDismissState()
			}
		}
	}

	val coroutineScope = rememberCoroutineScope()
	Scaffold(
		scaffoldState = scaffoldState,
		drawerContent = {
			AppDrawer(
				currentScreen = Screen.Home,
				closeDrawer = { coroutineScope.launch { scaffoldState.drawerState.close() } },
				navigateTo = navigateTo
			)
		},
		topBar = {
			val title = stringResource(id = R.string.app_name)
			TopAppBar(
				title = { Text(text = title) },
				navigationIcon = {
					IconButton(onClick = { coroutineScope.launch { scaffoldState.drawerState.open() } }) {
						Icon(
							painter = painterResource(R.drawable.ic_jetnews_logo),
							contentDescription = stringResource(R.string.cd_open_navigation_drawer)
						)
					}
				}
			)
		},
		content = { innerPadding ->
			val modifier = Modifier.padding(innerPadding)
			LoadingContent(
				empty = posts.initialLoad,
				emptyContent = { FullScreenLoading() },
				loading = posts.loading,
				onRefresh = onRefreshPosts,
				content = {
					HomeScreenErrorAndContent(
						posts = posts,
						onRefresh = {
							onRefreshPosts()
						},
						navigateTo = navigateTo,
						favorites = favorites,
						onToggleFavorite = onToggleFavorite,
						modifier = modifier
					)
				}
			)
		},
		bottomBar = {
			Surface {
				Row(
					verticalAlignment = Alignment.CenterVertically,
					modifier = Modifier
							.height(56.dp)
							.fillMaxWidth()
				) {
					Spacer(modifier = Modifier.weight(1f))
					IconButton(
						onClick = startActivity?:{}
					){
						Icon(
							painter = painterResource(R.drawable.icon_jump),
							contentDescription = null,
							modifier = Modifier.width(30.dp).height(30.dp)
						)
					}
				}
			}
		}
	)
}

/**
 * 显示初始为空状态或滑动以刷新内容.
 *
 * @param empty (state) 如果为true，则显示[emptyContent]
 * @param emptyContent (slot) 空状态下显示的内容
 * @param loading (state) 如果为true，则在[content]上显示加载微调框
 * @param onRefresh (event) 请求刷新的事件
 * @param content (slot) 显示的主要内容
 */
@Composable
fun LoadingContent(
	empty: Boolean,
	emptyContent: @Composable () -> Unit,
	loading: Boolean,
	onRefresh: () -> Unit,
	content: @Composable () -> Unit
) {
	if (empty) {
		emptyContent()
	} else {
		SwipeToRefreshLayout(
			refreshingState = loading,
			onRefresh = onRefresh,
			refreshIndicator = {
				Surface(elevation = 10.dp, shape = CircleShape) {
					CircularProgressIndicator(
						modifier = Modifier
								.size(36.dp)
								.padding(4.dp)
					)
				}
			},
			content = content,
		)
	}
}

/**
 * 负责显示[PostList]周围的任何错误情况.
 *
 * @param posts (state) 帖子列表和要显示的错误状态
 * @param onRefresh (event) 请求刷新数据
 * @param navigateTo (event) 请求导航到 [Screen]
 * @param favorites (state) 所有收藏夹
 * @param onToggleFavorite (event) 请求切换单个收藏夹
 * @param modifier 根元素修饰符
 */
@Composable
fun HomeScreenErrorAndContent(
	posts: UiState<List<Post>>,
	onRefresh: () -> Unit,
	navigateTo: (Screen) -> Unit,
	favorites: Set<String>,
	onToggleFavorite: (String) -> Unit,
	modifier: Modifier = Modifier
) {
	if (posts.data != null) {
		PostList(posts.data, navigateTo, favorites, onToggleFavorite, modifier)
	} else if (!posts.hasError) {
		// 如果没有帖子，也没有错误，请让用户手动刷新
		TextButton(onClick = onRefresh, modifier.fillMaxSize()) {
			Text("Tap to load content", textAlign = TextAlign.Center)
		}
	} else {
		// 当前显示错误，不显示任何内容
		Box(modifier.fillMaxSize()) { /* 空屏 */ }
	}
}

/**
 * 显示帖子列表.
 *
 * 单击帖子后，将调用[navigateTo]导航到该帖子的详细信息屏幕.
 *
 * @param posts (state) 要显示的列表
 * @param navigateTo (event) 请求导航到 [Screen]
 * @param modifier 根元素的修饰符
 */
@Composable
fun PostList(
	posts: List<Post>,
	navigateTo: (Screen) -> Unit,
	favorites: Set<String>,
	onToggleFavorite: (String) -> Unit,
	modifier: Modifier = Modifier
) {
	val postTop = posts[3]
	val postsSimple = posts.subList(0, 2)
	val postsPopular = posts.subList(2, 7)
	val postsHistory = posts.subList(7, 10)

	LazyColumn(modifier = modifier) {
		item { PostListTopSection(postTop, navigateTo) }
		item { PostListSimpleSection(postsSimple, navigateTo, favorites, onToggleFavorite) }
		item { PostListPopularSection(postsPopular, navigateTo) }
		item { PostListHistorySection(postsHistory, navigateTo) }
	}
}

/**
 * 全屏循环进度指示器
 */
@Composable
fun FullScreenLoading() {
	Box(
		modifier = Modifier
				.fillMaxSize()
				.wrapContentSize(Alignment.Center)
	) {
		CircularProgressIndicator()
	}
}

/**
 * [PostList]的顶部
 *
 * @param post (state) 要显示的突出显示的帖子
 * @param navigateTo (event) 请求导航到 [Screen]
 */
@Composable
fun PostListTopSection(post: Post, navigateTo: (Screen) -> Unit) {
	Text(
		modifier = Modifier.padding(start = 16.dp, top = 16.dp, end = 16.dp),
		text = "Top stories for you",
		style = MaterialTheme.typography.subtitle1
	)
	PostCardTop(
		post = post,
		modifier = Modifier.clickable(onClick = { navigateTo(Screen.Article(post.id)) })
	)
	PostListDivider()
}

/**
 * Full-width list items for [PostList]
 *
 * @param posts (state) to display
 * @param navigateTo (event) request navigation to [Screen]
 */
@Composable
fun PostListSimpleSection(
	posts: List<Post>,
	navigateTo: (Screen) -> Unit,
	favorites: Set<String>,
	onToggleFavorite: (String) -> Unit
) {
	Column {
		posts.forEach { post ->
			PostCardSimple(
				post = post,
				navigateTo = navigateTo,
				isFavorite = favorites.contains(post.id),
				onToggleFavorite = { onToggleFavorite(post.id) }
			)
			PostListDivider()
		}
	}
}

/**
 * [PostList]的水平滚动卡
 *
 * @param posts (state) 显示
 * @param navigateTo (event) 请求导航到 [Screen]
 */
@Composable
fun PostListPopularSection(
	posts: List<Post>,
	navigateTo: (Screen) -> Unit
) {
	Column {
		Text(
			modifier = Modifier.padding(16.dp),
			text = "Popular on Jetnews",
			style = MaterialTheme.typography.subtitle1
		)

		LazyRow(modifier = Modifier.padding(end = 16.dp)) {
			items(posts) { post ->
				PostCardPopular(post, navigateTo, Modifier.padding(start = 16.dp, bottom = 16.dp))
			}
		}
		PostListDivider()
	}
}

/**
 * 在[PostList]中显示“基于您的历史记录”的全角列表项
 *
 * @param posts (state) 显示
 * @param navigateTo (event) 请求导航到 [Screen]
 */
@Composable
fun PostListHistorySection(
	posts: List<Post>,
	navigateTo: (Screen) -> Unit
) {
	Column {
		posts.forEach { post ->
			PostCardHistory(post, navigateTo)
			PostListDivider()
		}
	}
}

/**
 * 带[PostList]填充的全角分隔符
 */
@Composable
fun PostListDivider() {
	Divider(
		modifier = Modifier.padding(horizontal = 14.dp),
		color = MaterialTheme.colors.onSurface.copy(alpha = 0.08f)
	)
}

@Preview("Home screen body")
@Composable
fun PreviewHomeScreenBody() {
	ThemedPreview {
		val posts = loadFakePosts()
		PostList(posts, { }, setOf(), {})
	}
}

@Preview("Home screen, open drawer")
@Composable
fun PreviewDrawerOpen() {
	ThemedPreview {
		val scaffoldState = rememberScaffoldState(
			drawerState = rememberDrawerState(DrawerValue.Open)
		)
		HomeScreen(
			postsRepository = BlockingFakePostsRepository(LocalContext.current),
			scaffoldState = scaffoldState,
			navigateTo = { }
		)
	}
}

@Preview("Home screen dark theme")
@Composable
fun PreviewHomeScreenBodyDark() {
	ThemedPreview(darkTheme = true) {
		val posts = loadFakePosts()
		PostList(posts, {}, setOf(), {})
	}
}

@Composable
fun loadFakePosts(): List<Post> {
	val context = LocalContext.current
	val posts = runBlocking {
		BlockingFakePostsRepository(context).getPosts()
	}
	return (posts as Result.Success).data
}

@Preview("Home screen, open drawer dark theme")
@Composable
fun PreviewDrawerOpenDark() {
	ThemedPreview(darkTheme = true) {
		val scaffoldState = rememberScaffoldState(
			drawerState = rememberDrawerState(DrawerValue.Open)
		)
		HomeScreen(
			postsRepository = BlockingFakePostsRepository(LocalContext.current),
			scaffoldState = scaffoldState,
			navigateTo = { }
		)
	}
}


