/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class abel_serialport_SerialPort */

#ifndef _Included_abel_serialport_SerialPort
#define _Included_abel_serialport_SerialPort
#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     abel_serialport_SerialPort
 * Method:    open
 * Signature: (Ljava/lang/String;II)Ljava/io/FileDescriptor;
 */
JNIEXPORT jobject JNICALL Java_abel_serialport_SerialPort_open
  (JNIEnv *, jclass, jstring, jint, jint);

/*
 * Class:     abel_serialport_SerialPort
 * Method:    close
 * Signature: (Ljava/io/FileDescriptor;)V
 */
JNIEXPORT void JNICALL Java_abel_serialport_SerialPort_close
  (JNIEnv *, jclass, jobject);

#ifdef __cplusplus
}
#endif
#endif
