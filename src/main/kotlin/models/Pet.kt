package models

import java.time.LocalDate

data class Pet(
    var petID: Int,
    var name: String,
    var breed: String,
    var DOB: LocalDate,
    var isVaccinated: Boolean,
    var vetID: Int,
    var ownerPPS: Int

) {
    override fun toString(): String {
        return "PetID: $petID, Name: $name, Breed: $breed, DOB: $DOB, Vaccinated: $isVaccinated"
    }
}
