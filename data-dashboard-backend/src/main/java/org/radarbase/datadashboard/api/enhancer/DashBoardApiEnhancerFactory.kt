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

import org.radarbase.datadashboard.api.config.DashboardApiConfig
import org.radarbase.datadashboard.api.domain.model.Observation
import org.radarbase.jersey.enhancer.EnhancerFactory
import org.radarbase.jersey.enhancer.Enhancers
import org.radarbase.jersey.enhancer.JerseyResourceEnhancer
import org.radarbase.jersey.hibernate.config.HibernateResourceEnhancer
import kotlin.reflect.jvm.jvmName

class DashBoardApiEnhancerFactory(
    private val config: DashboardApiConfig,
) : EnhancerFactory {

    override fun createEnhancers(): List<JerseyResourceEnhancer> = listOf(
        DashboardApiEnhancer(config),
        Enhancers.radar(config.auth),
        Enhancers.managementPortal(config.auth),
        Enhancers.health,
        Enhancers.exception,
        HibernateResourceEnhancer(
            config.database.copy(
                managedClasses = listOf(
                    Observation::class.jvmName,
                ),
            ),
        ),
    )
}
