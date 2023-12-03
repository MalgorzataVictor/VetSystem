package models

import java.time.LocalDate

data class Vet(
    var vetID: Int,
    var name: String,
    var dateQualified: LocalDate,
    var specialisation: MutableList<String> = mutableListOf(),
    var salary: Double,
    var position: String,
    var patientList: MutableList<Int> = mutableListOf()
) {
    fun formatListStringSpecialisation() {
        specialisation.joinToString("\n") { it.toString() }
    }
    fun formatListStringPatientList() {
        patientList.joinToString("\n") { it.toString() }
    }
    override fun toString(): String {
        val patientsInfo = patientList.joinToString("\n") { it.toString() }
        return "vetID: $vetID, Name: $name, Date Qualified: $dateQualified, Specialisation: $specialisation, Salary: $salary, Position: $position, \nPatients: $patientList "
    }
}
