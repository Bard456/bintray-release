package guru.stefma.bintrayrelease

import org.gradle.api.Project

class PropertyFinder(
        private val project: Project,
        private val extension: PublishExtension
) {

    fun getBintrayUser(): String {
        return getString("bintrayUser", extension.bintrayUser)
    }

    fun getBintrayKey(): String {
        return getString("bintrayKey", extension.bintrayKey)
    }

    fun getDryRun(): Boolean {
        return getBoolean("dryRun", extension.dryRun)
    }

    fun getOverride(): Boolean {
        return getBoolean("override", extension.override)
    }

    private fun getString(propertyName: String, defaultValue: String): String {
        return project.properties[propertyName] as? String ?: defaultValue
    }

    private fun getBoolean(propertyName: String, defaultValue: Boolean): Boolean {
        val value = project.properties[propertyName] as? String ?: return defaultValue
        return value.toBoolean()
    }

}