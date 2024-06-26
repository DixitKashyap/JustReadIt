plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    id("com.google.gms.google-services")
    id("kotlin-kapt")
    id ("com.google.dagger.hilt.android")
}

android {
    namespace = "com.dixitkumar.justreadit"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.dixitkumar.justreadit"
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

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.play.services.auth)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    //Firebase Dependency
    implementation(libs.firebase.auth)
    implementation(libs.firebase.firestore.ktx)

    //View Model Dependency
    implementation (libs.androidx.lifecycle.viewmodel.compose)

    //Dagger Hilt Dependency
    implementation ("com.google.dagger:hilt-android:2.51")
    kapt("com.google.dagger:hilt-compiler:2.51")

    //Coroutines
    implementation (libs.kotlinx.coroutines.android)

    // Navigation Controller
    implementation ("androidx.navigation:navigation-compose:2.7.7")

    //Material Icon
    implementation("androidx.compose.material:material-icons-extended:1.6.2")

    //Coil Dependency
   implementation("io.coil-kt:coil-compose:2.6.0")

     //Cloud Storeage
    implementation("com.google.firebase:firebase-storage:20.3.0")
    //Retrofit dependency
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation  ("com.squareup.retrofit2:converter-gson:2.9.0")

    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")

//

    
}
kapt{
    correctErrorTypes = true
}
