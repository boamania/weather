plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.example.weather"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.weather"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(libs.androidx.foundation.android)
    implementation(libs.androidx.foundation.android)
    implementation(libs.androidx.foundation.android)
    implementation(libs.androidx.foundation.android)
    implementation(libs.androidx.foundation.android)
    implementation(libs.androidx.foundation.android)
    implementation(libs.androidx.foundation.android)
    implementation(libs.androidx.foundation.android)
    dependencies {
        // Core dependencies
        implementation(libs.androidx.core.ktx.v1120)
        implementation(libs.androidx.lifecycle.runtime.ktx.v262)
        implementation(libs.androidx.activity.compose.v180)

        // Compose dependencies
        implementation(libs.ui)
        implementation(libs.material3)
        implementation(libs.androidx.lifecycle.viewmodel.compose)
        implementation(libs.androidx.ui.v151)
        implementation(libs.androidx.ui.text)

        // Retrofit for API calls
        implementation(libs.retrofit)
        implementation(libs.converter.gson)
        implementation(libs.androidx.datastore.preferences)

        // Testing dependencies
        testImplementation(libs.junit)
        testImplementation(libs.mockito.kotlin)
        testImplementation(libs.kotlinx.coroutines.test)

        // Add the missing InstantTaskExecutorRule dependency
        testImplementation(libs.androidx.core.testing)

        androidTestImplementation(libs.androidx.junit.v115)
        androidTestImplementation(libs.androidx.espresso.core.v351)

        debugImplementation(libs.ui.tooling)
        debugImplementation(libs.ui.test.manifest)
    }

}