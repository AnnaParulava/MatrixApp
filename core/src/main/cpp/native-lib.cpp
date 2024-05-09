#include <jni.h>
#include <string>
#include "matrix.h"
#include <iostream>
#include <sstream>

extern "C" {

std::vector<std::vector<double>> init1 = {
        {1.1, 2},
        {3, 4}
};
std::vector<std::vector<double>> init2 = {
        {5, 6},
        {7, 8}
};
// Создаем матрицы
Matrix m1(init1);
Matrix m2(init2);

JNIEXPORT jobjectArray JNICALL Java_com_kinopoisk_matrixapp_MainActivity_matrixFromJNI(
        JNIEnv* env,
        jobject /* this */) {
    Matrix sum = m1.add(m2);
}




JNIEXPORT jstring JNICALL Java_com_kinopoisk_matrixapp_MainActivity_stringFromJNI(
        JNIEnv* env,
        jobject /* this */) {
    Matrix sum = m1.add(m2);
    std::ostringstream stream;
    stream << sum;
    std::string result = stream.str();
    return env->NewStringUTF(result.c_str());
}

// Addition function
JNIEXPORT jint JNICALL Java_com_kinopoisk_matrixapp_MainActivity_add(JNIEnv *env, jobject, jint x, jint y) {
    return x + y;
}
}
