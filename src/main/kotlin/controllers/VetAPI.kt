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

    fun updateVet(indexToUpdate: Int, vet: Vet?): Boolean {
        val foundVet = findVet(indexToUpdate)

        if ((foundVet != null) && (vet != null)) {
            foundVet.vetID = vet.vetID
            foundVet.name = vet.name
            foundVet.dateQualified = vet.dateQualified
            foundVet.specialisation = vet.specialisation
            foundVet.salary = vet.salary
            foundVet.position = vet.position
            foundVet.patientList = vet.patientList

            return true
        }

        return false
    }


}