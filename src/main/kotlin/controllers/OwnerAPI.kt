package controllers

import models.Owner
import models.Pet
import persistence.Serializer
import utils.Utilities

/**
 * Manages operations related to the collection of owners.
 */
class OwnerAPI(serializerType: Serializer) {
    private var serializer: Serializer = serializerType
    private var owners = ArrayList<Owner>()

    /**
     * Retrieves all owners.
     *
     * @return ArrayList of all owners
     */
    fun getAllOwners(): ArrayList<Owner> {
        return owners
    }

    /**
     * Adds a new owner to the collection.
     *
     * @param owner The owner object to be added
     * @return True if the addition is successful, false otherwise
     */
    fun addOwner(owner: Owner): Boolean {
        return owners.add(owner)
    }

    /**
     * Deletes an owner from the collection by index.
     *
     * @param indexToDelete Index of the owner to delete
     * @return The deleted owner, or null if deletion fails
     */
    fun deleteOwner(indexToDelete: Int): Owner? {
        return if (Utilities.isValidListIndex(indexToDelete, owners)) {
            owners.removeAt(indexToDelete)
        } else {
            null
        }
    }

    /**
     * Finds the index of a specific owner in the collection.
     *
     * @param owner The owner object to search for
     * @return Index of the owner if found, -1 otherwise
     */
    fun findOwnerIndex(owner: Owner): Int {
        return owners.indexOf(owner)
    }

    /**
     * Finds an owner by their ID.
     *
     * @param id The ID of the owner to find
     * @return The found owner or null if not found
     */
    fun findOwner(id: String): Owner? {
        return owners.find { owner -> owner.PPS == id }
    }

    /**
     * Retrieves the total number of owners.
     *
     * @return The number of owners
     */
    fun numberOfOwners(): Int = owners.size

    /**
     * Updates an existing owner with new information.
     *
     * @param indexToUpdate Index of the owner to update
     * @param owner The new owner object
     * @return True if the update is successful, false otherwise
     */
    fun updateOwner(indexToUpdate: Int, owner: Owner?): Boolean {
        if (!Utilities.isValidListIndex(indexToUpdate, owners)) {
            return false
        }
        val foundOwner = findOwnerByIndex(indexToUpdate)

        if ((foundOwner != null) && (owner != null)) {
            foundOwner.PPS = owner.PPS
            foundOwner.name = owner.name
            foundOwner.phoneNumber = owner.phoneNumber
            foundOwner.email = owner.email

            return true
        }

        return false
    }

    /**
     * Retrieves an owner by their index.
     *
     * @param id Index of the owner to retrieve
     * @return The owner if found, null otherwise
     */
    fun findOwnerByIndex(id: Int): Owner? {
        return owners.get(id)
    }

    /**
     * Assigns a pet to an owner by index.
     *
     * @param index Index of the owner to assign the pet to
     * @param pet The pet object to assign
     * @return True if the assignment is successful, false otherwise
     */
    fun assignPetToOwner(index: Int, pet: Pet): Boolean? {
        if (findOwnerByIndex(index)?.petsList?.filter { it == pet.petID }?.isNotEmpty()!!) {
            return false
        }
        return findOwnerByIndex(index)?.petsList?.add(pet.petID)
    }

    /**
     * Searches for owners by name and returns a formatted string of results.
     *
     * @param searchString The string to search for in owner names
     * @return Formatted string of matched owner information
     */
    fun searchByName(searchString: String) =
        formatListString(
            owners.filter { owner -> owner.name.contains(searchString, ignoreCase = true) }
        )

    /**
     * Formats a list of owners into a string.
     *
     * @param notesToFormat The list of owners to format
     * @return The formatted string
     */
    fun formatListString(notesToFormat: List<Owner>): String =
        notesToFormat
            .joinToString(separator = "\n") { owner ->
                "" + owners.indexOf(owner).toString() + ": " + owner.toString()
            }

    /**
     * Loads owner data from storage.
     *
     * @throws Exception If there's an issue while loading owner data
     */
    @Throws(Exception::class)
    fun loadOwners() {
        owners = serializer.read() as ArrayList<Owner>
    }

    /**
     * Saves owner data to storage.
     *
     * @throws Exception If there's an issue while saving owner data
     */
    @Throws(Exception::class)
    fun saveOwners() {
        serializer.write(owners)
    }
}
