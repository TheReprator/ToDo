package dev.uimodule.todoList.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import dev.root.baseUi.composeui.RepWidgetAppBar
import dev.root.baseUi.composeui.RepWidgetEmptyList
import dev.root.baseUi.composeui.RepWidgetLoader
import dev.root.baseUi.theme.Dimens.ExtraSmallPadding
import dev.root.baseUi.theme.Dimens.ToDoCardSize
import dev.root.baseUi.util.isEmpty
import dev.uimodule.todoList.R
import dev.uimodule.todoList.ToDoListViewModel
import dev.uimodule.todoList.domain.modal.ModalTodo
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter

@Composable
fun ToDoListScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: ToDoListViewModel = hiltViewModel(),
) {
    ToDoListScreen(true, {}, {}, modifier)
}

@OptIn(FlowPreview::class)
@Composable
fun ToDoListScreen(
    shouldShowFab: Boolean,
    onFabClick: () -> Unit,
    onSearchClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ToDoListViewModel = hiltViewModel(),
) {
    val paginatedToDoList = viewModel.paginatedToDo.collectAsLazyPagingItems()
    val lazyListState =
        rememberLazyListState(initialFirstVisibleItemIndex = viewModel.scrollPosition)

    LaunchedEffect(lazyListState) {
        snapshotFlow {
            lazyListState.firstVisibleItemIndex
        }.debounce(500L)
            .collectLatest {
                viewModel.updateScrollPosition(it)
            }
    }

    LaunchedEffect(lazyListState) {
        snapshotFlow { paginatedToDoList.loadState }
            .distinctUntilChangedBy { it.prepend }
            .filter {
                it.prepend is LoadState.NotLoading &&
                        it.source.prepend is LoadState.NotLoading &&
                        (0 == lazyListState.firstVisibleItemIndex)
            }
            .collect {
                lazyListState.animateScrollToItem(0)
            }
    }

    ToDoListScreen(
        paginatedToDoList,
        shouldShowFab,
        onFabClick,
        onSearchClick,
        modifier,
        lazyListState
    )
}

@Composable
fun ToDoListScreen(
    toDoList: LazyPagingItems<ModalTodo>,
    shouldShowFab: Boolean,
    onFabClick: () -> Unit,
    onSearchClick: () -> Unit,
    modifier: Modifier = Modifier,
    lazyListState: LazyListState = rememberLazyListState()
) {
    Scaffold(modifier = modifier,
        topBar = {
            RepWidgetAppBar(
                title = "Auto hide or Extend FAB",
                onBackPressed = {},
                shouldShowBack = false,
                actionIcon = Icons.Rounded.Search,
                actionDescriptionId = R.string.todo_search,
                onActionClick = onSearchClick
            )
        },
        floatingActionButton = {
            AddTodoFabWidget(shouldShowFab, onFabClick)
        }) {
        ToDoListItemWidget(toDoList, Modifier.padding(it), lazyListState)
    }
}

@Composable
fun AddTodoFabWidget(
    shouldShowFab: Boolean,
    onFabClick: () -> Unit,
) {
    if (shouldShowFab)
        FloatingActionButton(onClick = onFabClick) {
            Icon(Icons.Default.Add, "Floating action button.")
        }
}

@Composable
internal fun ToDoListItemWidget(
    toDoList: LazyPagingItems<ModalTodo>,
    modifier: Modifier = Modifier,
    lazyListState: LazyListState = rememberLazyListState()
) {
    LazyColumn(
        modifier = modifier,
        state = lazyListState,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(8.dp)
    ) {
        items(count = toDoList.itemCount, key = toDoList.itemKey { item ->
            item.id
        }) { index ->

            toDoList[index]?.let {
                ToDoItemCardWidget(todoItem = it)
            }
        }

        val loadState = toDoList.loadState.mediator

        item {
            when {
                (LoadState.Loading == loadState?.append) -> {
                    RepWidgetLoader(Modifier.fillMaxWidth())
                }

                (toDoList.isEmpty) -> {
                    RepWidgetEmptyList(
                        stringResource(R.string.todo_emptyList),
                        Modifier.fillParentMaxSize()
                    )
                }
            }
        }
    }
}


@Composable
fun ToDoItemCardWidget(
    todoItem: ModalTodo
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor =
            MaterialTheme.colorScheme.surfaceVariant
        ),
        modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(15.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Text(
            modifier = Modifier
                .clip(RoundedCornerShape(4.dp))
                .padding(horizontal = ExtraSmallPadding)
                .height(ToDoCardSize),
            text = todoItem.description,
            style = MaterialTheme.typography.bodyMedium.copy()
        )
    }
}