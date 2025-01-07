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
# Giữ lại các annotations được dùng cho manifest
-keepattributes *Annotation*
-keepattributes *Annotations

# Giữ lại các class, interface được dùng trong manifest (và cả con của nó)
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public interface * extends android.app.Application
-keep public class * extends androidx.**
-keep public class * implements androidx.**

# Giữ lại các class được dùng trong intent filters
-keep class * {
    <init>(android.content.Context);
    void *(android.content.Intent);
}

#Giữ lại các class public
-keep public class *


# Giữ lại các class và interface liên quan đến các thư viện bên ngoài
-keep class com.google.** {*;}
-keep class com.squareup.** {*;}
-keep class okhttp3.** {*;}
-keep class okio.** {*;}
-keep class kotlin.** {*;}
-keep class kotlinx.** {*;}
 # Không hiển thị các warning
-dontwarn okio.**
-dontwarn kotlin.io.**
-dontwarn kotlinx.coroutines.**
# Giữ lại các tài nguyên được tham chiếu thông qua reflection
-keepclassmembers class * {
    @android.webkit.JavascriptInterface <methods>;
}

# Giữ lại các tài nguyên từ tên gói
-keep class com.example.app.R$* { *; }
