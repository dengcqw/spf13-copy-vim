#include <utils/Log.h>

#include <binder/IPCThreadState.h>
#include <binder/ProcessState.h>
#include <binder/IServiceManager.h>
#include <binder/IMemory.h>

#include <surfaceflinger/ISurfaceComposer.h>

#include <SkImageEncoder.h>
#include <SkBitmap.h>

#include "com_rincliu_capturescreen_CaptureScreenUtil.h"

using namespace android;

JNIEXPORT void JNICALL Java_com_rincliu_capturescreen_CaptureScreenUtil_captureScreenToFile
  (JNIEnv *env, jclass clazz, jstring fileName) {

    const String16 name("SurfaceFlinger");
    sp<ISurfaceComposer> composer;
    getService(name, &composer);

    sp<IMemoryHeap> heap;
    uint32_t w, h;
    PixelFormat f;
    status_t err = composer->captureScreen(0, &heap, &w, &h, &f, 0, 0);
    if (err != NO_ERROR) {
        fprintf(stderr, "Capture screen failed: %s\n", strerror(-err));
        return;
    }

    LOGD("Capture screen success: w=%u, h=%u, pixels=%p\n",
            w, h, heap->getBase());

    SkBitmap b;
    b.setConfig(SkBitmap::kARGB_8888_Config, w, h);
    b.setPixels(heap->getBase());
    SkImageEncoder::EncodeFile(env->GetStringUTFChars(fileName, 0), b,
            SkImageEncoder::kPNG_Type, SkImageEncoder::kDefaultQuality);

}

JNIEXPORT jint JNICALL JNI_OnLoad(JavaVM* vm, void* reserved) {
    return JNI_VERSION_1_6;
}

JNIEXPORT void JNICALL JNI_OnUnload(JavaVM* vm, void* reserved) {

}
