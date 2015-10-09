

`Android Studio 1.3` 版本开始支持了NDK开发.试用了一下发现的确方便了许多,不再需要mk文件.

### 准备工作

1.在`SDK-Tools`中安装`Android-Ndk`'

2.修改工程目录下的`build,gradle`(不是Model下的文件)

```
dependencies {
//classpath 'com.android.tools.build:gradle:1.3.0'
classpath 'com.android.tools.build:gradle-experimental:0.2.0'    
    }
```

<!--more-->

3.修改`gradle->wrapper`目录下的`gradle-wrapper.properties`,修改为2.5版本

```
distributionUrl=https\://services.gradle.org/distributions/gradle-2.5-all.zip
```

4.修改Model下的`build.gradle`文件

```
apply plugin: 'com.android.model.application'

model {
    android {
        compileSdkVersion = 23
        buildToolsVersion = "23.0.1"

        defaultConfig.with {
            applicationId = "mio.kon.yyb.ndk_test"
            minSdkVersion.apiLevel = 14
            targetSdkVersion.apiLevel = 22
            versionCode = 1
            versionName = "1.0"
        }
        compileOptions.with {
            sourceCompatibility=JavaVersion.VERSION_1_7
            targetCompatibility=JavaVersion.VERSION_1_7
        }
    }
    android.ndk {
        moduleName = "jni-test"
        /*
         * Other ndk flags configurable here are
         * cppFlags += "-fno-rtti"
         * cppFlags += "-fno-exceptions"
         * ldLibs    = ["android", "log"]
         * stl       = "system"
         */
    }

    android.productFlavors {
        // for detailed abiFilter descriptions, refer to "Supported ABIs" @
        // https://developer.android.com/ndk/guides/abis.html#sa
        create("arm") {
            ndk.abiFilters += "armeabi"
        }
        create("arm7") {
            ndk.abiFilters += "armeabi-v7a"
        }
        create("arm8") {
            ndk.abiFilters += "arm64-v8a"
        }
        create("x86") {
            ndk.abiFilters += "x86"
        }
        create("x86-64") {
            ndk.abiFilters += "x86_64"
        }
        create("mips") {
            ndk.abiFilters += "mips"
        }
        create("mips-64") {
            ndk.abiFilters += "mips64"
        }
        // To include all cpu architectures, leaves abiFilters empty
        create("all")
    }

    android.buildTypes {
        release {
            minifyEnabled = false
            proguardFiles += file('proguard-rules.pro')
        }
    }

}

dependencies {
    compile fileTree(include: ['*.jar'], dir: 'libs')
    compile 'com.android.support:appcompat-v7:23.0.1'
}


```

### JNI开发

在`main`目录下新建`jni`目录

![](http://7q5ccl.com1.z0.glb.clouddn.com/miojni.png)

编写JNI代码

```java

  public native String stringFromJNI();
  public native int intFromJNI();
  
```

生成c文件

![](http://7q5ccl.com1.z0.glb.clouddn.com/miojni2.png)


编写.c文件

```c
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
```

导入库(应该与之前gradle配置的`android.ndk`下的`moduleName`保持一致)

```java

static {
        System.loadLibrary ("jni-test");
    }
```
```java

TextView tv = (TextView) findViewById (R.id.tv);
TextView tv2 = (TextView) findViewById (R.id.tv2);
tv.setText (stringFromJNI ());
tv2.setText ("int from jni:"+intFromJNI ());
```

运行结果:

![](http://7q5ccl.com1.z0.glb.clouddn.com/jni3.png)


最后,如果需要so文件可以在`build-binaries-debug-all-obj`文件下找到.

[demo](https://github.com/mio4kon/ndk-test)

[参考链接](http://tools.android.com/tech-docs/new-build-system/gradle-experimental)

	

