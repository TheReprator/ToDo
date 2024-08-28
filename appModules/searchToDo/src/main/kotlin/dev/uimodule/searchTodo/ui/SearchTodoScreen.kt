package dev.uimodule.searchTodo.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import dev.root.baseUi.composeui.RepWidgetBackButton
import dev.root.baseUi.composeui.RepWidgetEmptyList
import dev.root.baseUi.composeui.RepWidgetSearchTextField
import dev.root.baseUi.theme.Dimens.ExtraSmallPadding
import dev.root.baseUi.theme.Dimens.ToDoCardSize
import dev.root.baseUi.util.isEmpty
import dev.uimodule.searchTodo.R
import dev.uimodule.searchTodo.SearchTodoViewModel
import dev.uimodule.searchTodo.domain.modal.ModalToDo

@Composable
fun SearchTodoScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: SearchTodoViewModel = hiltViewModel(),
) {
    SearchTodoScreen({}, modifier, viewModel)
}

@Composable
fun SearchTodoScreen(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SearchTodoViewModel = hiltViewModel(),
) {
    val paginatedToDoList = viewModel.searchResults.collectAsLazyPagingItems()
    val searchText = viewModel.toDoSearchText.collectAsState()
    val lazyListState = rememberLazyListState()

    SearchTodoScreen(paginatedToDoList, searchText.value.orEmpty(), {
        onBackClick()
        viewModel.updateSearch("")
    }, { text ->
        viewModel.updateSearch(text)
    }, modifier, lazyListState)
}

@Composable
private fun SearchTodoScreen(
    paginatedList: LazyPagingItems<ModalToDo>,
    searchText: String,
    onClickBack: () -> Unit,
    onSearchChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    lazyListState: LazyListState = rememberLazyListState()
) {
    val focusRequest = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        focusRequest.requestFocus()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .then(Modifier.windowInsetsPadding(WindowInsets.statusBars))
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 4.dp)
        ) {
            ToDoSearchWidget(
                modifier = Modifier
                    .weight(1f),
                searchText = searchText,
                focusRequester = focusRequest,
                onSearchChange = onSearchChange,
                onCancelClick = onClickBack
            )
        }

        if(searchText.trim().isEmpty())
            RepWidgetEmptyList(message = stringResource(id = R.string.search_todo_empty))
        else
            ToDoListItemWidget(paginatedList, modifier.fillMaxSize(), lazyListState)
    }
}


@Composable
private fun ToDoSearchWidget(
    modifier: Modifier,
    searchText: String,
    focusRequester: FocusRequester,
    onSearchChange: (String) -> Unit,
    onCancelClick: () -> Unit,
) {
    RepWidgetBackButton(onCancelClick)

    Spacer(Modifier.width(8.dp))

    RepWidgetSearchTextField(
        value = searchText,
        onValueChange = onSearchChange,
        placeholderValue = stringResource(R.string.search_todo),
        modifier = modifier
            .height(50.dp)
            .focusRequester(focusRequester),
        shape = MaterialTheme.shapes.large,
        textStyle = MaterialTheme.typography.titleSmall,
        leadingIcon = {
            Icon(
                imageVector = Icons.Rounded.Search,
                contentDescription = stringResource(id = R.string.search_todo),
            )
        }
    )
}


@Composable
internal fun ToDoListItemWidget(
    toDoList: LazyPagingItems<ModalToDo>,
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

        item {
            when {
                (toDoList.isEmpty) -> {
                    RepWidgetEmptyList(message = stringResource(id = R.string.search_todo_empty))
                }
            }
        }
    }
}


@Composable
fun ToDoItemCardWidget(
    todoItem: ModalToDo
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