/*
 *
 *  *  Copyright 2024 The Hyve
 *  *
 *  *  Licensed under the Apache License, Version 2.0 (the "License");
 *  *  you may not use this file except in compliance with the License.
 *  *  You may obtain a copy of the License at
 *  *
 *  *    http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  *  Unless required by applicable law or agreed to in writing, software
 *  *  distributed under the License is distributed on an "AS IS" BASIS,
 *  *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  *  See the License for the specific language governing permissions and
 *  *  limitations under the License.
 *
 */

package org.radarbase.datadashboard.api

import jakarta.ws.rs.core.HttpHeaders
import org.glassfish.hk2.utilities.binding.AbstractBinder
import org.glassfish.jersey.server.ResourceConfig
import org.glassfish.jersey.servlet.ServletContainer
import org.glassfish.jersey.test.DeploymentContext
import org.glassfish.jersey.test.JerseyTest
import org.glassfish.jersey.test.ServletDeploymentContext
import org.glassfish.jersey.test.grizzly.GrizzlyWebTestContainerFactory
import org.glassfish.jersey.test.spi.TestContainerFactory
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.radarbase.datadashboard.api.config.DashboardApiConfig
import org.radarbase.datadashboard.api.resource.ObservationResource
import org.radarbase.jersey.auth.AuthValidator
import org.radarbase.jersey.auth.disabled.DisabledAuthValidator
import org.radarbase.jersey.config.ConfigLoader

class DashboardIntegrationTest : JerseyTest() {

    lateinit var disabledAuthValidator: DisabledAuthValidator

    override fun configure(): ResourceConfig {
        val config: DashboardApiConfig = ConfigLoader.loadConfig("src/test/resources/dashboard_test.yml", emptyArray())
        val resourceConfig = ConfigLoader.loadResources(config.service.resourceConfig, config)
        disabledAuthValidator = DisabledAuthValidator(config.auth)
        resourceConfig.register(object : AbstractBinder() {
            override fun configure() {
                bind(disabledAuthValidator).to(AuthValidator::class.java).ranked(1)
            }
        })
        resourceConfig.register(ObservationResource::class.java)
        return resourceConfig
    }

    override fun getTestContainerFactory(): TestContainerFactory {
        return GrizzlyWebTestContainerFactory()
    }

    // See https://stackoverflow.com/questions/37902211/test-case-for-testing-a-jersey-web-resource-using-grizzle-is-giving-me-404
    override fun configureDeployment(): DeploymentContext {
        return ServletDeploymentContext.forServlet(ServletContainer(configure())).build()
    }

    @Test
    fun testGetHealth() {
        // Added by the health enhancer from radar-jersey.
        val response = target("health").request().get()
        Assertions.assertEquals(200, response.status)
    }

    @Test
    fun testGetObservationsNoToken() {
        val response = target("project/project-1/sub-1/topic/phone_battery_level/observations").request().get()
        Assertions.assertEquals(401, response.status)
    }

    @Test
    fun testGetObservationsWithToken() {
        val response = target("project/project-1/subject/sub-1/topic/phone_battery_level/observations")
            .request()
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + "... encoded token ...")
            .get()
        Assertions.assertEquals(200, response.status)
    }

    // TODO add more tests that include the token validation.
}
