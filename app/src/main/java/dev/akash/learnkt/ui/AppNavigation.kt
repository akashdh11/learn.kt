package dev.akash.learnkt.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.akash.learnkt.data.AuthManager
import kotlinx.coroutines.launch

@Composable
fun AppNavigation(authManager: AuthManager) {
    val navController = rememberNavController()
    val startDestination = if (authManager.getCurrentUser() != null) "home" else "login"
    val scope = rememberCoroutineScope()

    NavHost(navController = navController, startDestination = startDestination) {
        composable("login") {
            LoginScreen(
                authManager = authManager,
                onLoginSuccess = { navController.navigate("home") { popUpTo("login") { inclusive = true } } },
                onNavigateToSignUp = { navController.navigate("signup") }
            )
        }
        composable("signup") {
            SignUpScreen(
                authManager = authManager,
                onSignUpSuccess = { navController.navigate("home") { popUpTo("signup") { inclusive = true } } },
                onNavigateToLogin = { navController.navigate("login") { popUpTo("signup") { inclusive = true } } }
            )
        }
        composable("home") {
            HomeScreen(
                authManager = authManager,
                onSignOut = { navController.navigate("login") },
                onStartQuiz = { quizId -> navController.navigate("quiz/$quizId?restart=true") },
                onReviewQuiz = { quizId -> navController.navigate("quiz/$quizId?readOnly=true") },
                onLeaderboardClick = { quizId, topic ->
                    navController.navigate("leaderboard/$quizId/$topic")
                }
            )
        }
        composable("quiz/{quizId}?readOnly={readOnly}&restart={restart}") { backStackEntry ->
            QuizScreen(
                authManager = authManager,
                quizId = backStackEntry.arguments?.getString("quizId") ?: "",
                readOnly = backStackEntry.arguments?.getString("readOnly")?.toBoolean() ?: false,
                restart = backStackEntry.arguments?.getString("restart")?.toBoolean() ?: false,
                onQuizComplete = { navController.popBackStack("home", inclusive = false) }
            )
        }
        composable("leaderboard/{quizId}/{quizTopic}") { backStackEntry ->
            LeaderboardScreen(
                quizId = backStackEntry.arguments?.getString("quizId") ?: "",
                quizTopic = backStackEntry.arguments?.getString("quizTopic")?.replace("%20", " ") ?: "Unknown",
                onBack = { navController.popBackStack("home", inclusive = false) }
            )
        }
    }
}