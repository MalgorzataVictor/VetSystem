package models

import java.time.LocalDate

/**
 * Represents a Vet with associated information.
 *
 * @property vetID Unique identifier for the vet
 * @property name Name of the vet
 * @property dateQualified Date when the vet qualified
 * @property specialisation List of specializations of the vet
 * @property salary Salary of the vet
 * @property position Position held by the vet
 * @property patientList List of patients associated with the vet
 * @constructor Creates a Vet with specified properties
 */
data class Vet(
    var vetID: Int,
    var name: String,
    var dateQualified: LocalDate,
    var specialisation: MutableList<String> = mutableListOf(),
    var salary: Double,
    var position: String,
    var patientList: MutableList<Int> = mutableListOf()
) {

    /**
     * Provides a string representation of the Vet object.
     *
     * @return String representation of the Vet
     */
    override fun toString(): String {
        return "\uD83D\uDD35 VetID: $vetID, Name: $name, Date Qualified: $dateQualified, Specialisation: $specialisation, Salary: $salary, Position: $position"
    }
}
