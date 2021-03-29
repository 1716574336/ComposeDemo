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

package com.cenming.composedemo.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import com.cenming.composedemo.ui.state.UiState
import com.cenming.composedemo.ui.state.copyWithResult
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import com.cenming.composedemo.data.Result

/**
 * [produceUiState]的结果对象
 *
 * 打算在调用站点上销毁此类。这是一个示例用法，该示例调用dataSource.loadData（），然后根据结果显示一个UI
 *
 * ```
 * val (result, onRefresh, onClearError) = produceUiState(dataSource) { loadData() }
 * Text(result.value)
 * Button(onClick = onRefresh) { Text("Refresh" }
 * Button(onClick = onClearError) { Text("Clear loading error") }
 * ```
 *
 * @param result (state) 此生产者在状态对象中的当前结果
 * @param onRefresh (event) 触发此生产者的刷新
 * @param onClearError (event) 清除此生产者返回的所有错误值，这对于显示瞬时错误很有用。
 */
data class ProducerResult<T>(
    val result: State<T>,
    val onRefresh: () -> Unit,
    val onClearError: () -> Unit
)

/**
 * 启动协程以从挂起的生产者创建可刷新的[UiState].
 *
 * [Producer]是具有返回[Result]的挂起方法的任何对象。在[block]中调用产生单个值的悬浮方法。
 * 该调用的结果将与事件一起返回以刷新（或再次调用[block]），并返回另一个事件以清除错误结果
 *
 * 希望您在呼叫站点处破坏退货。这是一个示例用法，该示例调用dataSource.loadData（），
 * 然后根据结果显示一个UI
 *
 * ```
 * val (result, onRefresh, onClearError) = produceUiState(dataSource) { loadData() }
 * Text(result.value)
 * Button(onClick = onRefresh) { Text("Refresh" }
 * Button(onClick = onClearError) { Text("Clear loading error") }
 * ```
 *
 * 在请求进行过程中，对onRefresh的重复调用进行了合并.
 *
 * @param producer 从中加载数据的数据源
 * @param block 挂起从数据源生成单个值的lambda
 * @return 数据状态，onRefresh事件和onClearError事件
 */
@Composable
fun <Producer, T> produceUiState(
    producer: Producer,
    block: suspend Producer.() -> Result<T>
): ProducerResult<UiState<T>> = produceUiState(producer, Unit, block)

/**
 * 启动协程以从挂起的生产者创建可刷新的[UiState].
 *
 * [Producer]是具有返回[Result]的挂起方法的任何对象。在[block]中调用产生单个值的悬浮方法。
 * 该调用的结果将与事件一起返回以刷新（或再次调用[block]），并返回另一个事件以清除错误结果
 *
 * 希望您在呼叫站点处破坏退货。这是一个示例用法，该用法调用dataSource.loadData（resourceId），
 * 然后根据结果显示一个UI
 *
 * ```
 * val (result, onRefresh, onClearError) = produceUiState(dataSource, resourceId) {
 *     loadData(resourceId)
 * }
 * Text(result.value)
 * Button(onClick = onRefresh) { Text("Refresh" }
 * Button(onClick = onClearError) { Text("Clear loading error") }
 * ```
 *
 * 在请求进行过程中，对onRefresh的重复调用进行了合并.
 *
 * @param producer 从中加载数据的数据源
 * @param key 生产lambda使用的任何参数，例如资源ID
 * @param block 挂起从数据源生成单个值的lambda
 * @return 数据状态，onRefresh事件和onClearError事件
 */
@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun <Producer, T> produceUiState(
    producer: Producer,
    key: Any?,
    block: suspend Producer.() -> Result<T>
): ProducerResult<UiState<T>> {
    // 发布到此频道将触发一次刷新
    val refreshChannel = remember { Channel<Unit>(Channel.CONFLATED) }
    // 发布到此频道将清除当前的错误情况（如果有）
    val errorClearChannel = remember { Channel<Unit>(Channel.CONFLATED) }

    val result = produceState(UiState<T>(loading = true), producer, key) {
        // 每当协程从生产者或密钥更改重新启动时，请立即清除先前的结果并强制刷新
        value = UiState(loading = true)
        refreshChannel.send(Unit)

        // 启动新的协程以处理错误清除事件异步
        launch {
            // 此for循环将循环直到[produceState]协程被取消
            for (clearEvent in errorClearChannel) {
                // 当errorClearChanel为空时，此for循环将暂停，并在提供下一个值或将其发送给chanel时恢复。
                value = value.copy(exception = null)
            }
        }

        // 此for循环将一直循环直到[produceState]协程被取消.
        for (refreshEvent in refreshChannel) {
            // 每当触发刷新时，再次调用块。当refreshChannel为空时，
            // 此for循环将暂停，并在下一个值被提供或发送到通道时恢复。
            value = value.copy(loading = true)
            value = value.copyWithResult(producer.block())
        }
    }
    return ProducerResult(
        result = result,
        onRefresh = { refreshChannel.offer(Unit) },
        onClearError = { errorClearChannel.offer(Unit) }
    )
}
