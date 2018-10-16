package guru.stefma.bintrayrelease

import guru.stefma.androidartifacts.ArtifactsExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.PluginManager

/**
 * This [Plugin] will just streamline the process of setup the **com.jfrog.bintray** plugin for you.
 * It will also apply the **guru.stefma.artifacts** plugin which will generates the **publication** for us.
 */
class BintrayReleasePlugin : Plugin<Project> {

    override fun apply(project: Project) = with(project.pluginManager) {
        apply("guru.stefma.artifacts")

        withPlugins("com.android.library", "java-library", "org.jetbrains.kotlin.jvm", "kotlin") {
            setupPlugin(project)
        }
    }

    private fun setupPlugin(project: Project) = with(project) {
        val artifactsExtension = extensions.getByType(ArtifactsExtension::class.java)
        val publishExtension = extensions.create("publish", PublishExtension::class.java)

        pluginManager.apply("com.jfrog.bintray")
        afterEvaluate {
            publishExtension.validate()
            BintrayConfiguration(artifactsExtension, publishExtension).configure(project)
        }
    }

}

/**
 * Loops over each of the given [pluginIds] by calling [PluginManager.withPlugin]
 * and calls the given [block] when one of the **pluginId** is applied...
 */
private fun PluginManager.withPlugins(vararg pluginIds: String, block: () -> Unit) {
    var applied = false

    pluginIds.forEach { pluginId ->
        withPlugin(pluginId) {
            if (applied) return@withPlugin
            applied = true
            block()
        }
    }
}
