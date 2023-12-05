package controllers

import models.Pet
import models.Vet
import persistence.Serializer
import utils.Utilities
import java.time.LocalDate
import java.time.Period

/**
 * Manages operations related to the collection of vets.
 */
class VetAPI(serializerType: Serializer) {
    private var serializer: Serializer = serializerType
    private var vets = ArrayList<Vet>()

    /**
     * Retrieves the next available Vet ID in the collection. If the collection is empty,
     * returns the default ID as 1. Otherwise, returns the last Vet's ID incremented by 1.
     *
     * @return The next available Vet ID
     */
    private fun getVetID(): Int {
        if (vets.isEmpty()) {
            return 1
        }
        return vets.last().vetID + 1
    }

    /**
     * Retrieves all vets.
     *
     * @return ArrayList of all vets
     */
    fun getAllVets(): ArrayList<Vet> {
        return vets
    }

    /**
     * Adds a new vet to the collection.
     *
     * @param vet The vet object to be added
     * @return True if the addition is successful, false otherwise
     */
    fun addVet(vet: Vet): Boolean {
        vet.vetID = getVetID()
        return vets.add(vet)
    }

    /**
     * Deletes a vet from the collection by index.
     *
     * @param indexToDelete Index of the vet to delete
     * @return The deleted vet, or null if deletion fails
     */
    fun deleteVet(indexToDelete: Int): Vet? {
        return if (Utilities.isValidListIndex(indexToDelete, vets)) {
            vets.removeAt(indexToDelete)
        } else {
            null
        }
    }

    /**
     * Finds the index of a specific vet in the collection.
     *
     * @param vet The vet object to search for
     * @return Index of the vet if found, -1 otherwise
     */
    fun findVetIndex(vet: Vet): Int {
        return vets.indexOf(vet)
    }

    /**
     * Finds a vet by its ID.
     *
     * @param id The ID of the vet to find
     * @return The found vet or null if not found
     */
    fun findVet(id: Int): Vet? {
        return vets.find { vet -> vet.vetID == id }
    }

    /**
     * Finds a vet by its index.
     *
     * @param id Index of the vet to retrieve
     * @return The vet if found, null otherwise
     */
    fun findVetByIndex(id: Int): Vet? {
        return vets.get(id)
    }

    /**
     * Retrieves the number of vets.
     *
     * @return The number of vets
     */
    fun numberOfVets(): Int = vets.size

    /**
     * Searches for vets by name and returns a formatted string of results.
     *
     * @param searchString The string to search for in vet names
     * @return Formatted string of matched vet information
     */
    fun searchByName(searchString: String) =
        formatListString(
            vets.filter { vet -> vet.name.contains(searchString, ignoreCase = true) }
        )

    /**
     * Updates an existing vet with new information.
     *
     * @param indexToUpdate Index of the vet to update
     * @param vet The new vet object
     * @return True if the update is successful, false otherwise
     */
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

    /**
     * Filters vets based on years of experience and returns the list of qualified vets.
     *
     * @param yearsOfExperience Minimum years of experience for vets to be considered
     * @return List of vets fulfilling the experience criteria
     */
    fun filterVetsByExperience(yearsOfExperience: Int): List<Vet> {
        val currentDate = LocalDate.now()
        return vets.filter { vet ->
            vet.dateQualified.let { dateQualified ->
                val years = Period.between(dateQualified, currentDate).years
                years >= yearsOfExperience
            } // Filter out vets with null dateQualified values
        }
    }

    /**
     * Checks if the given index is valid for the vet collection.
     *
     * @param index Index to check
     * @return True if the index is valid, false otherwise
     */
    fun isValidIndex(index: Int): Boolean {
        return Utilities.isValidListIndex(index, vets)
    }

    /**
     * Assigns a pet to a vet by index and returns the success status.
     *
     * @param index Index of the vet
     * @param pet Pet object to assign
     * @return True if assignment is successful, false otherwise
     */
    fun assignPetToVet(index: Int, pet: Pet): Boolean? {
        println(index)
        println(getAllVets().size)
        if (index >= getAllVets().size.minus(1) || findVetByIndex(index)?.patientList?.filter { it == pet.petID }!!.isNotEmpty()) {
            return false
        }
        return findVetByIndex(index)?.patientList?.add(pet.petID)
    }

    /**
     * Searches for vets by specialisation and returns the list of matching vets.
     *
     * @param specialisation Specialisation to search for
     * @return List of vets with the specified specialisation
     */
    fun searchVetSpecialisation(specialisation: String): List<Vet> {
        return vets.filter { vet ->
            vet.specialisation.contains(specialisation)
        }
    }

    /**
     * Formats a list of vets into a string.
     *
     * @param notesToFormat The list of vets to format
     * @return The formatted string
     */
    fun formatListString(notesToFormat: List<Vet>): String =
        notesToFormat
            .joinToString(separator = "\n") { vet ->
                "" + vets.indexOf(vet).toString() + ": " + vet.toString()
            }

    /**
     * Loads vet data from storage.
     *
     * @throws Exception If there's an issue while loading vet data
     */
    @Throws(Exception::class)
    fun loadVets() {
        vets = serializer.read() as ArrayList<Vet>
    }

    /**
     * Saves vet data to storage.
     *
     * @throws Exception If there's an issue while saving vet data
     */
    @Throws(Exception::class)
    fun saveVets() {
        serializer.write(vets)
    }
}
