package models

data class Owner(
    var PPS: Int,
    var name: String,
    var phoneNumber: String,
    var email: String,
    var petsList: MutableList<Pet> = mutableListOf()
) {

    fun formatListStringPatientList(): String =
        petsList
            .joinToString(separator = "\n") { petsl ->
                "" + petsList.indexOf(petsl).toString() + ": " + petsl
            }

    override fun toString(): String {
        val petsInfo = petsList.joinToString("\n") { it.toString() }
        return " Name: $name, PPS: $PPS, Phone No: $phoneNumber, Email: $email " +
            "Patients:\n$petsInfo"
    }
}
