package dev.uimodule.createTodo.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavHostController
import dev.root.baseUi.composeui.RepWidgetAppBar
import dev.root.baseUi.composeui.RepWidgetError
import dev.root.baseUi.composeui.RepWidgetLoader
import dev.root.baseUi.composeui.RepWidgetTextArea
import dev.uimodule.createTodo.CreateTodoViewModel
import dev.uimodule.createTodo.R
import kotlinx.coroutines.flow.filter

@Composable
fun CreateTodoScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: CreateTodoViewModel = hiltViewModel(),
) {
    CreateTodoScreen(true, {}, modifier, viewModel)
}

@Composable
fun CreateTodoScreen(
    shouldShowBack: Boolean,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: CreateTodoViewModel = hiltViewModel(),
    onStartInteraction: () -> Unit = {},
    shouldClosePage: (String) -> Unit = {},
) {
    val toDoTextField = viewModel.createToDo.collectAsState()
    val toDoLoader = viewModel.showLoader.collectAsState()
    val toDoError = viewModel.isError.collectAsState(initial = "")

    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(Unit) {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.shouldNavigateBack.filter { it }.collect {
                shouldClosePage(toDoError.value)
            }
        }
    }

    CreateTodoScreen(toDoLoader.value,
        toDoError.value,
        toDoTextField.value,
        shouldShowBack,
        {
            viewModel.resetToDoText()
            onBackClick()
        }, {
            viewModel.submitToDo()
        },
        modifier.then(Modifier.windowInsetsPadding(WindowInsets.statusBars)),
        {
            onStartInteraction()
            viewModel.updateToDo(it)
        })
}

@Composable
fun CreateTodoScreen(
    showLoader: Boolean,
    showError: String,
    textFieldValue: TextFieldValue,
    shouldShowBack: Boolean,
    onBackClick: () -> Unit,
    onSubmitClick: () -> Unit,
    modifier: Modifier = Modifier,
    onValueChange: (TextFieldValue) -> Unit = {},
) {
    Scaffold(modifier = Modifier.then(modifier),
        topBar = {
            RepWidgetAppBar(
                title = stringResource(R.string.create_todo),
                onBackPressed = onBackClick,
                shouldShowBack = shouldShowBack
            )
        }) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(it),
            contentAlignment = Alignment.Center
        ) {
            AnimatedVisibility(modifier = Modifier.align(Alignment.TopStart), visible = (!showLoader && showError.trim().isEmpty())) {
                CreateTodoScreenMainContent(
                    textFieldValue,
                    onSubmitClick,
                    onValueChange=onValueChange
                )
            }
            RepWidgetLoader(showLoader, modifier = Modifier.size(80.dp))
            RepWidgetError(showError.trim().isNotEmpty(), errorMessage = showError)
        }
    }
}

@Composable
private fun CreateTodoScreenMainContent(
    textFieldValue: TextFieldValue,
    onSubmitClick: () -> Unit,
    modifier: Modifier = Modifier,
    onValueChange: (TextFieldValue) -> Unit = {},
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .then(modifier)
    ) {
        CreateTodoScreenInputText(textFieldValue, { text ->
            onValueChange(text)
        })

        Button(onClick = onSubmitClick, content = {
            Text(stringResource(R.string.create_todo_submit))
        })
    }
}

@Composable
private fun CreateTodoScreenInputText(
    searchText: TextFieldValue,
    onSearchChange: (TextFieldValue) -> Unit
) {
    val focusRequest = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        focusRequest.requestFocus()
    }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp)
    ) {
        WidgetToDoInputArea(
            modifier = Modifier
                .weight(1f),
            searchText = searchText,
            focusRequester = focusRequest,
            onSearchChange = onSearchChange
        )
    }
}

@Composable
private fun WidgetToDoInputArea(
    modifier: Modifier,
    searchText: TextFieldValue,
    focusRequester: FocusRequester,
    onSearchChange: (TextFieldValue) -> Unit
) {
    RepWidgetTextArea(
        value = searchText,
        onValueChange = onSearchChange,
        placeholderValue = stringResource(R.string.create_todo),
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(5.dp))
            .height(150.dp)
            .background(
                color = MaterialTheme.colorScheme.secondary,
                shape = MaterialTheme.shapes.small
            )
            .focusRequester(focusRequester),
        style = MaterialTheme.typography.titleSmall
    )
}


@Preview
@Composable
fun CreateTodoContentScreenPreview() {
    CreateTodoScreen(false, "", TextFieldValue(), true, {}, {})
}

@Preview
@Composable
fun CreateTodoScreenLoaderPreview() {
    CreateTodoScreen(true, "", TextFieldValue(), true, {}, {})
}


@Preview
@Composable
fun CreateTodoScreenErrorPreview() {
    CreateTodoScreen(false, "Error", TextFieldValue(), true, {}, {})
}