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

package org.radarbase.datadashboard.api.api

/** Single observation or data point. */
data class ObservationDto(

    /** Unique identifier of project. */
    val project: String?,

    /** Unique identifier of study subject. */
    val subject: String?,

    /** Unique identifier of the data source. */
    val source: String?,

    /** Unique identifier of the kafka topic. */
    val topic: String?,

    /** Category of the observation (optional). */
    val category: String?,

    /** Date or date-time of the observation. */
    val date: String?,

    /**
     * For how long the observation was valid. Null if there is no duration, or if the observation
     * lasted for exactly the date or time in [date].
     */
    val period: String?,

    /**
     * Value of the observation. This should adhere to the coding system and type of the
     * variable.
     */
    val value: Any?,
)
