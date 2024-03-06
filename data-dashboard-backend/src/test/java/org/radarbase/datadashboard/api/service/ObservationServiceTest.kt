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

package org.radarbase.datadashboard.api.service

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.radarbase.datadashboard.api.api.VariableListDto
import org.radarbase.datadashboard.api.domain.ObservationRepository
import org.radarbase.datadashboard.api.domain.mapper.toDto
import org.radarbase.datadashboard.api.domain.model.Observation
import org.radarbase.datadashboard.api.domain.model.Variable

class ObservationServiceTest {

    // Create a Mockito mock of the ObservationRepository. This is instantiated in the init block.
    @Mock
    private lateinit var observationRepository: ObservationRepository

    private val observationService: ObservationService

    init {
        // Initialize all Mockito mocks.
        MockitoAnnotations.openMocks(this)
        observationService = ObservationService(observationRepository)
    }

    @Test
    fun test_getObservations1() {

        // Create some fake observations that are returned by the repository.
        // Each observation is linked to a Variable.
        val subjectId = "sub-1"
        val var1 = createVariable(id = 1L, name = "name1")
        val var2 = createVariable(id = 2L, name = "name2")
        val obs1 = createObservation(id = 1L, variable = var1)
        val obs2 = createObservation(id = 2L, variable = var1)
        val obs3 = createObservation(id = 1L, variable = var2)
        val obs4 = createObservation(id = 2L, variable = var2)
        val observations = listOf(obs1, obs2, obs3, obs4)

        // Mock the repository to return the fake observations.
        `when`(observationRepository.getObservations(subjectId)).thenReturn(observations)

        // Call the ObservationService (class under test) to get the observations.
        val result = observationService.getObservations(subjectId)

        // Check if the result is as expected (observations keyed by the Variable).
        val expectedDto = VariableListDto(
            observations.groupBy { it.variable }.map { it.toDto() }
        )
        assertEquals(expectedDto, result)
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