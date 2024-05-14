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
import org.radarbase.datadashboard.api.api.ObservationListDto
import org.radarbase.datadashboard.api.domain.ObservationRepository
import org.radarbase.datadashboard.api.domain.mapper.toDto
import org.radarbase.datadashboard.api.domain.model.Observation
import java.time.ZonedDateTime

class ObservationServiceTest {

    // Create a Mockito mock of the ObservationRepository. This is instantiated in the init block.
    @Mock
    private lateinit var observationRepository: ObservationRepository

    private var observationId: Long = 1
    private val projectId = "project-1"
    private val subjectId = "sub-1"
    private val topicId = "topic-1"

    private val observationService: ObservationService

    init {
        // Initialize all Mockito mocks.
        MockitoAnnotations.openMocks(this)
        observationService = ObservationService(observationRepository)
    }

    /** This test does not test much (only whether the service calls the repository).
     *  I made it mainly to document how to write a test with mocking.
     * */
    @Test
    fun test_getObservations1() {
        // Create some fake observations that are returned by the repository.
        // Each observation is linked to a Variable.
        val observations: List<Observation> = listOf(createObservation(), createObservation(), createObservation(), createObservation())

        // Mock the repository to return the fake observations.
        `when`(observationRepository.getObservations(projectId = projectId, subjectId = subjectId, topicId = topicId)).thenReturn(observations)

        // Call the ObservationService (class under test) to get the observations.
        val result = observationService.getObservations(projectId = projectId, subjectId = subjectId, topicId = topicId)

        // Check if the result is as expected (observations transformed to ObservationListDto).
        val expectedDto = ObservationListDto(
            observations.map { it.toDto() },
        )
        assertEquals(expectedDto, result)
    }

    private fun createObservation(): Observation {
        return Observation(
            project = "project-1",
            subject = subjectId,
            source = "source-1",
            topic = "topic-1",
            category = "category-1",
            variable = "variable-1",
            date = ZonedDateTime.now(),
            valueTextual = "value1",
            valueNumeric = null,
            endDate = null,
        )
    }
}
