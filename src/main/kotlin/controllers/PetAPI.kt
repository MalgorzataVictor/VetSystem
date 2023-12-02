package controllers

import models.Pet

class PetAPI {
    private var pets = ArrayList<Pet>()



    fun add(pet: Pet): Boolean {
        return pets.add(pet)
    }


}
