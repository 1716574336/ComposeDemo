package com.cenming.composedemo.data

import com.cenming.composedemo.data.interests.InterestsRepository
import com.cenming.composedemo.data.interests.impl.FakeInterestsRepository
import com.cenming.composedemo.data.posts.PostsRepository
import com.cenming.composedemo.data.posts.impl.FakePostsRepository


/**
 * Created by Cenming on 2021/3/4.
 * 功能:
 */

/**
 * 在 application 级别中的依赖注入容器
 */
interface AppContainer{
	val postsRepository: PostsRepository
	val interestsRepository: InterestsRepository
}

/**
 * 在 application 级别实现依赖项注入容器。
 * 变量被延迟初始化，并且在整个应用程序中共享同一实例
 */
class AppContainerImpl : AppContainer{
	override val postsRepository: PostsRepository by lazy{
		FakePostsRepository()
	}
	override val interestsRepository: InterestsRepository by lazy {
		FakeInterestsRepository()
	}

}