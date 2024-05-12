package com.kinopoisk.matrixapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.ui.screens.MatrixCalculatorScreen
import com.example.ui.screens.MatrixInputScreen
import com.example.ui.screens.MatrixSizeScreen
import com.example.ui.viewmodel.MatrixViewModel

@Composable
fun Navigation(navController: NavHostController, viewModel: MatrixViewModel) {
    NavHost(navController = navController, startDestination = Routes.MatrixCalculatorScreen.route) {
        composable(Routes.MatrixCalculatorScreen.route) {
            MatrixCalculatorScreen { operation ->
                viewModel.selectOperation(operation)
                navigateToMatrixSize(navController)
            }
        }
        composable(Routes.MatrixSizeScreen.route) {
            MatrixSizeScreen(nav = {
                navigatePopUp(
                    navController,
                    Routes.MatrixCalculatorScreen.route
                )
            }) { rows, cols ->
                viewModel.selectMatrixParams(rows, cols)
                navigateToMatrixInput(navController)
            }
        }
        composable(Routes.MatrixInputScreen.route) {
            MatrixInputScreen(
                nav = {
                    navigatePopUp(
                        navController,
                        Routes.MatrixSizeScreen.route
                    )
                },
                viewModel
            )
        }
    }
}

fun navigatePopUp(navController: NavController, route: String) {
    navController.navigate(route) {
        popUpTo(route)
    }
}

fun navigateToMatrixSize(navController: NavController) =
    navController.navigate(Routes.MatrixSizeScreen.route)

fun navigateToMatrixInput(navController: NavController) =
    navController.navigate(Routes.MatrixInputScreen.route)
