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
import jakarta.ws.rs.*
import jakarta.ws.rs.container.ContainerRequestContext
import jakarta.ws.rs.core.Context
import jakarta.ws.rs.core.Response
import org.radarbase.datadashboard.api.service.ObservationService
import org.radarbase.auth.authorization.Permission
import org.radarbase.datadashboard.api.api.ObservationDto
import org.radarbase.datadashboard.api.api.ObservationListDto
import org.radarbase.jersey.auth.Authenticated
import org.radarbase.jersey.auth.NeedsPermission

@Path("subject/{subjectId}/topic/{topicId}")
@Resource
@Produces("application/json")
@Consumes("application/json")
@Authenticated
class ObservationResource(
    @Context private val observationService: ObservationService
) {
    @GET
    @Path("observations")
    @NeedsPermission(Permission.MEASUREMENT_READ)
    fun getObservations(
        @PathParam("subjectId") subjectId: String,
        @PathParam("topicId") topicId: String
    ): ObservationListDto {
//        if (request.securityContext != null && request.securityContext is RadarSecurityContext) {
//            val userName = (request.securityContext as RadarSecurityContext).userPrincipal
//            if (!subjectId.equals(userName)) throw NotFoundException("Subjects can only access their own data.")
            return observationService.getObservations(topicId, subjectId)
//        }
//        return emptyList()
    }
}
