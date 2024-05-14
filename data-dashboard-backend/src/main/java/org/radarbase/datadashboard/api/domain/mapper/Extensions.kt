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

package org.radarbase.datadashboard.api.domain.mapper

import org.radarbase.datadashboard.api.api.ObservationDto
import org.radarbase.datadashboard.api.domain.model.Observation
import java.time.Duration

fun Observation.toDto(): ObservationDto = ObservationDto(
    project = project,
    subject = subject,
    source = source,
    topic = topic,
    category = category,
    date = date.toString(),
    period = if (endDate != null) {
        Duration.between(date, endDate).toString()
    } else {
        null
    },
    value = valueNumeric ?: valueTextual,
)
