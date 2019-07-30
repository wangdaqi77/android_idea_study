#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring JNICALL
Java_com_example_wongki_modularization_Main_stringFromJNI(
    JNIEnv *env,
    jobject
){
std::string hello = "Hello from c++";
return env->NewStringUTF(hello.c_str());
}