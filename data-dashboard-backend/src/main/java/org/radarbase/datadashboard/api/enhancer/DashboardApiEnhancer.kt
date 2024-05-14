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

package org.radarbase.datadashboard.api.enhancer

import jakarta.inject.Singleton
import org.glassfish.jersey.internal.inject.AbstractBinder
import org.radarbase.datadashboard.api.config.DashboardApiConfig
import org.radarbase.datadashboard.api.domain.ObservationRepository
import org.radarbase.datadashboard.api.service.ObservationService
import org.radarbase.jersey.enhancer.JerseyResourceEnhancer
import org.radarbase.jersey.filter.Filters

class DashboardApiEnhancer(
    private val config: DashboardApiConfig,
) : JerseyResourceEnhancer {
    override val classes: Array<Class<*>>
        get() = listOfNotNull(
            Filters.cache,
            Filters.logResponse,
            if (config.service.enableCors == true) Filters.cors else null,
        ).toTypedArray()

    override val packages: Array<String> = arrayOf(
        "org.radarbase.datadashboard.api.resource",
    )

    override fun AbstractBinder.enhance() {
        bind(config)
            .to(DashboardApiConfig::class.java)

        bind(ObservationService::class.java)
            .to(ObservationService::class.java)
            .`in`(Singleton::class.java)

        bind(ObservationRepository::class.java)
            .to(ObservationRepository::class.java)
            .`in`(Singleton::class.java)
    }
}
