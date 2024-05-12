package repository

import model.Matrix


interface MatrixRepository {
    suspend fun addMatrices(matrix1: Matrix, matrix2: Matrix): Matrix
    suspend fun subtractMatrices(matrix1: Matrix, matrix2: Matrix): Matrix
    suspend fun multiplyMatrices(matrix1: Matrix, matrix2: Matrix): Matrix
    suspend fun transposeMatrix(matrix: Matrix): Matrix
    suspend fun rankMatrix(matrix: Matrix): Int
    suspend fun determinantMatrix(matrix: Matrix): Double
}