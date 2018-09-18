package guru.stefma.bintrayrelease

import org.gradle.api.Project
import java.io.File

class GradlePluginPropertyFinder(
        private val project: Project
) {
    private val FILE_EXTENSION_PROPERTIES = ".properties"

    fun findBestGradlePluginId(): String? {
        val pluginFiles = project.fileTree("src/main/resources/META-INF/gradle-plugins")
        if (pluginFiles.isEmpty) {
            return null
        }
        val filteredPluginFiles = pluginFiles.filter {
            it.name.endsWith(FILE_EXTENSION_PROPERTIES) && isNamespacedPropertyFile(it)
        }
        if (filteredPluginFiles.isEmpty) {
            return null
        }
        val bestPluginFile = filteredPluginFiles.first()
        return removePropertyFileExtension(bestPluginFile)
    }

    private fun isNamespacedPropertyFile(file: File): Boolean {
        return file.name.substring(0, file.name.length - FILE_EXTENSION_PROPERTIES.length).contains('.')
    }

    private fun removePropertyFileExtension(bestPluginFile: File): String {
        return bestPluginFile.name.substring(0, bestPluginFile.name.length - FILE_EXTENSION_PROPERTIES.length)
    }
}
