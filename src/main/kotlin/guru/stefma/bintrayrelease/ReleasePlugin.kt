package guru.stefma.bintrayrelease

import com.jfrog.bintray.gradle.BintrayPlugin
import guru.stefma.androidartifacts.ArtifactsExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

class ReleasePlugin : Plugin<Project> {

    private var artifactsPluginApplied = false

    override fun apply(project: Project) = with(project) {
        pluginManager.withPlugin("com.android.library") {
            applyArtifactsPlugin(project)
        }
        pluginManager.withPlugin("java-library") {
            applyArtifactsPlugin(project)
        }
    }

    private fun applyArtifactsPlugin(project: Project) = with(project) {
        if (artifactsPluginApplied) return
        artifactsPluginApplied = true

        pluginManager.apply("guru.stefma.artifacts")

        val artifactsExtension = extensions.getByType(ArtifactsExtension::class.java)
        val publishExtension = extensions.create("publish", PublishExtension::class.java, artifactsExtension)

        BintrayPlugin().apply(project)
        project.afterEvaluate {
            publishExtension.validate()
            BintrayConfiguration(publishExtension).configure(project)
        }
    }

}
