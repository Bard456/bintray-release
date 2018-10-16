package guru.stefma.bintrayrelease

val javaBuildScript: String
    get() {
        return """
            plugins {
                id 'guru.stefma.bintrayrelease'
                id 'java-library'
            }

            repositories {
                jcenter()
            }

            dependencies {
                implementation "junit:junit:4.12"
            }

            group = "guru.stefma"
            version = "1.0"
            javaArtifact {
                artifactId = "test"
            }
            publish {
                userOrg = 'stefma'
                desc = 'description'
            }
               """
    }

val androidBuildScript: String
    get() {
        return """
            buildscript {
                repositories {
                    jcenter()
                    google()
                }
                dependencies {
                    classpath 'com.android.tools.build:gradle:3.0.0'
                }
            }

            plugins {
                id 'guru.stefma.bintrayrelease' apply false
            }

            apply plugin: "guru.stefma.bintrayrelease"
            apply plugin: "com.android.library"

            android {
                compileSdkVersion 26

                defaultConfig {
                    minSdkVersion 16
                    versionCode 1
                    versionName "0.0.1"
                }

                lintOptions {
                   tasks.lint.enabled = false
                }
            }

            repositories {
                jcenter()
                google()
            }

            dependencies {
                implementation "junit:junit:4.12"
            }

            group = "guru.stefma"
            version = "1.0"
            androidArtifact {
                artifactId = "test"
            }
            publish {
                userOrg = 'stefma'
                artifactId = 'test'
                desc = 'description'
            }
               """
    }