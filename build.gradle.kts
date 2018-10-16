import guru.stefma.bintrayrelease.PublishExtension

plugins {
    groovy
    kotlin("jvm") version "1.2.60"
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
    implementation(localGroovy())
    implementation(kotlin("stdlib-jdk8"))
    implementation("com.jfrog.bintray.gradle:gradle-bintray-plugin:1.8.4")
    implementation("guru.stefma.androidartifacts:androidartifacts:1.2.0")

    testImplementation("junit:junit:4.12")
    testImplementation("org.assertj:assertj-core:3.9.0")
}

tasks.getByName<GroovyCompile>("compileGroovy") {
    sourceCompatibility = "1.6"
    targetCompatibility = "1.6"
}

gradlePlugin {
    plugins {
        create("binrayRelease ") {
            id = "guru.stefma.bintrayrelease"
            implementationClass = "guru.stefma.bintrayrelease.ReleasePlugin"
        }
    }
}

version = "1.0.0"
group = "guru.stefma.bintrayrelease"
configure<PublishExtension> {
    userOrg = "stefma"
    artifactId = "bintrayrelease"
    uploadName = "BintrayRelease"
    desc = "Super duper easy way to release your Android and other artifacts to bintray"
    website = "https://github.com/StefMa/bintray-release"
}
