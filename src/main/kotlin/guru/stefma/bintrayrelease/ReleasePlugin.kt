package guru.stefma.bintrayrelease

import com.jfrog.bintray.gradle.BintrayPlugin
import guru.stefma.androidartifacts.AndroidArtifactsExtension
import guru.stefma.androidartifacts.AndroidArtifactsPlugin
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication

class ReleasePlugin : Plugin<Project> {

    override fun apply(project: Project) {
        var extension: PublishExtension? = null
        if (project.plugins.hasPlugin("com.android.library")) {
            project.plugins.apply(AndroidArtifactsPlugin::class.java)
            val artifactsExtension = project.extensions.getByType(AndroidArtifactsExtension::class.java)
            extension = project.extensions.create("publish", AndroidPublishExtension::class.java, artifactsExtension)
        }
        if (project.plugins.hasPlugin("java-library")) {
            extension = project.extensions.create("publish", PublishExtension::class.java)
        }

        BintrayPlugin().apply(project)
        project.afterEvaluate {
            extension!!.validate()
            if (project.plugins.hasPlugin("java-library")) {
                attachJavaArtifacts(extension, project)
            }
            BintrayConfiguration(extension).configure(project)
        }
    }

    fun attachJavaArtifacts(extension: PublishExtension, project: Project) {
        project.pluginManager.apply("maven-publish")
        addArtifact(project, "maven", extension, JavaArtifacts())
    }

    fun addArtifact(project: Project, name: String, extension: PublishExtension, artifacts: JavaArtifacts) {
        project.extensions.getByType(PublishingExtension::class.java).publications.create(name, MavenPublication::class.java) {
            it.groupId = project.group.toString()
            it.artifactId = extension.artifactId
            it.version = project.version.toString()

            /* This can be removed later anyway..
            artifacts.all(it.name, project).each {
                delegate.artifact it
            }
            */
            it.from(artifacts.from(project))
        }
    }
}
