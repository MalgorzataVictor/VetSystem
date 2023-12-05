package models

import java.time.LocalDate

data class Pet(
    var petID: Int,
    var name: String,
    var breed: String,
    var DOB: LocalDate,
    var isVaccinated: Boolean,
    var vetID: Int,
    var ownerPPS: String

) {
    override fun toString(): String {
        return "\uD83D\uDC3E PetID: $petID, Name: $name, Breed: $breed, DOB: $DOB, Vaccinated: $isVaccinated, VetID: $vetID, OwnerPPS: $ownerPPS"
    }
}
