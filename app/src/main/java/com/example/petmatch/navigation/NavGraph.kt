package com.example.petmatch.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.petmatch.ui.feature.forgotpassword.NewPasswordScreen
import com.example.petmatch.ui.feature.forgotpassword.ForgotPasswordScreen
import com.example.petmatch.ui.feature.forgotpassword.VerificationCodeScreen
import com.example.petmatch.ui.feature.login.LoginScreen
import com.example.petmatch.ui.feature.register.RegisterScreen
import kotlinx.serialization.Serializable


@Serializable object LoginRoute
@Serializable object RegisterRoute
@Serializable object ForgotPasswordRoute
@Serializable object VerificationCodeRoute
@Serializable object NewPasswordRoute
@Serializable data class HomeRoute(val userId: Long)

@Composable
fun NavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = LoginRoute
    ) {
        composable<LoginRoute> {
            LoginScreen(
                onNavigateToHome = {
                    navController.navigate(HomeRoute(userId = 1)) {
                        popUpTo<LoginRoute> { inclusive = true }
                    }
                },
                onNavigateToRegister = {
                    navController.navigate(RegisterRoute)
                },
                onNavigateToForgotPassword = {
                    navController.navigate(ForgotPasswordRoute)
                }
            )
        }

        composable<RegisterRoute> {
            RegisterScreen(
                onNavigateToLogin = {
                    navController.popBackStack()
                },
                onRegisterSuccess = {
                    navController.popBackStack()
                }
            )
        }

        composable<ForgotPasswordRoute> {
            ForgotPasswordScreen(
                onNavigateToLogin = {
                    navController.popBackStack()
                },
                onNavigateToVerificationCode = {
                    navController.navigate(VerificationCodeRoute)
                }
            )
        }

        composable<VerificationCodeRoute> {
            VerificationCodeScreen(
                onCodeVerified = {
                    navController.navigate(NewPasswordRoute)
                }
            )
        }

        composable<NewPasswordRoute> {
            NewPasswordScreen(
                onResetSuccess = {
                    navController.popBackStack(LoginRoute, inclusive = false)
                }
            )
        }

        composable<HomeRoute> { backStackEntry ->
            val args = backStackEntry.toRoute<HomeRoute>()

            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "Bem-vindo à Home! ID do Usuário: ${args.userId}")
            }
        }
    }
}