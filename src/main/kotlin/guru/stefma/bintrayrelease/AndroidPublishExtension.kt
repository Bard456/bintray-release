package guru.stefma.bintrayrelease

import guru.stefma.androidartifacts.AndroidArtifactsExtension

open class AndroidPublishExtension(
        private val artifactsExtension: AndroidArtifactsExtension
) : PublishExtension() {

    override var artifactId: String? = null
        set(value) {
            field = value
            artifactsExtension.artifactId = value
        }

}