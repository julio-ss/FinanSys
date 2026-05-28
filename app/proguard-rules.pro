# Retrofit
-keep class retrofit2.** { *; }
-keepattributes Signature
-keepattributes *Annotation*

# OkHttp3
-keep class okhttp3.** { *; }

# Gson
-keep class com.google.gson.** { *; }
-keepattributes Signature

# Firebase
-keep class com.google.firebase.** { *; }
-keep class com.google.android.gms.** { *; }
-keepattributes Exceptions

# Room
-keep class androidx.room.** { *; }
-keepattributes Signature

# Coroutines
-keep class kotlin.coroutines.** { *; }

# Hilt
-keep class dagger.** { *; }
-keep class javax.inject.** { *; }

# Models (Kotlin Serialization)
-keep @kotlinx.serialization.Serializable class * { *; }

# Timber
-keep class timber.log.Timber* { *; }

# Keep line numbers for debugging
-keepattributes SourceFile,LineNumberTable
-renamesourcefileattribute SourceFile

# Compose
-keep class androidx.compose.** { *; }
