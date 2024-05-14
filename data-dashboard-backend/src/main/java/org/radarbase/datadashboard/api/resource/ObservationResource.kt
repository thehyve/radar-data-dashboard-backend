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

import jakarta.annotation.Resource
import jakarta.ws.rs.Consumes
import jakarta.ws.rs.GET
import jakarta.ws.rs.NotFoundException
import jakarta.ws.rs.Path
import jakarta.ws.rs.PathParam
import jakarta.ws.rs.Produces
import jakarta.ws.rs.container.ContainerRequestContext
import jakarta.ws.rs.core.Context
import org.radarbase.auth.authorization.Permission
import org.radarbase.datadashboard.api.api.ObservationListDto
import org.radarbase.datadashboard.api.service.ObservationService
import org.radarbase.jersey.auth.Authenticated
import org.radarbase.jersey.auth.NeedsPermission
import org.radarbase.jersey.auth.filter.RadarSecurityContext
import org.slf4j.LoggerFactory

@Path("project/{projectId}/subject/{subjectId}/topic/{topicId}")
@Resource
@Produces("application/json")
@Consumes("application/json")
@Authenticated
class ObservationResource(
    @Context private val observationService: ObservationService,
    @Context private val request: ContainerRequestContext,
) {
    @GET
    @Path("observations")
    @NeedsPermission(Permission.MEASUREMENT_READ)
    fun getObservations(
        @PathParam("projectId") projectId: String,
        @PathParam("subjectId") subjectId: String,
        @PathParam("topicId") topicId: String,
    ): ObservationListDto {
        if (request.securityContext != null && request.securityContext is RadarSecurityContext) {
            val userName = (request.securityContext as RadarSecurityContext).userPrincipal
            log.info("User $userName is accessing observations for $subjectId")
            if (!subjectId.equals(userName)) throw NotFoundException("Subjects can only request their own observations.")
            return observationService.getObservations(projectId = projectId, subjectId = subjectId, topicId = topicId)
        }
        return ObservationListDto(emptyList())
    }

    companion object {
        private val log = LoggerFactory.getLogger(ObservationResource::class.java)
    }
}
