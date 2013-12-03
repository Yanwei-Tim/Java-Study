/* DO NOT EDIT THIS FILE - it is machine generated */

#include <windows.h>
#include "jni.h"
#include "jni_md.h"

/* Header for class Test */

#ifndef _Included_Test
#define _Included_Test
#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     Test
 * Method:    showVoidContent
 * Signature: ()V
 */
 JNIEXPORT void JNICALL Java_Test_showVoidContent
  (JNIEnv *, jclass);

/*
 * Class:     Test
 * Method:    showReturnContent
 * Signature: ()Ljava/lang/String;
 */
 JNIEXPORT jstring JNICALL Java_Test_showReturnContent
  (JNIEnv *, jclass);

/*
 * Class:     Test
 * Method:    getContent
 * Signature: (Ljava/lang/String;IJD)Ljava/lang/String;
 */
 JNIEXPORT jstring JNICALL Java_Test_getContent
  (JNIEnv *, jclass, jstring, jint, jlong, jdouble);

/*
 * Class:     Test
 * Method:    getOneContent
 * Signature: (Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
 */
 JNIEXPORT jstring JNICALL Java_Test_getOneContent
  (JNIEnv *, jclass, jstring, jstring);

/*
 * Class:     Test
 * Method:    getTwoDouble
 * Signature: (DDLjava/lang/String;)D
 */
extern JNIEXPORT jdouble JNICALL Java_Test_getTwoDouble
  (JNIEnv *, jclass, jdouble, jdouble, jstring);

#ifdef __cplusplus
}
#endif
#endif
