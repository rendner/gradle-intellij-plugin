// Copyright 2024 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.

package org.jetbrains.intellij.platform.gradle.resolvers.closestVersion

import org.jetbrains.intellij.platform.gradle.IntelliJPluginTestBase
import org.jetbrains.intellij.platform.gradle.utils.toVersion
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class ClosestVersionResolverTest : IntelliJPluginTestBase() {

    private val url = resourceUrl("resolvers/closestVersion.xml")

    @Test
    fun `match exact version if present`() {
        assertNotNull(url)

        val version = "0.1.8".toVersion()
        val resolver = object : ClosestVersionResolver("test", url) {
            override fun resolve() = inMaven(version)
        }
        val resolvedVersion = resolver.resolve()

        assertEquals(version, resolvedVersion)
    }
}
