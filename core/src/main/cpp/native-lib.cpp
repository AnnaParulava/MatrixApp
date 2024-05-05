#include <jni.h>
#include <string>
//#include "matrix.h"

#include <jni.h>
#include <string>

extern "C" {

JNIEXPORT jstring JNICALL Java_com_kinopoisk_matrixapp_MainActivity_stringFromJNI(
        JNIEnv* env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}

// Addition function
JNIEXPORT jint JNICALL Java_com_kinopoisk_matrixapp_MainActivity_add(JNIEnv *env, jobject, jint x, jint y) {
    return x + y;
}

} // extern "C"



////Subtraction function
//extern "C" JNIEXPORT jint  extern "C" JNICALL
//Java_com_example_myfirstcppapp_MainActivity_sub( JNIEnv *env, jobject, jint x, jint y) {
//
//    //return an integer
//    return x - y;
//}
//
////Multiplication function
//extern "C" JNIEXPORT jint  extern "C" JNICALL
//Java_com_example_myfirstcppapp_MainActivity_multiply( JNIEnv *env, jobject, jint x, jint y) {
//
//    //return an integer
//    return x * y;
//}
//
////Division function
//extern "C" JNIEXPORT jint  extern "C" JNICALL
//Java_com_example_myfirstcppapp_MainActivity_divide( JNIEnv *env, jobject, jint x, jint y) {
//
//    //return an integer
//    return x / y;
//}
