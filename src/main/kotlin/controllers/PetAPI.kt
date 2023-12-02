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

}
