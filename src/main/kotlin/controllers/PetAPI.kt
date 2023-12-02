package controllers

import models.Pet

class PetAPI {
    private var pets = ArrayList<Pet>()



    fun addPet(pet: Pet): Boolean {
        return pets.add(pet)
    }

    fun deletePet(indexToDelete: Int) {
        pets.removeIf { pet -> pet.petID == indexToDelete }
    }

    fun findPet(id: Int): Pet? {
        return pets.find { pet -> pet.petID == id }
    }

    fun updatePet(indexToUpdate: Int, pet: Pet?): Boolean {
        val foundPet = findPet(indexToUpdate)

        if ((foundPet != null) && (pet != null)) {
            foundPet.petID = pet.petID
            foundPet.name = pet.name
            foundPet.breed = pet.breed
            foundPet.DOB = pet.DOB
            foundPet.isVaccinated = pet.isVaccinated
            foundPet.vetID = pet.vetID
            foundPet.PPS = pet.PPS
            return true
        }

        return false
    }

    fun numberOfPets(): Int = pets.size
}
