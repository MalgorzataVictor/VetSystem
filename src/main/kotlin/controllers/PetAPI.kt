package controllers

import models.Pet
import persistence.Serializer
import utils.Utilities

class PetAPI(serializerType: Serializer) {
    private var serializer: Serializer = serializerType
    private var pets = ArrayList<Pet>()

    private fun getPetID(): Int {
        if (pets.isEmpty()) {
            return 1
        }
        return pets.last().petID + 1
    }

    fun getAllPets(): ArrayList<Pet> {
        return pets
    }

    fun listAllPets(): String =
        if (pets.isEmpty()) {
            "No pets stored"
        } else {
            formatListString(pets)
        }

    fun addPet(pet: Pet): Boolean {
        pet.petID = getPetID()
        return pets.add(pet)
    }

    fun deletePet(indexToDelete: Int): Pet? {
        return if (Utilities.isValidListIndex(indexToDelete, pets)) {
            pets.removeAt(indexToDelete)
        } else {
            null
        }
    }
    fun findPetIndex(pet: Pet): Int {
        return pets.indexOf(pet)
    }
    fun findPet(id: Int): Pet? {
        return pets.find { pet -> pet.petID == id }
    }
    private fun findPetByIndex(id: Int): Pet {
        return pets.get(id)
    }

    fun searchByName(searchString: String) =
        formatListString(
            pets.filter { pet -> pet.name.contains(searchString, ignoreCase = true) }
        )

    fun updatePet(indexToUpdate: Int, pet: Pet?): Boolean {
        if (!Utilities.isValidListIndex(indexToUpdate, pets)) {
            return false
        }
        val foundPet = findPetByIndex(indexToUpdate)

        if ((foundPet != null) && (pet != null)) {
            foundPet.petID = pet.petID
            foundPet.name = pet.name
            foundPet.breed = pet.breed
            foundPet.DOB = pet.DOB
            foundPet.isVaccinated = pet.isVaccinated
            foundPet.vetID = pet.vetID
            foundPet.ownerPPS = pet.ownerPPS
            return true
        }

        return false
    }

    fun isValidIndex(index: Int): Boolean {
        return Utilities.isValidListIndex(index, pets)
    }

    fun numberOfPets(): Int = pets.size

    fun formatListString(notesToFormat: List<Pet>): String =
        notesToFormat
            .joinToString(separator = "\n") { pet ->
                "" + pets.indexOf(pet).toString() + ": " + pet.toString()
            }

    @Throws(Exception::class)
    fun loadPets() {
        pets = serializer.read() as ArrayList<Pet>
    }

    @Throws(Exception::class)
    fun savePets() {
        serializer.write(pets)
    }
}
