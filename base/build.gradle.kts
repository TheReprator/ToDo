plugins {
    alias(libs.plugins.kotlin.jvm)
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17

    sourceSets {
        map { it.java.srcDirs("src/${it.name}/kotlin") }
    }
}

dependencies {
    api(libs.kotlinx.coroutines.core)
    api(libs.timber)
}
