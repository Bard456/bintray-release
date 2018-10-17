import guru.stefma.bintrayrelease.PublishExtension

plugins {
    kotlin("jvm") version "1.2.71"
    `java-gradle-plugin`
    `java-library`
    id("guru.stefma.bintrayrelease") version "1.0.0" apply false
    maven
}
apply(plugin = "guru.stefma.bintrayrelease")

repositories {
    google()
    jcenter()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("com.jfrog.bintray.gradle:gradle-bintray-plugin:1.8.4")
    implementation("guru.stefma.androidartifacts:androidartifacts:1.2.0")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.3.1")
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.3.1")
    testRuntime("org.junit.jupiter:junit-jupiter-engine:5.3.1")
    testImplementation("org.assertj:assertj-core:3.11.1")
}

tasks.withType(Test::class.java) {
    useJUnitPlatform()
}

gradlePlugin {
    plugins {
        create("binrayRelease") {
            id = "guru.stefma.bintrayrelease"
            implementationClass = "guru.stefma.bintrayrelease.BintrayReleasePlugin"
        }
    }
}

version = "1.1.0"
group = "guru.stefma.bintrayrelease"
configure<PublishExtension> {
    userOrg = "stefma"
    artifactId = "bintrayrelease"
    uploadName = "BintrayRelease"
    desc = "Super duper easy way to release your Android and other artifacts to bintray"
    website = "https://github.com/StefMa/bintray-release"
}
