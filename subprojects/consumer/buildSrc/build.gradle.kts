plugins {
    kotlin("jvm") version "1.3.21"
}

repositories {
    jcenter()
    google()
}

// We have to make sure that we are using the same dependencies as in our top-level project
dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("com.jfrog.bintray.gradle:gradle-bintray-plugin:1.8.4")
    implementation("guru.stefma.androidartifacts:androidartifacts:1.4.0")

    // This dependency is only needed for the `android` sub-project.
    // We need it here because otherwise the `AndroidArtifacts` plugin
    // doesn't have the AGP in the same classpath and therefore it will
    // crash with a `NoClassDefFoundException`
    implementation("com.android.tools.build:gradle:3.2.1")
}
kotlin.sourceSets["main"].kotlin.srcDir("../../../src/main/kotlin")