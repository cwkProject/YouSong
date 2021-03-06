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

-keepattributes Signature

# 接口数据模型
-keep class com.yousong.yousong.model.server.** {*;}

# gson
-dontwarn com.google.**
-keep class com.google.gson.** {*;}

# databinding
-dontwarn android.databinding.**
-keep class android.databinding.** { *; }
-keep class om.yousong.yousong.architecture.databinding.BindingTypeConverts {*;}

# 微信
-keep class com.tencent.mm.opensdk.** { *; }
-keep class com.tencent.mm.sdk.** { *; }
-keep class com.tencent.wxop.** { *; }

# Glide
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.module.AppGlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}

# 地区3级联动选择器
-keep class com.lljjcoder.**{*;}

-dontwarn demo.**
-keep class demo.**{*;}
-dontwarn net.sourceforge.pinyin4j.**
-keep class net.sourceforge.pinyin4j.**{*;}