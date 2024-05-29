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

package org.radarbase.datadashboard.api.domain.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.ZonedDateTime
import java.util.*

@Entity
@Table(name = "observation")
data class Observation(
    @Column(nullable = false)
    @Id
    val project: String,

    @Column(nullable = false)
    @Id
    val subject: String,

    @Id
    val source: String,

    @Column(nullable = false)
    @Id
    val topic: String,

    val category: String,

    @Column(nullable = false)
    @Id
    val variable: String,

    @Column(nullable = false)
    @Id
    val date: ZonedDateTime,

    @Column(name = "end_date")
    val endDate: ZonedDateTime?,

    @Column(name = "value_textual")
    val valueTextual: String?,

    @Column(name = "value_numeric")
    val valueNumeric: Double?,

) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Observation
        return subject == other.subject &&
            topic == other.topic &&
            category == other.category &&
            variable == other.variable &&
            date == other.date &&
            endDate == other.endDate
    }

    override fun hashCode(): Int = Objects.hash(subject, variable, date)

    companion object {
        internal fun String?.toPrintString() = if (this != null) "'$this'" else "null"
    }
}
