package repository

import model.Matrix


interface MatrixRepository {
    suspend fun addMatrices(matrix1: Matrix, matrix2: Matrix): Matrix
    suspend fun subtractMatrices(matrix1: Matrix, matrix2: Matrix): Matrix
    suspend fun multiplyMatrices(matrix1: Matrix, matrix2: Matrix): Matrix
}