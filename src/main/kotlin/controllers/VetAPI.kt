package controllers



import models.Vet

class VetAPI {
    private var vets = ArrayList<Vet>()

    fun addVet(vet: Vet): Boolean {
        return vets.add(vet)
    }

    fun deleteVet(indexToDelete: Int) {
        vets.removeIf { vet -> vet.vetID == indexToDelete }
    }
    fun findVet(id: Int): Vet? {
        return vets.find { vet -> vet.vetID == id }
    }

    fun numberOfVets(): Int = vets.size



}