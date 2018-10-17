package guru.stefma.bintrayrelease

import com.jfrog.bintray.gradle.BintrayExtension
import guru.stefma.androidartifacts.ArtifactsExtension
import org.gradle.api.Project

class BintrayConfiguration(
        private val artifactsExtension: ArtifactsExtension,
        private val publishExtension: PublishExtension
) {

    fun configure(project: Project) {
        initDefaults()
        deriveDefaultsFromProject(project)

        val propertyFinder = PropertyFinder(project, publishExtension)

        project.extensions.getByType(BintrayExtension::class.java).apply {
            user = propertyFinder.getBintrayUser()
            key = propertyFinder.getBintrayKey()
            publish = publishExtension.autoPublish
            dryRun = propertyFinder.getDryRun()
            override = propertyFinder.getOverride()

            val publication = publishExtension.publications.run {
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
                repo = publishExtension.repoName
                userOrg = publishExtension.userOrg
                name = publishExtension.uploadName
                desc = publishExtension.desc
                websiteUrl = publishExtension.website
                issueTrackerUrl = publishExtension.issueTracker
                vcsUrl = publishExtension.repository

                setLicenses(*publishExtension.licences)
                version.apply {
                    name = project.version.toString()
                    attributes = publishExtension.versionAttributes
                }
            }
        }
    }

    private fun initDefaults() {
        if (publishExtension.uploadName.isEmpty()) {
            publishExtension.uploadName = artifactsExtension.artifactId!!
        }

        if (publishExtension.website.contains("github.com")) {
            if (publishExtension.issueTracker.isEmpty()) {
                publishExtension.issueTracker = "${publishExtension.website}/issues"
            }
            if (publishExtension.repository.isEmpty()) {
                publishExtension.repository = "${publishExtension.website}.git"
            }
        }
    }

    private fun deriveDefaultsFromProject(project: Project) {
        if (publishExtension.versionAttributes.isEmpty()) {
            val gradlePluginPropertyFinder = GradlePluginPropertyFinder(project)
            val bestPluginId = gradlePluginPropertyFinder.findBestGradlePluginId()
            if (bestPluginId != null) {
                publishExtension.versionAttributes = mapOf("gradle-plugin" to "$bestPluginId:$project.group:$publishExtension.artifactId")
                project.logger.info("Using plugin identifier '" + publishExtension.versionAttributes.get("gradle-plugins") + "' for gradle portal.")
            }
        }
    }
}