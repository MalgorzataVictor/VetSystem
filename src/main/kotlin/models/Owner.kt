package models

data class Owner(
    var PPS: Int,
    var name: String,
    var phoneNumber: String,
    var email: String,
    var petsList: MutableList<Int> = mutableListOf()
) {

    fun formatListStringPetsList() {
        petsList.joinToString("\n") { it.toString() }
    }

    override fun toString(): String {
        val petsInfo = petsList.joinToString("\n") { it.toString() }
        return " Name: $name, PPS: $PPS, Phone No: $phoneNumber, Email: $email, \nPatients:$petsInfo"
    }
}
