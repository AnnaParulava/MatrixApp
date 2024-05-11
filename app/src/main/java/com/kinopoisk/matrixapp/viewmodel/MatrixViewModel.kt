package com.kinopoisk.matrixapp.viewmodel

import repository.MatrixRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import intent.MatrixScreenIntent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import model.Matrix
import model.MatrixScreenState
import javax.inject.Inject

class MatrixViewModel @Inject constructor(
    private val useCase: MatrixRepository
) : ViewModel(), MatrixScreenIntent {

    private val _screenState = MutableStateFlow(MatrixScreenState(Matrix(0, 0, emptyArray())))
    val screenState: StateFlow<MatrixScreenState> = _screenState

    override fun handleMatrixInput(matrix: Matrix) {
        _screenState.value = _screenState.value.copy(matrixInput = matrix)
    }

    override fun clearResult() {
        _screenState.value = _screenState.value.copy(result = null)
    }

    fun addMatrices(matrix1: Matrix, matrix2: Matrix) {
        viewModelScope.launch {
            val result = useCase.addMatrices(matrix1, matrix2)
            _screenState.value = _screenState.value.copy(result = result)
        }
    }

    fun subtractMatrices(matrix1: Matrix, matrix2: Matrix) {
        viewModelScope.launch {
            val result = useCase.subtractMatrices(matrix1, matrix2)
            _screenState.value = _screenState.value.copy(result = result)
        }
    }

    fun multiplyMatrices(matrix1: Matrix, matrix2: Matrix) {
        viewModelScope.launch {
            val result = useCase.multiplyMatrices(matrix1, matrix2)
            _screenState.value = _screenState.value.copy(result = result)
        }
    }
}