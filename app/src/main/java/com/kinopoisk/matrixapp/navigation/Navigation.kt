package com.kinopoisk.matrixapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.ui.screens.MatrixCalculatorScreen
import com.example.ui.screens.MatrixInputScreen
import com.example.ui.screens.MatrixSizeScreen

@Composable
fun Navigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Routes.MatrixCalculatorScreen.route) {
        composable(Routes.MatrixCalculatorScreen.route) {
            MatrixCalculatorScreen {
                navigateToMatrixSize(
                    navController
                )
            }
        }
        composable(Routes.MatrixSizeScreen.route) {
            MatrixSizeScreen({
                navigateToMatrixInput(
                    navController
                )
            })
        }
        composable(Routes.MatrixInputScreen.route) { MatrixInputScreen() }
    }
}

fun navigateToMainScreen(navController: NavController, route: String) {
    navController.navigate(route) {
        popUpTo(Routes.MatrixCalculatorScreen.route)
    }
}

fun navigateToMatrixSize(navController: NavController) =
    navController.navigate(Routes.MatrixSizeScreen.route)

fun navigateToMatrixInput(navController: NavController) =
    navController.navigate(Routes.MatrixInputScreen.route)
