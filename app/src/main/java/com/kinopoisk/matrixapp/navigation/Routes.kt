package com.kinopoisk.matrixapp.navigation

sealed class Routes(val route: String) {

    data object MatrixCalculatorScreen : Routes("matrixCalculatorScreen")
    data object MatrixInputScreen : Routes("matrixInputScreen")
    data object MatrixSizeScreen : Routes("matrixSizeScreen")
}