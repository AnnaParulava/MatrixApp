package usecase

import model.Matrix

interface MatrixUseCase {
    suspend fun addMatrices(matrix1: Matrix, matrix2: Matrix): Matrix
    suspend fun subtractMatrices(matrix1: Matrix, matrix2: Matrix): Matrix
    suspend fun multiplyMatrices(matrix1: Matrix, matrix2: Matrix): Matrix
}