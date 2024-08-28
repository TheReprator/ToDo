package app.root.container.ui.home

import android.os.Parcelable
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffold
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.layout.ThreePaneScaffoldDestinationItem
import androidx.compose.material3.adaptive.layout.calculatePaneScaffoldDirective
import androidx.compose.material3.adaptive.navigation.ThreePaneScaffoldNavigator
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import app.root.container.R
import app.root.container.util.LocalWindowAdaptiveInfo
import dev.root.baseUi.adaptiveInfo.isDetailPaneVisible
import dev.root.baseUi.adaptiveInfo.isListPaneVisible
import dev.root.baseUi.composeui.RepWidgetAlert
import dev.uimodule.createTodo.ui.CreateTodoScreen
import dev.uimodule.searchTodo.ui.SearchTodoScreen
import dev.uimodule.todoList.ui.ToDoListScreen
import kotlinx.parcelize.Parcelize

sealed class DetailPane {
    @Parcelize
    data object Detail : DetailPane(), Parcelable

    @Parcelize
    data object Search : DetailPane(), Parcelable
}

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun ListDetailPaneView(shouldShowDetail: Boolean = false) {

    val listDetailNavigator = rememberListDetailPaneScaffoldNavigator<DetailPane>(
        scaffoldDirective = calculatePaneScaffoldDirective(LocalWindowAdaptiveInfo.current),

        initialDestinationHistory = listOfNotNull(
            ThreePaneScaffoldDestinationItem(ListDetailPaneScaffoldRole.List),
            ThreePaneScaffoldDestinationItem<DetailPane.Detail>(ListDetailPaneScaffoldRole.Detail).takeIf {
                shouldShowDetail
            },
            ThreePaneScaffoldDestinationItem<DetailPane.Search>(ListDetailPaneScaffoldRole.Detail).takeIf {
                shouldShowDetail
            }
        ),
    )

    BackHandler(listDetailNavigator.canNavigateBack()) {
        listDetailNavigator.navigateBack()
    }

    ListDetailPaneViewScaffold(listDetailNavigator)
}

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun ListDetailPaneViewScaffold(
    navigator: ThreePaneScaffoldNavigator<DetailPane>
) {
    var detailPane by rememberSaveable { mutableStateOf<DetailPane>(DetailPane.Detail) }
    var shouldShowErrorPopUp by rememberSaveable { mutableStateOf(false) }

    fun onNewsClickShowDetailPane(pane: DetailPane) {
        detailPane = pane
        navigator.navigateTo(ListDetailPaneScaffoldRole.Detail, detailPane)
    }

    if(shouldShowErrorPopUp)
        RepWidgetAlert(stringResource(R.string.error_alert_failed_add), stringResource(R.string.dialog_ok), {
            shouldShowErrorPopUp = false
        })

    ListDetailPaneScaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.inverseOnSurface),
        value = navigator.scaffoldValue,
        directive = navigator.scaffoldDirective,
        listPane = {
            AnimatedPane {
                ToDoListScreen(
                    shouldShowFab = !navigator.isDetailPaneVisible(), {
                        onNewsClickShowDetailPane(DetailPane.Detail)
                    }, onSearchClick = {
                        onNewsClickShowDetailPane(DetailPane.Search)
                    }
                )
            }
        },
        detailPane = {
                AnimatedPane {
                    NavigatorDetailPane(detailPane, navigator, { pane ->
                        onNewsClickShowDetailPane(pane)
                    }, {
                        shouldShowErrorPopUp = it
                    })
                }
        },
    )
}


@OptIn(ExperimentalMaterial3AdaptiveApi::class)
fun ThreePaneScaffoldNavigator<DetailPane>.handleBack() {
    if (canNavigateBack()) {
        navigateBack()
    }
}

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun NavigatorDetailPane(detailPane: DetailPane, navigator: ThreePaneScaffoldNavigator<DetailPane>,
                        paneUpdate: (DetailPane) -> Unit, showErrorPopUp:(Boolean) -> Unit) {
        when (detailPane) {
            DetailPane.Detail -> CreateTodoScreen(
                shouldShowBack = !navigator.isListPaneVisible(),
                onBackClick = {
                    navigator.handleBack()
                }, onStartInteraction = {
                    paneUpdate(DetailPane.Detail)
                }, shouldClosePage = { error ->
                    navigator.handleBack()
                    showErrorPopUp(error.trim().isNotEmpty())
                })

            DetailPane.Search -> SearchTodoScreen(
                onBackClick = {
                    val isListPaneVisible = navigator.isListPaneVisible()
                    navigator.handleBack()
                    if(isListPaneVisible) {
                        paneUpdate(DetailPane.Detail)
                    }
                })
        }
}