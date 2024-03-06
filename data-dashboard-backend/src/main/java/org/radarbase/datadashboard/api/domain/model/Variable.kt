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

import org.radarbase.datadashboard.api.domain.model.Observation.Companion.toPrintString
import jakarta.persistence.*

@Entity
@Table(name = "variable")
class Variable(
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    val id: Long?,
    val name: String,
    val type: String?,
    val category: String?,
    @OneToMany(mappedBy = "variable", fetch = FetchType.LAZY)
    val observations: List<Observation>,
    @Enumerated(EnumType.STRING)
    @Column(name = "date_type")
    val dateType: DateType? = DateType.LOCAL_DATE,
) {
    enum class DateType {
        LOCAL_DATE, ZONED_DATE, LOCAL_DATE_TIME, ZONED_DATE_TIME,
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Variable

        if (id != null && other.id != null) {
            return id == other.id
        }

        return name == other.name &&
            type == other.type &&
            category == other.category &&
            dateType == other.dateType
    }

    override fun hashCode(): Int = name.hashCode()

    override fun toString(): String = "Variable(" +
        "id=$id, " +
        "name='$name', " +
        "type=${type.toPrintString()}, " +
        "category=${category.toPrintString()}, " +
        "dateType=$dateType)"
}
