// Copyright 2000-2023 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.

package org.jetbrains.intellij

import org.gradle.api.Project
import org.jetbrains.intellij.IntelliJPluginConstants.PLUGIN_ID as prefix

enum class BuildFeature(private val defaultValue: Boolean) {
    NO_SEARCHABLE_OPTIONS_WARNING(true),
    PAID_PLUGIN_SEARCHABLE_OPTIONS_WARNING(true),
    SELF_UPDATE_CHECK(true),
    ;

    fun getValue(project: Project) = project.findProperty(toString())
        ?.toString()
        ?.toBoolean()
        .or { defaultValue }

    override fun toString() = name
        .lowercase()
        .split('_')
        .joinToString("", transform = {
            it.replaceFirstChar(Char::uppercase)
        })
        .replaceFirstChar(Char::lowercase)
        .let { "$prefix.buildFeature.$it" }
}

fun Project.isBuildFeatureEnabled(feature: BuildFeature) =
    feature
        .getValue(this)
        .apply {
            when (this) {
                true -> "Build feature is enabled: $feature"
                false -> "Build feature is disabled: $feature"
            }.also { info(logCategory(), it) }
        }
