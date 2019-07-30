#include <jni.h>
#include <string>
#include "art_method.h"

extern "C" JNIEXPORT void JNICALL
Java_com_example_wongki_modularization_sophix_sophix_SopHixHelper_replace(
        JNIEnv *env,
        jclass type,
        jobject newMethod,
        jobject bugMethod) {
    art::mirror::ArtMethod *newArtMethod =
            reinterpret_cast<art::mirror::ArtMethod *> (env->FromReflectedMethod(newMethod));
    art::mirror::ArtMethod *bugArtMethod =
            reinterpret_cast<art::mirror::ArtMethod *> (env->FromReflectedMethod(bugMethod));
    bugArtMethod->declaring_class_ = newArtMethod->declaring_class_;
    bugArtMethod->dex_cache_resolved_methods_ = newArtMethod->dex_cache_resolved_methods_;
    bugArtMethod->access_flags_ = newArtMethod->access_flags_;
    bugArtMethod->dex_cache_resolved_types_ = newArtMethod->dex_cache_resolved_types_;
    bugArtMethod->dex_code_item_offset_ = newArtMethod->dex_code_item_offset_;
    bugArtMethod->dex_method_index_ = newArtMethod->dex_method_index_;
    bugArtMethod->method_index_ = newArtMethod->method_index_;

}