package guru.stefma.bintrayrelease

import org.assertj.core.api.Assertions.assertThat
import org.gradle.testkit.runner.GradleRunner
import org.gradle.testkit.runner.TaskOutcome
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import java.io.File

class PublishExtensionCompatTest {

    private val projectDir = createTempDir()

    @AfterEach
    fun tearDown() {
        projectDir.deleteRecursively()
    }

    @Test
    fun `test artifactId in the publish extension in a compatibility way`() {
        File(projectDir, "build.gradle").apply {
            writeText("""
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
                publish {
                    artifactId = "test"
                    userOrg = 'stefma'
                    desc = 'description'
                }
            """.trimIndent())
        }

        File(projectDir, "/src/main/AndroidManifest.xml").apply {
            parentFile.mkdirs()
            writeText("<manifest package=\"guru.stefma.bintrayrelease.test\"/>")
        }

        val runner = GradleRunner.create()
                .withProjectDir(projectDir)
                .withArguments("build", "bintrayUpload", "-PbintrayKey=key", "-PbintrayUser=user")
                .withPluginClasspath()

        assertThat(runner.build().task(":bintrayUpload")!!.outcome).isEqualTo(TaskOutcome.SUCCESS)
    }

}