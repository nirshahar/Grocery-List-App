package com.example.grocerylist

import androidx.compose.runtime.Composable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

sealed interface LoadingState<T> {
    class Loading<T> : LoadingState<T>
    data class Finished<T>(val data: T) : LoadingState<T>
}

fun <T> loadSuspend(
    scope: CoroutineScope,
    supplier: suspend () -> T
): MutableStateFlow<LoadingState<T>> {
    val mutableFlow: MutableStateFlow<LoadingState<T>> = MutableStateFlow(LoadingState.Loading())

    scope.launch {
        mutableFlow.reloadFromSupplier(supplier)
    }

    return mutableFlow
}

suspend fun <T> MutableStateFlow<LoadingState<T>>.reloadFromSupplier(
    supplier: suspend () -> T
) {
    value = LoadingState.Loading()
    value = LoadingState.Finished(supplier())
}

fun <T> Flow<T>.loading(scope: CoroutineScope): StateFlow<LoadingState<T>> {
    return map { LoadingState.Finished(it) }.stateIn(
        scope = scope,
        initialValue = LoadingState.Loading(),
        started = SharingStarted.Eagerly
    )
}

fun <T> MutableStateFlow<LoadingState<T>>.updateLoaded(function: (T) -> T) {
    update {
        when (it) {
            is LoadingState.Finished -> LoadingState.Finished(function(it.data))
            else -> it
        }
    }
}

@Composable
fun <T> Load(
    data: LoadingState<T>,
    loadingContent: @Composable () -> Unit,
    content: @Composable (T) -> Unit
) {
    when (data) {
        is LoadingState.Loading<T> -> loadingContent()
        is LoadingState.Finished<T> -> content(data.data)
    }
}
