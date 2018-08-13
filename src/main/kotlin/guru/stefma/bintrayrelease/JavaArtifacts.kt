package guru.stefma.bintrayrelease

import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.component.SoftwareComponent
import org.gradle.api.plugins.JavaPluginConvention
import org.gradle.api.tasks.javadoc.Javadoc
import org.gradle.jvm.tasks.Jar

class JavaArtifacts {

    fun all(publicationName: String, project: Project): Array<Task> {
        return arrayOf(sourcesJar(publicationName, project), javadocJar(publicationName, project))
    }

    fun sourcesJar(publicationName: String, project: Project): Task {
        return project.tasks.create(publicationName + "SourcesJar", Jar::class.java) {
            it.classifier = "sources"
            it.from(project.convention.getPlugin(JavaPluginConvention::class.java).sourceSets.getByName("main").allSource)
        }
    }

    fun javadocJar(publicationName: String, project: Project): Task {
        return project.tasks.create(publicationName + "JavadocJar", Jar::class.java) {
            it.classifier = "javadoc"
            it.from(project.tasks.getByName("javadoc").run {
                (this as Javadoc).destinationDir
            })
        }
    }

    fun from(project: Project): SoftwareComponent? {
        return project.components.getByName("java")
    }
}