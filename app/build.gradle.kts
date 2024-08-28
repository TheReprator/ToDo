plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.compose)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
    alias(libs.plugins.kotlin.serialization)
}

android {
    val appId = "app.root.container"

    namespace = appId
    compileSdk = libs.versions.sdk.compile.get().toInt()

    defaultConfig {
        applicationId = appId
        minSdk = libs.versions.sdk.min.get().toInt()
        targetSdk = libs.versions.sdk.target.get().toInt()
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables.useSupportLibrary = true

        resourceConfigurations.add("en")
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

composeCompiler {
    val composeReports = layout.buildDirectory.map { it.dir("reports").dir("compose") }
    reportsDestination.set(composeReports)
    metricsDestination.set(composeReports)

    reportsDestination = layout.buildDirectory.dir("compose_compiler")
    stabilityConfigurationFile =
        rootProject.layout.projectDirectory.file("config/compose_compiler_config.conf")
}

dependencies {
    implementation(projects.base)
    implementation(projects.baseAndroid)
    implementation(projects.appDb)
    implementation(projects.appModules.toDoList)
    implementation(projects.appModules.createToDo)
    implementation(projects.appModules.searchToDo)

    implementation(libs.androidx.core.splashscreen)

    implementation(libs.androidx.activity.compose)

    implementation(libs.androidx.compose.material3.adaptive.layout)
    implementation(libs.androidx.compose.material3.adaptive.navigation.suite)

    implementation(libs.androidx.compose.ui.tooling.preview)
    debugImplementation(libs.androidx.compose.ui.tooling)

    implementation(libs.androidx.lifecycle.process)

    implementation(libs.kotlinx.datetime)

    implementation(libs.kotlinx.serialization.json)
    implementation(libs.androidx.navigation.compose)

    implementation(libs.androidx.hilt.navigation.compose)

    ksp(libs.hilt.compiler)
    implementation(libs.hilt.android)
}