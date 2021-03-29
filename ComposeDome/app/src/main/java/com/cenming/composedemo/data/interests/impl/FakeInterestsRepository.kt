package com.cenming.composedemo.data.interests.impl

import com.cenming.composedemo.data.Result
import com.cenming.composedemo.data.interests.InterestsRepository
import com.cenming.composedemo.data.interests.TopicSelection
import com.cenming.composedemo.data.interests.TopicsMap
import com.cenming.composedemo.utils.addOrRemove
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock


/**
 * Created by Cenming on 2021/3/4.
 * 功能: InterestRepository 的实现，可同步返回主题，人员和出版社的硬编码列表
 */
@OptIn(ExperimentalCoroutinesApi::class)
class FakeInterestsRepository : InterestsRepository {
	private val mTopics by lazy {
		mapOf(
			"Android" to listOf("Jetpack Compose", "Kotlin", "Jetpack"),
			"Programming" to listOf("Kotlin", "Declarative UIs", "Java"),
			"Technology" to listOf("Pixel", "Google")
		)
	}

	private val mPeople by lazy {
		listOf(
			"Kobalt Toral", "K'Kola Uvarek", "Kris Vriloc", "Grala Valdyr", "Kruel Valaxar",
			"L'Elij Venonn", "Kraag Solazarn", "Tava Targesh", "Kemarrin Muuda"
		)
	}

	private val mPublications by lazy {
		listOf(
			"Kotlin Vibe", "Compose Mix", "Compose Breakdown", "Android Pursue", "Kotlin Watchman",
			"Jetpack Ark", "Composeshack", "Jetpack Point", "Compose Tribune"
		)
	}

	// 现在，将选择保存在内存中
	private val mSelectedTopics = MutableStateFlow(setOf<TopicSelection>())
	private val mSelectedPeople = MutableStateFlow(setOf<String>())
	private val mSelectedPublications = MutableStateFlow(setOf<String>())

	/**
	 * 用于使可读取和更新状态的挂起函数安全地从任何线程调用
	 */
	private val mMutex = Mutex()

	override suspend fun getTopics(): Result<TopicsMap> = Result.Success(this.mTopics)

	override suspend fun getPeople(): Result<List<String>> = Result.Success(this.mPeople)

	override suspend fun getPublications(): Result<List<String>> =
			Result.Success(this.mPublications)

	override suspend fun toggleTopicSelection(topic: TopicSelection) {
		this.mMutex.withLock {
			this.mSelectedTopics.setSelectedData(topic)
		}
	}

	override suspend fun togglePersonSelected(person: String) {
		this.mMutex.withLock {
			this.mSelectedPeople.setSelectedData(person)
		}
	}

	override suspend fun togglePublicationSelected(publication: String) {
		this.mMutex.withLock {
			this.mSelectedPublications.setSelectedData(publication)
		}
	}

	override fun observeTopicsSelected(): Flow<Set<TopicSelection>> = this.mSelectedTopics

	override fun observePeopleSelected(): Flow<Set<String>> = this.mSelectedPeople

	override fun observePublicationSelected(): Flow<Set<String>> = this.mSelectedPublications

	private fun <T> MutableStateFlow<Set<T>>.setSelectedData(data : T){
		this.value = this.value.toMutableSet()
				.addOrRemove(data)
	}
}