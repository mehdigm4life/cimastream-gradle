import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "2.4.0"
    id("java-gradle-plugin")
    id("maven-publish")
}

group = "com.mehdigm.cimastream4"

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

tasks.withType<KotlinCompile> {
    compilerOptions {
        // Disables some unnecessary features
        freeCompilerArgs.addAll(
            listOf(
                "-Xno-call-assertions",
                "-Xno-param-assertions",
                "-Xno-receiver-assertions"
            )
        )

        jvmTarget.set(JvmTarget.JVM_11)  // Required
    }
}

repositories {
    mavenCentral()
    google()
    maven("https://jitpack.io")
}

dependencies {
    implementation(kotlin("stdlib", kotlin.coreLibrariesVersion))
    compileOnly(gradleApi())

    compileOnly("com.google.guava:guava:33.6.0-jre")
    compileOnly("com.android.tools:sdk-common:32.1.1")
    compileOnly("com.android.tools.build:gradle:9.1.1")
    compileOnly("org.jetbrains.kotlin:kotlin-gradle-plugin:2.4.0")

    implementation("org.ow2.asm:asm:9.9.1")
    implementation("org.ow2.asm:asm-tree:9.9.1")
    implementation("com.github.vidstige:jadb:v1.2.1")
}

gradlePlugin {
    plugins {
        create("cimastream4.gradle") {
            id = "com.mehdigm.cimastream4.gradle"
            implementationClass = "com.mehdigm.cimastream4.gradle.CloudstreamPlugin"
        }
    }
}

publishing {
    repositories {
        mavenLocal()

        val token = project.findProperty("gpr.key") as String? ?: System.getenv("GITHUB_TOKEN")

        if (token != null) {
            maven {
                credentials {
                    username = "mehdigm4life"
                    password = token
                }
                setUrl("https://maven.pkg.github.com/mehdigm4life/cimastream-gradle")
            }
        }
    }
}
