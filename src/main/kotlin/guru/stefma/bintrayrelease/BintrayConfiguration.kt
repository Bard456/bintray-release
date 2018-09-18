package guru.stefma.bintrayrelease

import com.jfrog.bintray.gradle.BintrayExtension
import org.gradle.api.Project

class BintrayConfiguration(
        private val extension: PublishExtension
) {

    fun configure(project: Project) {
        initDefaults()
        deriveDefaultsFromProject(project)

        val propertyFinder = PropertyFinder(project, extension)

        project.extensions.getByType(BintrayExtension::class.java).apply {
            user = propertyFinder.getBintrayUser()
            key = propertyFinder.getBintrayKey()
            publish = extension.autoPublish
            dryRun = propertyFinder.getDryRun()
            override = propertyFinder.getOverride()

            val publication = extension.publications.run {
                if (isNotEmpty()) {
                    this
                } else {
                    if (project.pluginManager.hasPlugin("com.android.library")) {
                        arrayOf("releaseAar")
                    } else {
                        arrayOf("maven")
                    }
                }
            }
            setPublications(*publication)

            pkg.apply {
                repo = extension.repoName
                userOrg = extension.userOrg
                name = extension.uploadName
                desc = extension.desc
                websiteUrl = extension.website
                issueTrackerUrl = extension.issueTracker
                vcsUrl = extension.repository

                setLicenses(*extension.licences)
                version.apply {
                    name = project.version.toString()
                    attributes = extension.versionAttributes
                }
            }
        }
    }

    private fun initDefaults() {
        if (extension.uploadName.isEmpty()) {
            extension.uploadName = extension.artifactId!!
        }

        if (extension.website.contains("github.com")) {
            if (extension.issueTracker.isEmpty()) {
                extension.issueTracker = "${extension.website}/issues"
            }
            if (extension.repository.isEmpty()) {
                extension.repository = "${extension.website}.git"
            }
        }
    }

    private fun deriveDefaultsFromProject(project: Project) {
        if (extension.versionAttributes.isEmpty()) {
            val gradlePluginPropertyFinder = GradlePluginPropertyFinder(project)
            val bestPluginId = gradlePluginPropertyFinder.findBestGradlePluginId()
            if (bestPluginId != null) {
                extension.versionAttributes = mapOf("gradle-plugin" to "$bestPluginId:$project.group:$extension.artifactId")
                project.logger.info("Using plugin identifier '" + extension.versionAttributes.get("gradle-plugins") + "' for gradle portal.")
            }
        }
    }
}