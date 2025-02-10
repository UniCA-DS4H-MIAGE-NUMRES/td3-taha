import org.jetbrains.compose.ExperimentalComposeLibrary
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.room)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlinxSerialization)

}

kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }
    
    jvm("desktop")
    
    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        moduleName = "composeApp"
        browser {
            val rootDirPath = project.rootDir.path
            val projectDirPath = project.projectDir.path
            commonWebpackConfig {
                outputFileName = "composeApp.js"
                devServer = (devServer ?: KotlinWebpackConfig.DevServer()).apply {
                    static = (static ?: mutableListOf()).apply {
                        // Serve sources to debug inside browser
                        add(rootDirPath)
                        add(projectDirPath)
                    }
                }
            }
        }
        binaries.executable()
    }
    
    sourceSets {

        val commonMain by getting {
            dependencies {
                implementation("io.ktor:ktor-client-core:3.0.3")

            }
        }

        val androidMain by getting {
            dependencies {
                implementation(compose.preview)
                implementation(libs.androidx.activity.compose)

                implementation(libs.androidx.lifecycle.viewmodel)
                implementation(libs.androidx.lifecycle.runtime.compose)

                implementation(libs.ktor.client.android)

                implementation(libs.ktor.client.core)
                implementation(libs.ktor.client.content.negotiation)
                implementation(libs.ktor.serialization.kotlinx.json)
                implementation(libs.koin.core)
                implementation(libs.room.runtime)
                implementation(libs.gson)

                implementation("app.cash.sqldelight:android-driver:2.0.0")
            }
        }

        val desktopMain by getting {
            dependencies {
                implementation("io.ktor:ktor-client-cio:3.0.3")
                implementation("app.cash.sqldelight:sqlite-driver:2.0.0")

            }
        }
        
        androidMain.dependencies {
            val room_version = "2.6.1"
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)
            implementation("androidx.room:room-runtime:$room_version")
            implementation("androidx.room:room-ktx:$room_version")

        }
        commonMain.dependencies {
            val voyagerVersion = "1.1.0-beta02"

            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.ui)
            @OptIn(ExperimentalComposeLibrary::class)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtime.compose)
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.kotlinx.serialization.json)

            implementation(compose.components.resources)
            implementation("cafe.adriel.voyager:voyager-navigator:$voyagerVersion")
        }

        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutines.swing)

            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.serialization.kotlinx.json)
            implementation(libs.koin.core)

            implementation("org.xerial:sqlite-jdbc:3.43.2.0")
        }

        wasmJsMain.dependencies {
            implementation(compose.components.resources)
            configurations["wasmJsMainImplementation"].exclude(group = "app.cash.sqldelight")
            configurations["wasmJsMainImplementation"].exclude(group = "org.jetbrains.compose.ui", module = "ui-tooling-preview")
            implementation("androidx.navigation:navigation-compose:2.4.0-alpha10") {
                exclude(group = "org.jetbrains.kotlinx", module = "kotlinx-coroutines-android")
            }
            implementation("org.jetbrains.skiko:skiko-wasm-js:0.7.36")
        }
    }
}

android {
    namespace = "fr.unice.miage.tp3.pizzap"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "fr.unice.miage.tp3.pizzap"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    implementation(libs.androidx.navigation.runtime.ktx)
    implementation(libs.androidx.activity.ktx)
    debugImplementation(compose.uiTooling)
    implementation(libs.androidx.ui.android)
    implementation(libs.androidx.navigation.runtime.android)
    add("kspAndroid", libs.room.compiler)
    add("kspDesktop", libs.room.compiler)
    implementation("androidx.navigation:navigation-compose:2.8.5")
    implementation("androidx.activity:activity-compose:1.8.2")
}

compose.desktop {
    application {
        mainClass = "fr.unice.miage.tp3.pizzap.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "fr.unice.miage.tp3.pizzap"
            packageVersion = "1.0.0"
        }
    }
}

compose.experimental {
    web.application {}
}

room {
    schemaDirectory("$projectDir/schemas")
}
