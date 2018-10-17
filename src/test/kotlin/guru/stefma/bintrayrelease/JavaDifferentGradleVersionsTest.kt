package guru.stefma.bintrayrelease

import org.assertj.core.api.Assertions.assertThat
import org.gradle.testkit.runner.GradleRunner
import org.gradle.testkit.runner.TaskOutcome
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import java.io.File

class JavaDifferentGradleVersionsTest {

    private val projectDir = createTempDir()

    @AfterEach
    fun tearDown() {
        projectDir.deleteRecursively()
    }

    @ParameterizedTest(
            name = "test task bintrayUpload with different Gradle version {arguments} should succeed"
    )
    @ValueSource(strings = ["4.0", "4.1", "4.2", "4.3", "4.4", "4.5", "4.5.1", "4.6", "4.7", "4.8", "4.8.1", "4.9", "4.10", "4.10.1", "4.10.2"])
    fun `test task bintrayUpload for java projects with different Gradle versions should succeed`(
            gradleVersion: String
    ) {
        File(projectDir, "build.gradle").apply {
            writeText(javaBuildScript)
        }

        val runner = GradleRunner.create()
                .withProjectDir(projectDir)
                .withArguments("build", "bintrayUpload", "-PbintrayKey=key", "-PbintrayUser=user")
                .withPluginClasspath()
                .withGradleVersion(gradleVersion)

        assertThat(runner.build().task(":bintrayUpload")!!.outcome).isEqualTo(TaskOutcome.SUCCESS)
    }

}