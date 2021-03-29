package com.cenming.composedemo.data.interests

import kotlinx.coroutines.flow.Flow
import com.cenming.composedemo.data.Result


/**
 * Created by Cenming on 2021/3/4.
 * 功能:
 */

/**
 * typealias 可用于提供一个更语义精简的类型别名取代具体泛型类型、匿名函数等含糊定义
 */
typealias TopicsMap = Map<String, List<String>>

/**
 * 与兴趣数据层的接口
 */
interface InterestsRepository {

	/**
	 * 向用户获取相关主题
	 */
	suspend fun getTopics(): Result<TopicsMap>

	/**
	 * 获取人员列表
	 */
	suspend fun getPeople(): Result<List<String>>

	/**
	 * 获取出版社列表
	 */
	suspend fun getPublications(): Result<List<String>>

	/**
	 * 切换主题选择在选定和未选定之间切换
	 */
	suspend fun toggleTopicSelection(topic: TopicSelection)

	/**
	 * 切换选定的人在选定和未选定之间切换
	 */
	suspend fun togglePersonSelected(person: String)

	/**
	 * 切换已选择发布在选定和未选定之间切换
	 */
	suspend fun togglePublicationSelected(publication: String)

	/**
	 * 观察当前所选主题
	 */
	fun observeTopicsSelected(): Flow<Set<TopicSelection>>

	/**
	 * 观察选定的人
	 */
	fun observePeopleSelected(): Flow<Set<String>>

	/**
	 * 观察所选出版社
	 */
	fun observePublicationSelected(): Flow<Set<String>>
}

/**
 * 主题
 */
data class TopicSelection(val section: String, val topic: String)