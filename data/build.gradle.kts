plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
    id("kotlinx-serialization")
}

android {
    compileSdk = 31
    buildToolsVersion = "30.0.3"

    defaultConfig {
        minSdk = 21
        targetSdk = 31
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        javaCompileOptions {
            annotationProcessorOptions {
                arguments += mapOf(
                    "room.schemaLocation" to "$projectDir/schemas",
                    "room.incremental" to "true",
                    "room.expandProjection" to "true"
                )
            }
        }
    }

    buildTypes {
        getByName("release") {
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

    testOptions {
        unitTests.isReturnDefaultValues = true
        unitTests.isIncludeAndroidResources = true
    }
}

dependencies {

    implementation(project(":domain"))

    implementation("androidx.core:core-ktx:1.6.0")
    implementation("androidx.appcompat:appcompat:1.3.1")
    implementation("io.insert-koin:koin-core:${Versions.koinVersion}")

    implementation("androidx.room:room-ktx:${Versions.roomVersion}")
    kapt("androidx.room:room-compiler:${Versions.roomVersion}")

    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation(platform("com.squareup.okhttp3:okhttp-bom:4.9.0"))
    implementation("com.squareup.okhttp3:okhttp")
    implementation("com.squareup.okhttp3:logging-interceptor")
    implementation("com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:0.8.0")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.2.2")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutinesVersion}")

    testImplementation("junit:junit:4.13.2")
    testImplementation("androidx.room:room-testing:${Versions.roomVersion}")
    testImplementation("com.squareup.okhttp3:mockwebserver")
    testImplementation("org.robolectric:robolectric:4.6.1")
    testImplementation("io.insert-koin:koin-test:${Versions.koinVersion}")
    testImplementation("io.insert-koin:koin-test-junit4:${Versions.koinVersion}")
    testImplementation("io.mockk:mockk:1.12.0")

    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
}