import android.util.Log
import model.Matrix
import repository.MatrixRepository

class MatrixRepositoryImpl : MatrixRepository {
    init {
        Log.d("AAA","MatrixRepos")
        System.loadLibrary("matrixapp")
        System.loadLibrary("matrix")
        //    main()
    }

    override suspend fun addMatrices(matrix1: Matrix, matrix2: Matrix): Matrix {
        val resultData = addMatricesJNI(matrix1.data, matrix2.data)
        return Matrix(resultData.size, resultData[0].size, resultData)
    }

    override suspend fun subtractMatrices(matrix1: Matrix, matrix2: Matrix): Matrix {
        val resultData = subtractMatricesJNI(matrix1.data, matrix2.data)
        return Matrix(resultData.size, resultData[0].size, resultData)
    }

    override suspend fun multiplyMatrices(matrix1: Matrix, matrix2: Matrix): Matrix {
        val resultData = multiplyMatricesJNI(matrix1.data, matrix2.data)
        return Matrix(resultData.size, resultData[0].size, resultData)
    }

    override suspend fun transposeMatrix(matrix: Matrix): Matrix {
        val resultData = transposeMatrixJNI(matrix.data)
        return Matrix(resultData.size, resultData[0].size, resultData)
    }

    override suspend fun rankMatrix(matrix: Matrix): Int {
        return rankMatrixJNI(matrix.data)
    }

    override suspend fun determinantMatrix(matrix: Matrix): Double {
        return determinantMatrixJNI(matrix.data)
    }

    private external fun addMatricesJNI(matrix1: Array<DoubleArray>, matrix2: Array<DoubleArray>): Array<DoubleArray>
   private external fun subtractMatricesJNI(matrix1: Array<DoubleArray>, matrix2: Array<DoubleArray>): Array<DoubleArray>
    private external fun multiplyMatricesJNI(matrix1: Array<DoubleArray>, matrix2: Array<DoubleArray>): Array<DoubleArray>
    private external fun transposeMatrixJNI(matrix: Array<DoubleArray>): Array<DoubleArray>
    private external fun rankMatrixJNI(matrix: Array<DoubleArray>): Int
    private external fun determinantMatrixJNI(matrix: Array<DoubleArray>): Double

//    fun main() {
//        val matrix1 = arrayOf(doubleArrayOf(1.1, 2.0), doubleArrayOf(3.0, 4.0))
//        val matrix2 = arrayOf(doubleArrayOf(5.0, 6.0), doubleArrayOf(7.0, 8.0))
//        val resultMatrix = addMatricesJNI(matrix1, matrix2)
//        extracted("add", resultMatrix)
//        extracted("sub", subtractMatricesJNI(matrix1, matrix2))
//        extracted("multi", multiplyMatricesJNI(matrix1, matrix2))
//    }
//
//    private fun extracted(log: String, resultMatrix: Array<DoubleArray>) {
//        var res = ""
//
//        for (row in resultMatrix) {
//            for (element in row) {
//                res += "$element "
//            }
//            println()
//        }
//        Log.d("AAA", "$log $res")
//    }
}

