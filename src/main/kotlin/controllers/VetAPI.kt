package controllers

import models.Pet
import models.Vet
import persistence.Serializer
import utils.Utilities
import java.time.LocalDate
import java.time.Period

class VetAPI(serializerType: Serializer) {
    private var serializer: Serializer = serializerType
    private var vets = ArrayList<Vet>()

    private fun getVetID(): Int {
        if (vets.isEmpty()) {
            return 1
        }
        return vets.last().vetID + 1
    }

    fun listAllVets(): String =
        if (vets.isEmpty()) {
            "No vets stored"
        } else {
            formatListString(vets)
        }

    fun getAllVets(): ArrayList<Vet> {
        return vets
    }

    fun addVet(vet: Vet): Boolean {
        vet.vetID = getVetID()
        return vets.add(vet)
    }

    fun deleteVet(indexToDelete: Int): Vet? {
        return if (Utilities.isValidListIndex(indexToDelete, vets)) {
            vets.removeAt(indexToDelete)
        } else {
            null
        }
    }

    fun findVetIndex(vet: Vet): Int {
        return vets.indexOf(vet)
    }

    fun findVet(id: Int): Vet? {
        return vets.find { vet -> vet.vetID == id }
    }

    fun findVetByIndex(id: Int): Vet? {
        return vets.get(id)
    }
    fun numberOfVets(): Int = vets.size

    fun searchByName(searchString: String) =
        formatListString(
            vets.filter { vet -> vet.name.contains(searchString, ignoreCase = true) }
        )

    fun updateVet(indexToUpdate: Int, vet: Vet?): Boolean {
        if (!Utilities.isValidListIndex(indexToUpdate, vets)) {
            return false
        }
        val foundVet = findVetByIndex(indexToUpdate)

        if (foundVet != null && vet != null) {
            // Update the fields of the found vet with the new information
            foundVet.name = vet.name
            foundVet.dateQualified = vet.dateQualified
            foundVet.specialisation = vet.specialisation
            foundVet.salary = vet.salary
            foundVet.position = vet.position

            return true
        }

        return false
    }

    fun filterVetsByExperience(yearsOfExperience: Int): List<Vet> {
        val currentDate = LocalDate.now()
        return vets.filter { vet ->
            vet.dateQualified.let { dateQualified ->
                val years = Period.between(dateQualified, currentDate).years
                years >= yearsOfExperience
            } ?: false // Filter out vets with null dateQualified values
        }
    }

    fun isValidIndex(index: Int): Boolean {
        return Utilities.isValidListIndex(index, vets)
    }

    fun assignPetToVet(index: Int, pet: Pet): Boolean? {
        return findVetByIndex(index)?.patientList?.add(pet.petID)
    }

    fun unAssignPetFromVet(oldIndex: Int, newIndex: Int, pet: Pet) {
        findVetByIndex(oldIndex)?.patientList?.remove(pet.petID)
        findVetByIndex(newIndex)?.patientList?.add(pet.petID)
    }
    fun searchVetSpecialisation(specialisation: String): List<Vet> {
        return vets.filter { vet ->
            vet.specialisation.contains(specialisation)
        }
    }

    fun formatListString(notesToFormat: List<Vet>): String =
        notesToFormat
            .joinToString(separator = "\n") { vet ->
                "" + vets.indexOf(vet).toString() + ": " + vet.toString()
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
