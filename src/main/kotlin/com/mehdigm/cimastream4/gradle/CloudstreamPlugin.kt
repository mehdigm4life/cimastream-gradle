package com.mehdigm.cimastream4.gradle

import com.mehdigm.cimastream4.gradle.configuration.registerConfigurations
import com.mehdigm.cimastream4.gradle.tasks.registerTasks
import org.gradle.api.Plugin
import org.gradle.api.Project

abstract class CloudstreamPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        project.extensions.create("cloudstream", CloudstreamExtension::class.java, project)
        registerTasks(project)
        registerConfigurations(project)
    }
}
