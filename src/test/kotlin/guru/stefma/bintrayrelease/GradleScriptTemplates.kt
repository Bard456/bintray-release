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
            plugins {
                id 'guru.stefma.bintrayrelease'
                id 'com.android.library'
            }

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
                desc = 'description'
            }
               """
    }

val androidSettingsScript = androidSettingsScript()

fun androidSettingsScript(agpVersion: String = "3.2.1"): String =
        """
            pluginManagement {
                repositories {
                    mavenLocal()
                    google()
                    jcenter()
                }
                resolutionStrategy {
                    eachPlugin {
                        if (requested.id.id.startsWith("com.android")) {
                            useModule("com.android.tools.build:gradle:$agpVersion")
                        }
                        if (requested.id.id.startsWith("guru.stefma")) {
                            useModule("guru.stefma.bintrayrelease:bintrayrelease:${System.getProperty("pluginVersion")}")
                        }
                    }
                }
            }
        """