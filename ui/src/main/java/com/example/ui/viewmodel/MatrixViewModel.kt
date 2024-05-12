package com.example.ui.viewmodel

import android.util.Log
import repository.MatrixRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import mappers.mapToMatrix
import intent.MatrixScreenIntent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import model.Matrix
import model.MatrixParams
import model.MatrixScreenState
import javax.inject.Inject
import utlis.Operation

class MatrixViewModel @Inject constructor(
    private val useCase: MatrixRepository,
) : ViewModel(), MatrixScreenIntent {

    private var operation: Operation? = null
    private var matrixParams: MatrixParams? = null

    private val initialRowCount = 2
    private val initialColumnCount = 2

    private val emptyMatrix = Array(initialRowCount) { DoubleArray(initialColumnCount) { 0.0 } }
    private val _screenState = MutableStateFlow(
        MatrixScreenState(
            Matrix(
                initialRowCount,
                initialColumnCount,
                emptyMatrix
            )
        )
    )
    val screenState: StateFlow<MatrixScreenState> = _screenState

    //если ищем ранг, детерминант или транспонируем
    fun checkToShowSecondMatrix(): Boolean =
        operation !in listOf(Operation.Rank, Operation.Determinant, Operation.Transpose)

    fun chechToShowResultMatrix(): Boolean =
        operation !in listOf(Operation.Rank, Operation.Determinant)

    fun selectOperation(operation: Operation) {
        this.operation = operation
        Log.d("AAA", "operation $operation ")
    }

    fun selectMatrixParams(rows: Int, cols: Int) {
        matrixParams = MatrixParams(rows, cols)
        Log.d("AAA", "size $rows $cols")
    }

    fun getSelectedOperation(): Operation? {
        return operation
    }

    fun getMatrixParams(): MatrixParams? {
        return matrixParams
    }

    fun operation(matrix1: Array<DoubleArray>, matrix2: Array<DoubleArray>) {
        when (getSelectedOperation()) {
            Operation.Add -> addMatrices(matrix1, matrix2)
            Operation.Multiply -> multiplyMatrices(matrix1, matrix2)
            Operation.Subtract -> subtractMatrices(matrix1, matrix2)
            Operation.Transpose -> transposeMatrix(matrix1)
            Operation.Rank -> rankMatrix(matrix1)
            Operation.Determinant -> determinantMatrix(matrix1)
            else -> throw Exception("no operation selected")
        }
    }

    override fun handleMatrixInput(matrix: Matrix) {
        _screenState.value = _screenState.value.copy(matrixInput = matrix)
    }

    override fun clearResult() {
        _screenState.value = _screenState.value.copy(result = null)
    }

    private fun addMatrices(matrix1: Array<DoubleArray>, matrix2: Array<DoubleArray>) {
        viewModelScope.launch {
            val matrix1Instance = mapToMatrix(matrix1)
            val matrix2Instance = mapToMatrix(matrix2)
            val result = useCase.addMatrices(matrix1Instance, matrix2Instance)

            val resultString = result.data.joinToString("\n") { row ->
                row.joinToString(" ") // Объединяем элементы в строку через пробел
            }
            Log.d("AAA", "result:\n$resultString") // Выводим результат

            _screenState.value = _screenState.value.copy(result = result)
        }
    }

    private fun subtractMatrices(matrix1: Array<DoubleArray>, matrix2: Array<DoubleArray>) {
        viewModelScope.launch {
            val matrix1Instance = mapToMatrix(matrix1)
            val matrix2Instance = mapToMatrix(matrix2)
            val result = useCase.subtractMatrices(matrix1Instance, matrix2Instance)
            _screenState.value = _screenState.value.copy(result = result)
        }
    }

    private fun multiplyMatrices(matrix1: Array<DoubleArray>, matrix2: Array<DoubleArray>) {
        viewModelScope.launch {
            val matrix1Instance = mapToMatrix(matrix1)
            val matrix2Instance = mapToMatrix(matrix2)
            val result = useCase.multiplyMatrices(matrix1Instance, matrix2Instance)
            _screenState.value = _screenState.value.copy(result = result)
        }
    }

    private fun transposeMatrix(matrix: Array<DoubleArray>) {
        viewModelScope.launch {
            val matrixInstance = mapToMatrix(matrix)
            val result = useCase.transposeMatrix(matrixInstance)
            _screenState.value = _screenState.value.copy(result = result)
        }
    }

    private fun rankMatrix(matrix: Array<DoubleArray>) {
        viewModelScope.launch {
            val matrixInstance = mapToMatrix(matrix)
            val rank = useCase.rankMatrix(matrixInstance)
            // Создаем матрицу-результат только для отображения ранга
            val result = Matrix(1, 1, arrayOf(doubleArrayOf(rank.toDouble())))
            _screenState.value = _screenState.value.copy(result = result)
        }
    }

    private fun determinantMatrix(matrix: Array<DoubleArray>) {
        viewModelScope.launch {
            val matrixInstance = mapToMatrix(matrix)
            val determinant = useCase.determinantMatrix(matrixInstance)
            // Создаем матрицу-результат только для отображения определителя
            val result = Matrix(1, 1, arrayOf(doubleArrayOf(determinant)))
            _screenState.value = _screenState.value.copy(result = result)
        }
    }

}