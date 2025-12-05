package com.example.petmatch.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.petmatch.ui.feature.forgotpassword.NewPasswordScreen
import com.example.petmatch.ui.feature.forgotpassword.ForgotPasswordScreen
import com.example.petmatch.ui.feature.forgotpassword.VerificationCodeScreen
import com.example.petmatch.ui.feature.home.HomeScreen
import com.example.petmatch.ui.feature.login.LoginScreen
import com.example.petmatch.ui.feature.register.RegisterScreen
import com.example.petmatch.ui.feature.petregister.PetRegisterScreen
import kotlinx.serialization.Serializable


@Serializable object LoginRoute
@Serializable object RegisterRoute
@Serializable object PetRegisterRoute
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
                onNavigateToHome = { userEmail ->
                    //LÓGICA DO ADMIN:
                    if (userEmail.equals("nadine.cabral336@gmail.com", ignoreCase = true)) {
                        // Se for Nadine (admin), vai para o Cadastro de Pets
                        navController.navigate(PetRegisterRoute) {
                            popUpTo<LoginRoute> { inclusive = true }
                        }
                    } else {
                        // Se for qualquer outra pessoa, vai para a Home (Swipe)
                        navController.navigate(HomeRoute(userId = 1)) {
                            popUpTo<LoginRoute> { inclusive = true }
                        }
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

        // ROTA DO ADMIN
        composable<PetRegisterRoute> {
            PetRegisterScreen(
                onLogout = {
                    navController.navigate(LoginRoute) {
                        popUpTo<PetRegisterRoute> { inclusive = true }
                    }
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

        composable<HomeRoute> {
            HomeScreen()
        }
    }
}