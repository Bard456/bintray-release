import guru.stefma.bintrayrelease.PublishExtension
import guru.stefma.androidartifacts.ArtifactsExtension
import guru.stefma.bintrayrelease.BintrayReleasePlugin

apply(plugin = "java-library")
apply<BintrayReleasePlugin>()

repositories {
    jcenter()
}

dependencies {
    "implementation"("junit:junit:4.12")
}

group = "guru.stefma.bintrayrelease.consumer"
version = "0.0.1"
configure<ArtifactsExtension> {
    artifactId = "jvm-sample"
}

configure<PublishExtension> {
    userOrg = "stefma"
    desc = "Just a simple java lib sample"
    website = "https://github.com/stefma/bintray-release"
}
