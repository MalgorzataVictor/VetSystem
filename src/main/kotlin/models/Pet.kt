package models

import java.time.LocalDate
/**
 * Represents a Pet with associated information.
 *
 * @property petID Unique identifier for the pet
 * @property name Name of the pet
 * @property breed Breed of the pet
 * @property DOB Date of Birth of the pet
 * @property isVaccinated Indicates if the pet is vaccinated or not
 * @property vetID Unique identifier of the vet associated with the pet
 * @property ownerPPS Personal Public Service number of the pet's owner
 * @constructor Creates a Pet with specified properties
 */

data class Pet(
    var petID: Int,
    var name: String,
    var breed: String,
    var DOB: LocalDate,
    var isVaccinated: Boolean,
    var vetID: Int,
    var ownerPPS: String

) {
    /**
     * Provides a string representation of the Pet object.
     *
     * @return String representation of the Pet
     */
    override fun toString(): String {
        return "\uD83D\uDC3E PetID: $petID, Name: $name, Breed: $breed, DOB: $DOB, Vaccinated: $isVaccinated, VetID: $vetID, OwnerPPS: $ownerPPS"
    }
}
