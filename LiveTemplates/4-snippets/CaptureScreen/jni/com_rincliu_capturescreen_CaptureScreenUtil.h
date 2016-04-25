#include <jni.h>

#ifndef _Included_com_rincliu_capturescreen_CaptureScreenUtil
#define _Included_com_rincliu_capturescreen_CaptureScreenUtil
#ifdef __cplusplus
extern "C" {
#endif

JNIEXPORT void JNICALL Java_com_rincliu_capturescreen_CaptureScreenUtil_captureScreenToFile
  (JNIEnv *, jclass, jstring);

#ifdef __cplusplus
}
#endif
#endif
