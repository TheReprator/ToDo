package dev.root.baseUi.util

import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems

val <T : Any> LazyPagingItems<T>.isEmpty
    get() = loadState.source.refresh is LoadState.NotLoading && loadState.append.endOfPaginationReached && itemCount == 0
