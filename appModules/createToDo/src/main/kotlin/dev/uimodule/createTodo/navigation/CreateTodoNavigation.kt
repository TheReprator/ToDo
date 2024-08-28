package dev.uimodule.createTodo.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import dev.uimodule.createTodo.ui.CreateTodoScreen

fun NavGraphBuilder.createToDoScreen(navController: NavHostController) {
    composable<CreateToDoScreen> {
        CreateTodoScreen(navController = navController)
    }
}
