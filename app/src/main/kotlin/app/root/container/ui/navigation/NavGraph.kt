package app.root.container.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import dev.uimodule.createTodo.navigation.createToDoScreen
import dev.uimodule.searchTodo.navigation.searchToDoScreen
import dev.uimodule.todoList.navigation.toDoListScreen

@Composable
fun ToDoNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        modifier = Modifier.then(modifier),
        navController = navController,
        startDestination = HomeScreen,
    ) {
        homeScreen()
        toDoListScreen(navController)
        createToDoScreen(navController)
        searchToDoScreen(navController)
    }
}