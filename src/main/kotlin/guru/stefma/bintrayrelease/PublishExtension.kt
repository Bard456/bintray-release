package guru.stefma.bintrayrelease

import com.jfrog.bintray.gradle.BintrayExtension

/**
 * A gradle extension which will be used to configure the plugin.
 *
 * Most of the properties will be used to setup the [BintrayExtension] in the [BintrayConfiguration].
 * [See also this documentation](https://github.com/bintray/gradle-bintray-plugin#plugin-dsl)
 *
 * Some properties are mandatory and have to be validated before any action on it happen.
 * The other ones are all optional or provide a default value.
 *
 * Optional doesn't mean they aren't needed but that they will handled correctly by the plugin!
 */
open class PublishExtension {

    var repoName = "maven"
    var userOrg: String? = null

    var versionAttributes = emptyMap<String, String>()

    var licences = arrayOf("Apache-2.0")

    var uploadName = ""

    var desc: String? = null

    var website = ""
    var issueTracker = ""
    var repository = ""
    var autoPublish = true

    var bintrayUser = ""
    var bintrayKey = ""
    var dryRun = true
    var override = false

    var publications = emptyArray<String>()

    /**
     * Validate all mandatory properties for this extension.
     *
     * Will throw a Exception if not setup correctly.
     */
    internal fun validate() {
        var extensionError = ""
        if (userOrg == null) {
            extensionError += "Missing userOrg. "
        }
        if (desc == null) {
            extensionError += "Missing desc. "
        }

        if (extensionError.isNotEmpty()) {
            val prefix = "Have you created the publish closure? "
            throw IllegalStateException(prefix + extensionError)
        }
    }

}
