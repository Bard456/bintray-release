package guru.stefma.bintrayrelease

import com.jfrog.bintray.gradle.BintrayPlugin
import guru.stefma.androidartifacts.AndroidArtifactsPlugin
import guru.stefma.androidartifacts.ArtifactsExtension
import guru.stefma.androidartifacts.JavaArtifactsPlugin
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.logging.LogLevel

class ReleasePlugin : Plugin<Project> {

    override fun apply(project: Project) {
        when {
            project.plugins.hasPlugin("com.android.library") -> {
                // Apply the AndroidArtifactsPlugin on Android projects
                project.plugins.apply(AndroidArtifactsPlugin::class.java)
            }
            project.plugins.hasPlugin("java-library") -> {
                // ...and the JavaArtifactsPlugin on pure Java projects
                project.plugins.apply(JavaArtifactsPlugin::class.java)
            }
            else -> {
                project.logger.log(
                        LogLevel.INFO,
                        "You have to apply either the `com.android.library` plugin or the `java-library` plugin...")
                return
            }
        }
        val artifactsExtension = project.extensions.getByType(ArtifactsExtension::class.java)
        val publishExtension = project.extensions.create("publish", PublishExtension::class.java, artifactsExtension)

        BintrayPlugin().apply(project)
        project.afterEvaluate {
            publishExtension.validate()
            BintrayConfiguration(publishExtension).configure(project)
        }
    }

}
