#include "JNI_Dll.h"
#include <malloc.h>

JNIEXPORT void JNICALL Java_Test_showVoidContent(JNIEnv *env, jclass cls){

}

 JNIEXPORT jstring  JNICALL Java_Test_getOneContent
(JNIEnv *env, jclass cls, jstring str1, jstring str2)
{
	const char *szStr1 = env->GetStringUTFChars(str1, 0);
	const char *szStr2 = env->GetStringUTFChars(str2, 0);
	int len = strlen(szStr1) + strlen(szStr2)+ strlen("~ test OK");
	char *buff = (char*)alloca(len+1);
	memcpy(buff, szStr1, strlen(szStr1));
	memcpy(buff+strlen(szStr1), szStr2, strlen(szStr2));
	memcpy(buff+strlen(szStr1)+strlen(szStr2), "~ test OK", strlen("~ test OK"));
	buff[len] = '\0';
	return env->NewStringUTF(buff);
}