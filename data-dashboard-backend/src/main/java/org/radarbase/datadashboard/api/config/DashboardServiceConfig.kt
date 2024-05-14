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

package org.radarbase.datadashboard.api.config

import org.radarbase.datadashboard.api.enhancer.DashBoardApiEnhancerFactory
import org.radarbase.jersey.enhancer.EnhancerFactory
import java.net.URI
import kotlin.time.Duration.Companion.days

data class DashboardServiceConfig(
    val baseUri: URI = URI.create("http://0.0.0.0:9000/data-dashboard-backend/"),
    val advertisedBaseUri: URI? = null,
    val frontendBaseUri: URI? = null,
    val resourceConfig: Class<out EnhancerFactory> = DashBoardApiEnhancerFactory::class.java,
    val enableCors: Boolean? = false,
    val tokenExpiryTimeInMinutes: Long = 15,
    val persistentTokenExpiryInMin: Long = 3.days.inWholeMinutes,
) {
    fun withEnv(): DashboardServiceConfig = copy(
        baseUri = URI.create(System.getenv("RADAR_DATA_DASHBOARD_BASE_URI") ?: baseUri.toString()),
        advertisedBaseUri = URI.create(System.getenv("RADAR_DATA_DASHBOARD_ADVERTISED_BASE_URI") ?: advertisedBaseUri.toString()),
        frontendBaseUri = URI.create(System.getenv("RADAR_DATA_DASHBOARD_FRONTEND_BASE_URI") ?: frontendBaseUri.toString()),
        enableCors = System.getenv("RADAR_DATA_DASHBOARD_ENABLE_CORS")?.toBoolean() ?: enableCors,
        tokenExpiryTimeInMinutes = System.getenv("RADAR_DATA_DASHBOARD_TOKEN_EXPIRY_TIME_IN_MINUTES")?.toLong() ?: tokenExpiryTimeInMinutes,
        persistentTokenExpiryInMin = System.getenv("RADAR_DATA_DASHBOARD_PERSISTENT_TOKEN_EXPIRY_IN_MIN")?.toLong() ?: persistentTokenExpiryInMin,
    )
}
