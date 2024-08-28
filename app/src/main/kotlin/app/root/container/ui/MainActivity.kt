package app.root.container.ui

import android.os.Bundle
import android.view.WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.CompositionLocalProvider
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import app.root.container.ui.navigation.ToDoNavHost
import app.root.container.util.LocalWindowAdaptiveInfo
import dagger.hilt.android.AndroidEntryPoint
import dev.root.baseUi.theme.ContrastAwareToDoTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        enableEdgeToEdgeForTheme()

        super.onCreate(savedInstanceState)

        setContent {
            CompositionLocalProvider(
                LocalWindowAdaptiveInfo provides currentWindowAdaptiveInfo()
            ) {
                ContrastAwareToDoTheme {
                    val navController = rememberNavController()
                    ToDoNavHost(navController)
                }
            }
        }
    }
}

private fun ComponentActivity.enableEdgeToEdgeForTheme() {
    enableEdgeToEdge()
    window.setFlags(FLAG_LAYOUT_NO_LIMITS, FLAG_LAYOUT_NO_LIMITS)
}