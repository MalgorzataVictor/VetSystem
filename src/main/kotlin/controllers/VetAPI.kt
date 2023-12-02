package controllers


import models.Pet
import models.Vet

class VetAPI {
    private var vets = ArrayList<Vet>()

    fun addVet(vet: Vet): Boolean {
        return vets.add(vet)
    }
}