package models

import java.time.LocalDate

data class Vet(
    var vetID: Int,
    var name: String,
    var dateQualified: LocalDate,
    var specialisation: MutableList<String> = mutableListOf(),
    var salary: Double,
    var position: String,
    var patientList: MutableList<Pet> = mutableListOf()
) {
    fun formatListStringSpecialisation(): String =
        specialisation
            .joinToString(separator = "\n") { spec ->
                "" + specialisation.indexOf(spec).toString() + ": " + spec
            }
    fun formatListStringPatientList(): String =
        patientList
            .joinToString(separator = "\n") { pat ->
                "" + patientList.indexOf(pat).toString() + ": " + pat
            }
    override fun toString(): String {
        val patientsInfo = patientList.joinToString("\n") { it.toString() }
        return "vetID: $vetID, Name: $name, Date Qualified: $dateQualified, Specialisation: $specialisation, Salary: $salary, Position: $position, Patients: $patientList " +
            "\nPatients:$patientsInfo"
    }
}
