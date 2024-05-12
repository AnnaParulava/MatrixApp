package mappers

import model.Matrix

fun mapToMatrix(array: Array<DoubleArray>): Matrix {
    val rows = array.size
    val cols = if (rows > 0) array[0].size else 0
    val data = Array(rows) { i ->
        DoubleArray(cols) { j ->
            array[i][j]
        }
    }
    return Matrix(rows, cols, array)
}

fun matrixToString(matrix: Array<DoubleArray>): String {
    val sb = StringBuilder()
    for (row in matrix) {
        sb.append(row.joinToString(separator = " ", prefix = "[", postfix = "]"))
        sb.append("\n")
    }
    return sb.toString()
}