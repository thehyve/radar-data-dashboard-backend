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

import jakarta.ws.rs.core.Context
import org.radarbase.datadashboard.api.api.ObservationListDto
import org.radarbase.datadashboard.api.domain.ObservationRepository
import org.radarbase.datadashboard.api.domain.mapper.toDto

class ObservationService(
    @Context private val observationRepository: ObservationRepository,
) {
    fun getObservations(projectId: String, subjectId: String, topicId: String): ObservationListDto {
        val result = this.observationRepository.getObservations(projectId = projectId, topicId = topicId, subjectId = subjectId)
        return ObservationListDto(
            result.map { it.toDto() },
        )
    }
}
