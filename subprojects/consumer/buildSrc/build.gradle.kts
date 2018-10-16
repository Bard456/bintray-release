plugins {
    kotlin("jvm") version "1.2.71"
}

repositories {
    jcenter()
    google()
}

// We have to make sure that we are using the same dependencies as in our top-level project
dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("com.jfrog.bintray.gradle:gradle-bintray-plugin:1.8.4")
    implementation("guru.stefma.androidartifacts:androidartifacts:1.2.0")
}
kotlin.sourceSets["main"].kotlin.srcDir("../../../src/main/kotlin")