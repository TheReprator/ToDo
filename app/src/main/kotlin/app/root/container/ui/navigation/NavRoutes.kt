package app.root.container.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import app.root.container.ui.home.ListDetailPaneView
import kotlinx.serialization.Serializable

@Serializable
object HomeScreen

fun NavGraphBuilder.homeScreen() {
    composable<HomeScreen> {
        ListDetailPaneView()
    }
}
