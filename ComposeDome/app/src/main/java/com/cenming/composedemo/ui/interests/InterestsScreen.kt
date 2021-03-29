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

package com.cenming.composedemo.ui.interests

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.DrawerValue
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.rememberDrawerState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cenming.composedemo.R
import com.cenming.composedemo.data.interests.InterestsRepository
import com.cenming.composedemo.data.interests.TopicSelection
import com.cenming.composedemo.data.interests.TopicsMap
import com.cenming.composedemo.data.interests.impl.FakeInterestsRepository
import com.cenming.composedemo.data.Result
import com.cenming.composedemo.ui.AppDrawer
import com.cenming.composedemo.ui.Screen
import com.cenming.composedemo.ui.ThemedPreview
import com.cenming.composedemo.utils.produceUiState
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

enum class Sections(val title: String) {
    Topics("Topics"),
    People("People"),
    Publications("Publications")
}

/**
 * 屏幕上单个选项卡的TabContent.
 *
 * 这旨在将选项卡及其内容封装为单个对象。添加它是为了避免将每个选项卡的几个参数从有状态的
 * 可组合对象传递到显示当前选项卡的可组合对象.
 *
 * @param section 该内容用于的选项卡
 * @param section 选项卡的内容，一个描述内容的组合
 */
class TabContent(val section: Sections, val content: @Composable () -> Unit)

/**
 * Stateful InterestsScreen使用[produceUiState]管理状态
 *
 * @param navigateTo (event) 请求导航到 [Screen]
 * @param scaffoldState (state) 屏幕支架的状态
 * @param interestsRepository 此屏幕的数据源
 */
@Composable
fun InterestsScreen(
    navigateTo: (Screen) -> Unit,
    interestsRepository: InterestsRepository,
    scaffoldState: ScaffoldState = rememberScaffoldState()
) {
    // 返回范围为[InterestsScreen]生命周期的[CoroutineScope]。从合成中删除此屏幕时，合并范围将被取消
    val coroutineScope = rememberCoroutineScope()

    // 由于每个部分需要2个状态和1个事件，因此请在此处描述屏幕部分。
    // 使用tabContent将它们传递给无状态的InterestsScreen
    val topicsSection = TabContent(Sections.Topics) {
        val (topics) = produceUiState(interestsRepository) {
            getTopics()
        }
        // collectAsState将在Compose中读取[Flow]
        val selectedTopics by interestsRepository.observeTopicsSelected().collectAsState(setOf())
        val onTopicSelect: (TopicSelection) -> Unit = {
            coroutineScope.launch { interestsRepository.toggleTopicSelection(it) }
        }
        val data = topics.value.data ?: return@TabContent
        TopicList(data, selectedTopics, onTopicSelect)
    }

    val peopleSection = TabContent(Sections.People) {
        val (people) = produceUiState(interestsRepository) {
            getPeople()
        }
        val selectedPeople by interestsRepository.observePeopleSelected().collectAsState(setOf())
        val onPeopleSelect: (String) -> Unit = {
            coroutineScope.launch { interestsRepository.togglePersonSelected(it) }
        }
        val data = people.value.data ?: return@TabContent
        PeopleList(data, selectedPeople, onPeopleSelect)
    }

    val publicationSection = TabContent(Sections.Publications) {
        val (publications) = produceUiState(interestsRepository) {
            getPublications()
        }
        val selectedPublications by interestsRepository.observePublicationSelected()
            .collectAsState(setOf())
        val onPublicationSelect: (String) -> Unit = {
            coroutineScope.launch { interestsRepository.togglePublicationSelected(it) }
        }
        val data = publications.value.data ?: return@TabContent
        PublicationList(data, selectedPublications, onPublicationSelect)
    }

    val tabContent = listOf(topicsSection, peopleSection, publicationSection)
    val (currentSection, updateSection) = rememberSaveable { mutableStateOf(tabContent.first().section) }
    InterestsScreen(
        tabContent = tabContent,
        tab = currentSection,
        onTabChange = updateSection,
        navigateTo = navigateTo,
        scaffoldState = scaffoldState
    )
}

/**
 * 无状态兴趣屏幕显示[tabContent]中指定的标签
 *
 * @param tabContent (slot) 要在此屏幕上显示的标签及其内容，必须是非空列表，标签以此列表指定的顺序显示
 * @param tab (state) 当前要显示的标签，必须位于[tabContent]中
 * @param onTabChange (event) 请求将[tab]中的[tabContent]从[tabContent]更改为另一个标签
 * @param navigateTo (event) 请求导航到 [Screen]
 * @param scaffoldState (state) 屏幕[Scaffold]的状态
 */
@Composable
fun InterestsScreen(
    tabContent: List<TabContent>,
    tab: Sections,
    onTabChange: (Sections) -> Unit,
    navigateTo: (Screen) -> Unit,
    scaffoldState: ScaffoldState,
) {
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        scaffoldState = scaffoldState,
        drawerContent = {
            AppDrawer(
                currentScreen = Screen.Interests,
                closeDrawer = { coroutineScope.launch { scaffoldState.drawerState.close() } },
                navigateTo = navigateTo
            )
        },
        topBar = {
            TopAppBar(
                title = { Text("Interests") },
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
        content = {
            TabContent(tab, onTabChange, tabContent)
        }
    )
}

/**
 * 显示一个选择了[currentSection]的标签行以及相应的[tabContent]的主体.
 *
 * @param currentSection (state) 当前选中的标签
 * @param updateSection (event) 请求更改选项卡选择
 * @param tabContent (slot) 标签及其要显示的内容，必须是非空列表，标签以此列表的顺序显示
 */
@Composable
fun TabContent(
    currentSection: Sections,
    updateSection: (Sections) -> Unit,
    tabContent: List<TabContent>
) {
    val selectedTabIndex = tabContent.indexOfFirst { it.section == currentSection }
    Column {
        TabRow(
            selectedTabIndex = selectedTabIndex
        ) {
            tabContent.forEachIndexed { index, tabContent ->
                Tab(
                    text = { Text(tabContent.section.title) },
                    selected = selectedTabIndex == index,
                    onClick = { updateSection(tabContent.section) }
                )
            }
        }
        Box(modifier = Modifier.weight(1f)) {
            // 显示当前选项卡的内容，它是@Composable（）-> Unit
            tabContent[selectedTabIndex].content()
        }
    }
}

/**
 * 显示主题选项卡的列表
 *
 * @param topics (state) 要显示的主题，按部分映射
 * @param selectedTopics (state) 当前选择的主题
 * @param onTopicSelect (event) 请求更改主题选择
 */
@Composable
fun TopicList(
    topics: TopicsMap,
    selectedTopics: Set<TopicSelection>,
    onTopicSelect: (TopicSelection) -> Unit
) {
    TabWithSections(topics, selectedTopics, onTopicSelect)
}

/**
 * 显示人员列表标签
 *
 * @param people (state) 要显示的人
 * @param selectedPeople (state) 当前选中的人
 * @param onPersonSelect (event) 请求更改人员选择
 */
@Composable
fun PeopleList(
    people: List<String>,
    selectedPeople: Set<String>,
    onPersonSelect: (String) -> Unit
) {
    TabWithTopics(people, selectedPeople, onPersonSelect)
}

/**
 * 显示出版社列表选项卡
 *
 * @param publications (state) 要显示的出版社
 * @param selectedPublications (state) 当前选定的出版社
 * @param onPublicationSelect (event) 请求更改发布选择
 */
@Composable
fun PublicationList(
    publications: List<String>,
    selectedPublications: Set<String>,
    onPublicationSelect: (String) -> Unit
) {
    TabWithTopics(publications, selectedPublications, onPublicationSelect)
}

/**
 * 显示一个简单的主题列表
 *
 * @param topics (state) 要显示的主题
 * @param selectedTopics (state) 当前选择的主题
 * @param onTopicSelect (event) 请求更改主题选择
 */
@Composable
fun TabWithTopics(
    topics: List<String>,
    selectedTopics: Set<String>,
    onTopicSelect: (String) -> Unit
) {
    LazyColumn(modifier = Modifier.padding(top = 16.dp)) {
        items(topics) { topic ->
            TopicItem(
                topic,
                selected = selectedTopics.contains(topic)
            ) { onTopicSelect(topic) }
            TopicDivider()
        }
    }
}

/**
 * 显示主题的分段列表
 *
 * @param sections (state) 要显示的主题，按部分分组
 * @param selectedTopics (state) 当前选定的主题
 * @param onTopicSelect (event) 请求更改主题+部分选择
 */
@Composable
fun TabWithSections(
    sections: TopicsMap,
    selectedTopics: Set<TopicSelection>,
    onTopicSelect: (TopicSelection) -> Unit
) {
    LazyColumn {
        sections.forEach { (section, topics) ->
            item {
                Text(
                    text = section,
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.subtitle1
                )
            }
            items(topics) { topic ->
                TopicItem(
                    itemTitle = topic,
                    selected = selectedTopics.contains(TopicSelection(section, topic))
                ) { onTopicSelect(TopicSelection(section, topic)) }
                TopicDivider()
            }
        }
    }
}

/**
 * 显示全角主题项目
 *
 * @param itemTitle (state) 主题标题
 * @param selected (state) 是当前选择的主题
 * @param onToggle (event) 切换主题选择
 */
@Composable
fun TopicItem(itemTitle: String, selected: Boolean, onToggle: () -> Unit) {
    val image = painterResource(R.drawable.placeholder_1_1)
    Row(
        modifier = Modifier
            .toggleable(
                value = selected,
                onValueChange = { onToggle() }
            )
            .padding(horizontal = 16.dp)
    ) {
        Image(
            painter = image,
            contentDescription = null, // 装饰性的
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .size(56.dp, 56.dp)
                .clip(RoundedCornerShape(4.dp))
        )
        Text(
            text = itemTitle,
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(16.dp),
            style = MaterialTheme.typography.subtitle1
        )
        Spacer(Modifier.weight(1f))
        SelectTopicButton(
            modifier = Modifier.align(Alignment.CenterVertically),
            selected = selected
        )
    }
}

/**
 * 主题的全角分隔线
 */
@Composable
fun TopicDivider() {
    Divider(
        modifier = Modifier.padding(start = 72.dp, top = 8.dp, bottom = 8.dp),
        color = MaterialTheme.colors.surface.copy(alpha = 0.08f)
    )
}

@Preview("Interests screen")
@Composable
fun PreviewInterestsScreen() {
    ThemedPreview {
        InterestsScreen(
            navigateTo = {},
            interestsRepository = FakeInterestsRepository()
        )
    }
}

@Preview("Interests screen dark theme")
@Composable
fun PreviewInterestsScreenDark() {
    ThemedPreview(darkTheme = true) {
        val scaffoldState = rememberScaffoldState(
            drawerState = rememberDrawerState(DrawerValue.Open)
        )
        InterestsScreen(
            navigateTo = {},
            scaffoldState = scaffoldState,
            interestsRepository = FakeInterestsRepository()
        )
    }
}

@Preview("Interests screen drawer open")
@Composable
fun PreviewDrawerOpen() {
    ThemedPreview {
        val scaffoldState = rememberScaffoldState(
            drawerState = rememberDrawerState(DrawerValue.Open)
        )
        InterestsScreen(
            navigateTo = {},
            scaffoldState = scaffoldState,
            interestsRepository = FakeInterestsRepository()
        )
    }
}

@Preview("Interests screen drawer open dark theme")
@Composable
fun PreviewDrawerOpenDark() {
    ThemedPreview(darkTheme = true) {
        val scaffoldState = rememberScaffoldState(
            drawerState = rememberDrawerState(DrawerValue.Open)
        )
        InterestsScreen(
            navigateTo = {},
            scaffoldState = scaffoldState,
            interestsRepository = FakeInterestsRepository()
        )
    }
}

@Preview("Interests screen topics tab")
@Composable
fun PreviewTopicsTab() {
    ThemedPreview {
        TopicList(loadFakeTopics(), setOf(), {})
    }
}

@Preview("Interests screen topics tab dark theme")
@Composable
fun PreviewTopicsTabDark() {
    ThemedPreview(darkTheme = true) {
        TopicList(loadFakeTopics(), setOf(), {})
    }
}

@Composable
fun loadFakeTopics(): TopicsMap {
    val topics = runBlocking {
        FakeInterestsRepository().getTopics()
    }
    return (topics as Result.Success).data
}

@Preview("Interests screen people tab")
@Composable
fun PreviewPeopleTab() {
    ThemedPreview {
        PeopleList(loadFakePeople(), setOf(), { })
    }
}

@Preview("Interests screen people tab dark theme")
@Composable
fun PreviewPeopleTabDark() {
    ThemedPreview(darkTheme = true) {
        PeopleList(loadFakePeople(), setOf(), { })
    }
}

@Composable
fun loadFakePeople(): List<String> {
    val people = runBlocking {
        FakeInterestsRepository().getPeople()
    }
    return (people as Result.Success).data
}

@Preview("Interests screen publications tab")
@Composable
fun PreviewPublicationsTab() {
    ThemedPreview {
        PublicationList(loadFakePublications(), setOf(), { })
    }
}

@Preview("Interests screen publications tab dark theme")
@Composable
fun PreviewPublicationsTabDark() {
    ThemedPreview(darkTheme = true) {
        PublicationList(loadFakePublications(), setOf(), { })
    }
}

@Composable
fun loadFakePublications(): List<String> {
    val publications = runBlocking {
        FakeInterestsRepository().getPublications()
    }
    return (publications as Result.Success).data
}

@Preview("Interests screen tab with topics")
@Composable
fun PreviewTabWithTopics() {
    ThemedPreview {
        TabWithTopics(topics = listOf("Hello", "Compose"), selectedTopics = setOf()) {}
    }
}

@Preview("Interests screen tab with topics dark theme")
@Composable
fun PreviewTabWithTopicsDark() {
    ThemedPreview(darkTheme = true) {
        TabWithTopics(topics = listOf("Hello", "Compose"), selectedTopics = setOf()) {}
    }
}
