plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("androidx.navigation.safeargs.kotlin")
    id("kotlin-kapt")
}

android {
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.andersen.rick_and_morty"
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"

        val googleMapKey:String by project
        manifestPlaceholders["googleMapKey"]  = "AIzaSyA_DKknzyYk6MetKXUi21x9oX9XUkOGL7A"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
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
        viewBinding = true
    }
}

dependencies {

    implementation(project(":domain"))
    implementation(project(":data"))

    implementation ("pl.droidsonroids.gif:android-gif-drawable:1.2.17")


    implementation(libs.bundles.android.google.maps)

    implementation(libs.kotlinx.serialization)

    implementation(libs.androidx.swiperefreshlayout)

    implementation(libs.koin.android)

    implementation(libs.bundles.androidx.lifecycle)

    kapt(libs.androidx.room.compiler)
    implementation(libs.bundles.androidx.room)

    implementation(libs.bundles.androidx.retrofit)

    implementation(libs.coil)

    implementation(libs.bundles.androidx.navigation)

    implementation(libs.androidx.core)
    implementation(libs.androidx.appcompat)
    implementation(libs.android.material)

    implementation(libs.androidx.constraintlayout)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext)
    androidTestImplementation(libs.androidx.test.espresso)
}