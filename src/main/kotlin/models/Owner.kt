package models

data class Owner(
    var PPS: String,
    var name: String,
    var phoneNumber: String,
    var email: String,
    var petsList: MutableList<Int> = mutableListOf()
) {

    fun formatListStringPetsList() {
        petsList.joinToString("\n") { it.toString() }
    }

    override fun toString(): String {
        return " Name: $name, PPS: $PPS, Phone No: $phoneNumber, Email: $email"
    }
}
