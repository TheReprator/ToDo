package dev.uimodule.todoList.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import dev.uimodule.todoList.ui.ToDoListScreen


fun NavGraphBuilder.toDoListScreen(navController: NavHostController) {
    composable<ListToDoScreen> {
        ToDoListScreen(navController = navController)
    }
}
