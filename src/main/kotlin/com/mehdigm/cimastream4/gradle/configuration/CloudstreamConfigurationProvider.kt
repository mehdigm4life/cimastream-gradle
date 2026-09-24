package com.mehdigm.cimastream4.gradle.configuration

import org.gradle.api.Project
import org.gradle.api.artifacts.Dependency

/**
 * Resolves the declared CimaStream API dependency (e.g.
 * cloudstream("com.mehdigm.api:library:pre-release")) from the configured
 * repositories (JitPack) and adds it to the compileOnly classpath.
 *
 * The API classes are never bundled into the extension package, they are
 * resolved against the app itself at runtime.
 */
abstract class CloudstreamConfigurationProvider : IConfigurationProvider {

    override val name: String
        get() = "cloudstream"

    override fun provide(project: Project, dependency: Dependency) {
        val group = dependency.group ?: "unknown"
        val version = dependency.version ?: "unknown"
        project.logger.lifecycle("Adding CimaStream API $group:${dependency.name}:$version to compileOnly")

        project.dependencies.add("compileOnly", dependency)
    }
}