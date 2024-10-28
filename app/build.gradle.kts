plugins {
    kotlinAndroid
    androidApplication
}

val packageName = "ru.myitschool.work"

android {
    namespace = packageName
    compileSdk = Version.Android.Sdk.compile

    defaultConfig {
        applicationId = packageName
        minSdk = Version.Android.Sdk.min
        targetSdk = Version.Android.Sdk.target
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures.viewBinding = true

    compileOptions {
        sourceCompatibility = Version.Kotlin.javaSource
        targetCompatibility = Version.Kotlin.javaSource
    }

    kotlinOptions {
        jvmTarget = Version.Kotlin.jvmTarget
    }
}

dependencies {
    defaultLibrary()
    customLibrary()
}
