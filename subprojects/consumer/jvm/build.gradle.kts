import guru.stefma.bintrayrelease.PublishExtension
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
configure<PublishExtension> {
    userOrg = "stefma"
    artifactId = "jvm-sample"
    desc = "Just a simple java lib sample"
    website = "https://github.com/stefma/bintray-release"
}
