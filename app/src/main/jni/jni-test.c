#include <string.h>
#include <jni.h>

JNIEXPORT jstring JNICALL
Java_mio_kon_yyb_ndk_1test_MainActivity_stringFromJNI(JNIEnv *env, jobject instance) {

    return (*env)->NewStringUTF(env, "I come from jni string");
}

JNIEXPORT jint JNICALL
Java_mio_kon_yyb_ndk_1test_MainActivity_intFromJNI(JNIEnv *env, jobject instance) {

    return 24;
}