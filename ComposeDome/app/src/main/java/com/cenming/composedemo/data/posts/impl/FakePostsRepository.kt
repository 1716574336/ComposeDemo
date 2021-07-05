package com.cenming.composedemo.data.posts.impl

import com.cenming.composedemo.data.Result
import com.cenming.composedemo.data.posts.PostsRepository
import com.cenming.composedemo.model.Post
import com.cenming.composedemo.utils.addOrRemove
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext


/**
 * Created by Cenming on 2021/3/4.
 * 功能:PostsRepository 的实现，它在后台线程中延迟了一段时间后返回带有资源的帖子的硬编码列表
 */
@OptIn(ExperimentalCoroutinesApi::class)
class FakePostsRepository : PostsRepository {
	/**
	 * 现在，将它们存储在内存中
	 */
	private val mFavorites = MutableStateFlow<Set<String>>(setOf())

	/**
	 * 用于使可读取和更新状态的挂起函数安全地从任何线程调用
	 * Mutex : 永远不会同时执行的 关键代码块 来保护共享状态（协程之间不会同时执行）
	 */
	private val mMutex = Mutex()

	/**
	 * 用于以可预测的模式驱动“随机”故障，从而始终使第一个请求成为可能
	 */
	private var requestCount = 0

	/**
	 * 随机破坏一些负载以模拟真实网络
	 * 每5个请求将确定性地失败一次
	 */
	private fun shouldRandomlyFail(): Boolean = ++requestCount % 5 == 0

	override suspend fun getPost(postId: String): Result<Post> =
			withContext(Dispatchers.IO) {        // 通过 ID 查询对应的 post 数据
				val post = posts.find { it.id == postId }
				if (post == null) {
					Result.Error(IllegalArgumentException("Post not found"))
				} else {
					Result.Success(post)
				}
			}

	override suspend fun getPosts(): Result<List<Post>> =
			withContext(Dispatchers.IO) {		// 假装我们的网络速度很慢
				println("getPost()")
				delay(800)
				if (shouldRandomlyFail()) {
					Result.Error(IllegalStateException())
				} else {
					Result.Success(posts)
				}
			}

	override fun observeFavorites(): Flow<Set<String>> = this.mFavorites

	override suspend fun toggleFavorite(postId: String) {
		this.mMutex.withLock {
			this.mFavorites.value = this.mFavorites.value.toMutableSet()
					.addOrRemove(postId).toSet()
		}
	}
}