package intent

import model.Matrix

interface MatrixScreenIntent {
    fun handleMatrixInput(matrix: Matrix)
    fun clearResult()
}