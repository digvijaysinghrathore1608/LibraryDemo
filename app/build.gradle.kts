import groovy.json.JsonSlurper
import java.net.HttpURLConnection
import java.net.URL

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.apna.myapplication"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.apna.myapplication"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    implementation(libs.androidx.appcompat)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

//    implementation(project(":mylibrary"))
    implementation ("com.github.digvijaysinghrathore1608:LibraryDemo:1.0.1")
}

tasks.register("checkForLibraryUpdate") {
    doLast {
        val apiUrl = "https://api.github.com/repos/digvijaysinghrathore1608/LibraryDemo/releases"
        val latestVersion: String

        try {
            val connection = URL(apiUrl).openConnection() as HttpURLConnection
            connection.requestMethod = "GET"
            connection.connect()

            val response = connection.inputStream.bufferedReader().readText()
            val releases = JsonSlurper().parseText(response) as List<Map<String, Any>>

            latestVersion = if (releases.isNotEmpty()) {
                releases[0]["tag_name"] as String // Get the latest version
            } else {
                throw Exception("No releases found")
            }

            // Update build.gradle.kts automatically
            val buildGradleFile = file("${projectDir}/build.gradle.kts")
            val lines = buildGradleFile.readLines().toMutableList()

            for (i in lines.indices) {
                if (lines[i].contains("implementation(\"com.github.digvijaysinghrathore1608:LibraryDemo:")) {
                    lines[i] = "    implementation(\"com.github.digvijaysinghrathore1608:LibraryDemo:$latestVersion\")"
                    break
                }
            }

            buildGradleFile.writeText(lines.joinToString("\n"))
            println("Updated to version: $latestVersion")
        } catch (e: Exception) {
            println("Error checking for updates: ${e.message}")
        }
    }
}


