
#include "ffmpeg.h"
#include <jni.h>

#ifdef ANDROID

#include <android/log.h>

#define TAG "TAG"

#define ALOGE(fmt, ...) __android_log_vprint(ANDROID_LOG_ERROR, TAG, fmt, ##__VA_ARGS__)
#define ALOGI(fmt, ...) __android_log_vprint(ANDROID_LOG_INFO, TAG, fmt, ##__VA_ARGS__)
#define ALOGD(fmt, ...) __android_log_vprint(ANDROID_LOG_DEBUG, TAG, fmt, ##__VA_ARGS__)
#define ALOGW(fmt, ...) __android_log_vprint(ANDROID_LOG_WARN, TAG, fmt, ##__VA_ARGS__)
#define ALOGV(fmt, ...) __android_log_vprint(ANDROID_LOG_VERBOSE, TAG, fmt, ##__VA_ARGS__)
#else
#define ALOGE printf
#define ALOGI printf
#define ALOGD printf
#define ALOGW printf
#endif

// 是否启用 debug 默认不启用
jboolean FFMPEG_ANDROID_DEBUG = 0;
static void ffmpeg_android_log_callback(void *ptr, int level, const char *fmt, va_list vl){
    if (FFMPEG_ANDROID_DEBUG){
        switch(level) {
            case AV_LOG_DEBUG:
                ALOGD(fmt, vl);
                break;
            case AV_LOG_VERBOSE:
                ALOGV(fmt, vl);
                break;
            case AV_LOG_INFO:
                ALOGI(fmt, vl);
                break;
            case AV_LOG_WARNING:
                ALOGW(fmt, vl);
                break;
            case AV_LOG_ERROR:
                ALOGE(fmt, vl);
                break;
        }
    }
}


/**
 * ffmpeg命令执行函数
 */
JNIEXPORT jint JNICALL
Java_com_example_ffmpegandroidcompress_FFMpegNativeBridge_RunCommand(JNIEnv *env, jobject type,
        jobjectArray commands){
    int argc = (*env)->GetArrayLength(env,commands);
    char *argv[argc];
    int i;
    for (i = 0; i < argc; i++) {
        jstring js = (jstring) (*env)->GetObjectArrayElement(env,commands, i);
        argv[i] = (char *) (*env)->GetStringUTFChars(env,js, 0);
    }
    return run_command(argc,argv);
}

/**
 * 开启日志函数
 */
JNIEXPORT void JNICALL
Java_com_example_ffmpegandroidcompress_FFMpegNativeBridge_setDebug(JNIEnv *env, jclass type, jboolean debug) {
    FFMPEG_ANDROID_DEBUG = debug;
    if (debug){
        av_log_set_callback(ffmpeg_android_log_callback);
    } else {
        av_log_set_callback(NULL);
    }
}
