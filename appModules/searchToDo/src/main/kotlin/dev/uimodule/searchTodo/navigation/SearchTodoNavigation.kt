package dev.uimodule.searchTodo.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import dev.uimodule.searchTodo.ui.SearchTodoScreen

fun NavGraphBuilder.searchToDoScreen(navController: NavHostController) {
    composable<SearchToDoScreen> {
        SearchTodoScreen(navController = navController)
    }
}
