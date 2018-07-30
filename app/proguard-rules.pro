# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

## dagger

# dagger2 https://github.com/google/dagger/issues/645
-dontwarn com.google.errorprone.annotations.*

## data

# moshi
# okhttp3
-dontwarn okio.**
-dontwarn javax.annotation.**

# fix for kotlin 1.2 https://github.com/square/moshi/issues/402
-keep public class kotlin.reflect.jvm.internal.impl.builtins.* { public *; }
-dontwarn kotlin.reflect.jvm.internal.**
-keep class kotlin.reflect.jvm.internal.** { *; }

-keep class kotlin.** {
    public protected *;
}

-keep enum ** {
    public protected *;
}

-keepattributes InnerClasses,EnclosingMethod

# retrofit2 http://square.github.io/retrofit/
-dontnote retrofit2.Platform
-dontwarn retrofit2.Platform$Java8
-keepattributes Signature
-keepattributes Exceptions

# okhttp3 https://github.com/square/okhttp/issues/2230
-dontwarn okhttp3.**

# Simple-Xml Proguard Config
# NOTE: You should also include the Android Proguard config found with the build tools:
# $ANDROID_HOME/tools/proguard/proguard-android.txt

# Keep public classes and methods.
-dontwarn com.bea.xml.stream.**
-dontwarn org.simpleframework.xml.stream.**
-keep class org.simpleframework.xml.**{ *; }
-keepclassmembers,allowobfuscation class * {
    @org.simpleframework.xml.* <fields>;
    @org.simpleframework.xml.* <init>(...);
}

# glide https://github.com/krschultz/android-proguard-snippets/blob/master/libraries/proguard-glide.pro
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep class com.bumptech.glide.integration.okhttp3.OkHttpGlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
    **[] $VALUES;
    public *;
}