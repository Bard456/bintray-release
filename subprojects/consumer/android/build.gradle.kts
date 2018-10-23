import com.android.build.gradle.LibraryExtension
import guru.stefma.androidartifacts.ArtifactsExtension
import guru.stefma.bintrayrelease.PublishExtension
import guru.stefma.bintrayrelease.BintrayReleasePlugin

buildscript {
    repositories {
        google()
        jcenter()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:3.0.1")
    }
}

apply<BintrayReleasePlugin>()
apply(plugin = "com.android.library")

configure<LibraryExtension> {
    compileSdkVersion(27)
    defaultConfig {
        minSdkVersion(21)
        targetSdkVersion(27)
        versionCode = 1
        versionName = "1.0.0"
        //archivesBaseName = "$archivesBaseName-$versionName" // This is not working
    }
    lintOptions {
        isAbortOnError = false
    }
}

repositories {
    google()
    jcenter()
}

dependencies {
    "implementation"("com.android.support:appcompat-v7:27.0.2")
}

group = "guru.stefma.bintrayrelease.consumer"
version = "0.0.1"
configure<ArtifactsExtension> {
    artifactId = "android-sample"
}

configure<PublishExtension> {
    userOrg = "stefma"
    desc = "Just a simple android lib sample"
    website = "https://github.com/stefma/bintray-release"
}