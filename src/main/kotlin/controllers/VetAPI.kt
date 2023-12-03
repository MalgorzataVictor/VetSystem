package controllers

import models.Vet
import persistence.Serializer
class VetAPI(serializerType: Serializer) {
    private var serializer: Serializer = serializerType
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

        if (foundVet != null && vet != null) {
            // Update the fields of the found vet with the new information
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

    fun formatListString(notesToFormat: List<Vet>): String =
        notesToFormat
            .joinToString(separator = "\n") { vet ->
                "        📌 " + vets.indexOf(vet).toString() + ": " + vet.toString()
            }

    @Throws(Exception::class)
    fun loadVets() {
        vets = serializer.read() as ArrayList<Vet>
    }

    @Throws(Exception::class)
    fun saveVets() {
        serializer.write(vets)
    }
}
