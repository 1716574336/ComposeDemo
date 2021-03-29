package com.cenming.composedemo.data.posts

import com.cenming.composedemo.model.Post
import com.cenming.composedemo.data.Result
import kotlinx.coroutines.flow.Flow


/**
 * Created by Cenming on 2021/3/3.
 * 功能:与Posts数据层的接口
 */
interface PostsRepository {

	/**
	 * 获取特定的JetNews帖子
	 */
	suspend fun getPost(postId: String): Result<Post>

	/**
	 * 获取JetNews帖子
	 */
	suspend fun getPosts(): Result<List<Post>>

	/**
	 * 观察当前收藏夹
	 */
	fun observeFavorites(): Flow<Set<String>>

	/**
	 * 切换postId为收藏夹或不收藏夹
	 */
	suspend fun toggleFavorite(postId: String)
}