package guru.stefma.bintrayrelease

import org.assertj.core.api.Assertions.assertThat
import org.gradle.testkit.runner.GradleRunner
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import java.io.File

class InvalidExtensionSetupTest {

    private val projectDir = createTempDir()

    @AfterEach
    fun tearDown() {
        projectDir.deleteRecursively()
    }

    @Test
    fun `invalid project should fail with description`() {
        File(projectDir, "build.gradle").apply {
            writeText("""
            plugins {
                id 'guru.stefma.bintrayrelease'
                id 'java-library'
            }

            group = "guru.stefma"
            version = "1.0"
            javaArtifact {
                artifactId = "test"
            }
            publish {
                userOrg = 'stefma'
                // desc = 'description'
            }
               """)
        }

        val result = GradleRunner.create()
                .withProjectDir(projectDir)
                .withArguments("build")
                .withPluginClasspath()
                .buildAndFail()

        assertThat(result.output).contains("Have you created the publish closure? Missing desc. ")
    }
}