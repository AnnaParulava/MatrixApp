#include <jni.h>
#include <string>
#include "matrix.h"
#include <iostream>
#include <sstream>

std::vector<std::vector<double>> createMatrix(JNIEnv* env, jobjectArray matrix, jsize& rows, jsize& cols) {
    rows = env->GetArrayLength(matrix);
    cols = env->GetArrayLength((jdoubleArray)env->GetObjectArrayElement(matrix, 0));

    std::vector<std::vector<double>> result(rows, std::vector<double>(cols));

    for (int i = 0; i < rows; ++i) {
        jdoubleArray rowArray = (jdoubleArray)env->GetObjectArrayElement(matrix, i);
        jdouble* row = env->GetDoubleArrayElements(rowArray, nullptr);
        for (int j = 0; j < cols; ++j) {
            result[i][j] = row[j];
        }
        env->ReleaseDoubleArrayElements(rowArray, row, JNI_ABORT);
    }

    return result;
}

jobjectArray createResultArray(JNIEnv* env, const Matrix& sum) {
    jclass doubleArrayClass = env->FindClass("[D");
    jobjectArray resultArray = env->NewObjectArray(sum.rows, doubleArrayClass, nullptr);

    for (int i = 0; i < sum.rows; ++i) {
        jdoubleArray rowArray = env->NewDoubleArray(sum.cols);
        if (rowArray == nullptr) {
            return nullptr; // Ошибка при создании массива
        }
        env->SetDoubleArrayRegion(rowArray, 0, sum.cols, sum.data[i].data());
        env->SetObjectArrayElement(resultArray, i, rowArray);
        env->DeleteLocalRef(rowArray);
    }
    return resultArray;
}


extern "C" {

JNIEXPORT jobjectArray JNICALL Java_com_kinopoisk_matrixapp_MainActivity_addMatricesJNI(
        JNIEnv* env,
        jobject /* this */,
        jobjectArray matrix1,
        jobjectArray matrix2) {

    jsize rows1, cols1, rows2, cols2;
    std::vector<std::vector<double>> init1 = createMatrix(env, matrix1, rows1, cols1);
    std::vector<std::vector<double>> init2 = createMatrix(env, matrix2, rows2, cols2);

    if (init1.empty() || init2.empty()) {
        return nullptr; // Ошибка при создании матрицы
    }

    Matrix m1(init1);
    Matrix m2(init2);
    Matrix sum = m1.add(m2);

    jobjectArray resultArray = createResultArray(env, sum);

    return resultArray;
}

JNIEXPORT jobjectArray JNICALL Java_com_kinopoisk_matrixapp_MainActivity_subtractMatricesJNI(
        JNIEnv* env,
        jobject /* this */,
        jobjectArray matrix1,
        jobjectArray matrix2) {

    jsize rows1, cols1, rows2, cols2;
    std::vector<std::vector<double>> init1 = createMatrix(env, matrix1, rows1, cols1);
    std::vector<std::vector<double>> init2 = createMatrix(env, matrix2, rows2, cols2);

    if (init1.empty() || init2.empty() || rows1 != rows2 || cols1 != cols2) {
        return nullptr; // Ошибка при создании матрицы или размеры не совпадают
    }

    Matrix m1(init1);
    Matrix m2(init2);
    Matrix result = m1.subtract(m2); // Вычитание матриц

    jobjectArray resultArray = createResultArray(env, result);

    return resultArray;
}

JNIEXPORT jobjectArray JNICALL Java_com_kinopoisk_matrixapp_MainActivity_multiplyMatricesJNI(
        JNIEnv* env,
        jobject /* this */,
        jobjectArray matrix1,
        jobjectArray matrix2) {

    jsize rows1, cols1, rows2, cols2;
    std::vector<std::vector<double>> init1 = createMatrix(env, matrix1, rows1, cols1);
    std::vector<std::vector<double>> init2 = createMatrix(env, matrix2, rows2, cols2);

    if (init1.empty() || init2.empty() || cols1 != rows2) {
        return nullptr; // Ошибка при создании матрицы или неверные размеры для умножения
    }

    Matrix m1(init1);
    Matrix m2(init2);
    Matrix result = m1.multiply(m2); // Умножение матриц

    jobjectArray resultArray = createResultArray(env, result);

    return resultArray;
}


}