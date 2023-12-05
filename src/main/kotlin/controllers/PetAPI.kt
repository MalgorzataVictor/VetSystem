package controllers

import models.Pet
import persistence.Serializer
import utils.Utilities

/**
 * Manages operations related to the collection of pets.
 */
class PetAPI(serializerType: Serializer) {
    private var serializer: Serializer = serializerType
    private var pets = ArrayList<Pet>()

    /**
     * Retrieves the next available Pet ID in the collection. If the collection is empty,
     * returns the default ID as 1. Otherwise, returns the last Pet's ID incremented by 1.
     *
     * @return The next available Pet ID
     */
    private fun getPetID(): Int {
        if (pets.isEmpty()) {
            return 1
        }
        return pets.last().petID + 1
    }

    /**
     * Retrieves all pets.
     *
     * @return ArrayList of all pets
     */
    fun getAllPets(): ArrayList<Pet> {
        return pets
    }

    /**
     * Lists all pets or a message if there are none stored.
     *
     * @return A string containing either the pets' information or a message if no pets are stored
     */
    fun listAllPets(): String =
        if (pets.isEmpty()) {
            "No pets stored"
        } else {
            formatListString(pets)
        }

    /**
     * Adds a new pet to the collection.
     *
     * @param pet The pet object to be added
     * @return True if the addition is successful, false otherwise
     */
    fun addPet(pet: Pet): Boolean {
        pet.petID = getPetID()
        return pets.add(pet)
    }

    /**
     * Deletes a pet from the collection by index.
     *
     * @param indexToDelete Index of the pet to delete
     * @return The deleted pet, or null if deletion fails
     */
    fun deletePet(indexToDelete: Int): Pet? {
        return if (Utilities.isValidListIndex(indexToDelete, pets)) {
            pets.removeAt(indexToDelete)
        } else {
            null
        }
    }

    /**
     * Finds the index of a specific pet in the collection.
     *
     * @param pet The pet object to search for
     * @return Index of the pet if found, -1 otherwise
     */
    fun findPetIndex(pet: Pet): Int {
        return pets.indexOf(pet)
    }

    /**
     * Finds a pet by its ID.
     *
     * @param id The ID of the pet to find
     * @return The found pet or null if not found
     */
    fun findPet(id: Int): Pet? {
        return pets.find { pet -> pet.petID == id }
    }

    /**
     * Retrieves an owner by their index.
     *
     * @param id Index of the pet to retrieve
     * @return The pet if found, null otherwise
     */
    private fun findPetByIndex(id: Int): Pet {
        return pets.get(id)
    }

    /**
     * Searches for pets by name and returns a formatted string of results.
     *
     * @param searchString The string to search for in pet names
     * @return Formatted string of matched pet information
     */
    fun searchByName(searchString: String) =
        formatListString(
            pets.filter { pet -> pet.name.contains(searchString, ignoreCase = true) }
        )

    /**
     * Updates an existing pet with new information.
     *
     * @param indexToUpdate Index of the pet to update
     * @param pet The new pet object
     * @return True if the update is successful, false otherwise
     */
    fun updatePet(indexToUpdate: Int, pet: Pet?): Boolean {
        if (!Utilities.isValidListIndex(indexToUpdate, pets)) {
            return false
        }
        val foundPet = findPetByIndex(indexToUpdate)

        if (pet != null) {
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

    /**
     * Updates the vaccination status of a pet by index.
     *
     * @param index Index of the pet to update
     * @return True if the vaccination is successful, false otherwise
     */
    fun updateVaccination(index: Int): Boolean {
        if (isValidIndex(index)) {
            val petToVaccinate = pets[index]
            if (!petToVaccinate.isVaccinated) {
                petToVaccinate.isVaccinated = true
                return true
            }
        }
        return false
    }

    /**
     * Sorts pets from youngest to oldest based on date of birth.
     *
     * @return Formatted string of pets sorted by age
     */
    fun sortPetsYoungestToOldest(): String {
        val sortedPets = pets.sortedByDescending { it.DOB }
        return if (sortedPets.isEmpty()) {
            "❗ No pets stored"
        } else {
            formatListString(sortedPets)
        }
    }

    /**
     * Sorts pets from oldest to youngest based on date of birth.
     *
     * @return Formatted string of pets sorted by age
     */
    fun sortPetsOldestToYoungest(): String {
        val sortedPets = pets.sortedBy { it.DOB }
        return if (sortedPets.isEmpty()) {
            "❗ No pets stored"
        } else {
            formatListString(sortedPets)
        }
    }

    /**
     * Checks if the given index is valid for the pet collection.
     *
     * @param index Index to check
     * @return True if the index is valid, false otherwise
     */
    fun isValidIndex(index: Int): Boolean {
        return Utilities.isValidListIndex(index, pets)
    }

    /**
     * Retrieves the number of pets.
     *
     * @return The number of pets
     */
    fun numberOfPets(): Int = pets.size

    /**
     * Formats a list of pets into a string.
     *
     * @param notesToFormat The list of pets to format
     * @return The formatted string
     */
    fun formatListString(notesToFormat: List<Pet>): String =
        notesToFormat
            .joinToString(separator = "\n") { pet ->
                "" + pets.indexOf(pet).toString() + ": " + pet.toString()
            }

    /**
     * Loads pet data from storage.
     *
     * @throws Exception If there's an issue while loading pet data
     */
    @Throws(Exception::class)
    fun loadPets() {
        pets = serializer.read() as ArrayList<Pet>
    }

    /**
     * Saves pet data to storage.
     *
     * @throws Exception If there's an issue while saving pet data
     */
    @Throws(Exception::class)
    fun savePets() {
        serializer.write(pets)
    }
}
