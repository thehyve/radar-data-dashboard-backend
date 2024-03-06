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

package org.radarbase.datadashboard.api.resource

import jakarta.ws.rs.core.Application
import org.glassfish.hk2.utilities.binding.AbstractBinder
import org.glassfish.jersey.server.ResourceConfig
import org.glassfish.jersey.test.JerseyTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.radarbase.datadashboard.api.api.VariableListDto
import org.radarbase.datadashboard.api.domain.mapper.toDto
import org.radarbase.datadashboard.api.domain.model.Observation
import org.radarbase.datadashboard.api.domain.model.Variable
import org.radarbase.datadashboard.api.service.ObservationService

class ObservationResourceTest: JerseyTest() {


    @Mock
    lateinit var observationService: ObservationService

    private lateinit var variableListDto: VariableListDto
    private val subjectId = "sub-1"

    override fun configure(): Application {
        // Initialize all defined Mockito mocks (with @Mock annotation).
        MockitoAnnotations.openMocks(this)
        // Configure the Jersey Application.
        val resourceConfig = ResourceConfig(ObservationResource::class.java)
        // Register the ObservationService mock for dependency injection (needed by ObservationResource).
        resourceConfig.register(object : AbstractBinder() {
            override fun configure() {
                bind(observationService).to(ObservationService::class.java)
            }
        })
        return resourceConfig
    }

    @BeforeEach
    fun init() {
        // Create some fake observations that are returned by the service.
        // Each observation is linked to a Variable.
        val var1 = createVariable(id = 1L, name = "name1")
        val var2 = createVariable(id = 2L, name = "name2")
        val obs1 = createObservation(id = 1L, variable = var1)
        val obs2 = createObservation(id = 2L, variable = var1)
        val obs3 = createObservation(id = 1L, variable = var2)
        val obs4 = createObservation(id = 2L, variable = var2)
        val observations: List<Observation> = listOf(obs1, obs2, obs3, obs4)
        // Create Dto that should be returned by the ObservationService.
        variableListDto = VariableListDto(
            observations
                .groupBy { it.variable }
                .map { it.toDto() },
        )
    }

    @Test
    fun testGetObservations() {
        // Instruct the mock to return the fake observations when called.
        `when`(observationService.getObservations(subjectId)).thenReturn(variableListDto)
        // Make the call to the REST endpoint.
        val response = target("subject/sub-1/variables/observations").request().get()
        // Expect the http response to be OK and the same as the expected DTO.
        assertEquals(200, response.status)
        assertEquals(variableListDto, response.readEntity(VariableListDto::class.java))
    }

    @Test
    fun testGetObservations_failNoSubjectId() {
        // Make the call to the REST endpoint.
        val response = target("subject//variables/observations").request().get()
        // Expect the http response to be OK and the same as the expected DTO.
        assertEquals(404, response.status)
    }

    private fun createVariable(id: Long, name: String): Variable {
        return Variable(
            id = id,
            name = name,
            type = "type1",
            category = null,
            observations = emptyList()
        )
    }

    private fun createObservation(id: Long, variable: Variable): Observation {
        return Observation(
            id = id,
            subjectId = "sub-1",
            variable = variable,
            date = null,
            valueTextual = "value1",
            valueNumeric = 1.0,
            endDate = null
        )
    }

}