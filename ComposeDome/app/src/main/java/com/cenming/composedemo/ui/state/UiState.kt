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

package com.cenming.composedemo.ui.state

import com.cenming.composedemo.data.Result


/**
 * 不可变的数据类，允许独立管理加载，数据和异常
 *
 * 这对于希望在加载时显示上一次成功结果的屏幕或以后的刷新导致错误的屏幕很有用
 */
data class UiState<T>(
    val loading: Boolean = false,
    val exception: Exception? = null,
    val data: T? = null
) {
    /**
     * 如果其中包含错误，则为真
     */
    val hasError: Boolean
        get() = exception != null

    /**
     * 如果这表示首次加载，则为true
     */
    val initialLoad: Boolean
        get() = data == null && loading && !hasError
}

/**
 * 根据结果 Result<T>复制UiState <T>
 *
 * Result.Success 将设置所有字段 Result.Error 将重置加载和仅异常
 */
fun <T> UiState<T>.copyWithResult(value: Result<T>): UiState<T> {
    return when (value) {
        is Result.Success -> copy(loading = false, exception = null, data = value.data)
        is Result.Error -> copy(loading = false, exception = value.exception)
    }
}
