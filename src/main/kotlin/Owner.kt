

data class Owner(
    var PPS: Int,
    var name: String,
    var phoneNumber: String,
    var email: String,
    var petsList: MutableList<Pet> = mutableListOf()
) {
    override fun toString(): String {
        val petsInfo = petsList.joinToString("\n") { it.toString() }
        return " Name: $name, PPS: $PPS, Phone No: $phoneNumber, Email: $email " +
            "Patients:\n$petsInfo"
    }
}
