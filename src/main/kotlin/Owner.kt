

data class Owner (
    var PPS: Int,
    var name: String,
    var phoneNumber: String,
    var email: String,
    var petList: MutableList<Pet> = mutableListOf()
        )
