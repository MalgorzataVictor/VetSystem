package controllers

import models.Owner
import models.Pet
import persistence.Serializer
import utils.Utilities

class OwnerAPI(serializerType: Serializer) {
    private var serializer: Serializer = serializerType
    private var owners = ArrayList<Owner>()

    fun listAllOwners(): String =
        if (owners.isEmpty()) {
            "No owners stored"
        } else {
            formatListString(owners)
        }

    fun addOwner(owner: Owner): Boolean {
        return owners.add(owner)
    }

    fun deleteOwner(indexToDelete: Int): Owner? {
        return if (Utilities.isValidListIndex(indexToDelete, owners)) {
            owners.removeAt(indexToDelete)
        } else {
            null
        }
    }

    fun findOwner(id: Int): Owner? {
        return owners.find { owner -> owner.PPS == id }
    }

    fun numberOfOwners(): Int = owners.size

    fun updateOwner(indexToUpdate: Int, owner: Owner?): Boolean {
        val foundOwner = findOwner(indexToUpdate)

        if ((foundOwner != null) && (owner != null)) {
            foundOwner.PPS = owner.PPS
            foundOwner.name = owner.name
            foundOwner.phoneNumber = owner.phoneNumber
            foundOwner.email = owner.email
            foundOwner.petsList = owner.petsList

            return true
        }

        return false
    }

    fun findOwnerByIndex(id: Int): Owner? {
        return owners.get(id)
    }

    fun isValidIndex(index: Int): Boolean {
        return Utilities.isValidListIndex(index, owners)
    }
    fun assignPetToOwner(index: Int, pet: Pet): Boolean? {
        return findOwnerByIndex(index)?.petsList?.add(pet)
    }

    fun unAssignPetFromOwner(oldIndex: Int, newIndex: Int, pet: Pet) {
        findOwnerByIndex(oldIndex)?.petsList?.remove(pet)
        findOwnerByIndex(newIndex)?.petsList?.add(pet)
    }

    fun searchByName(searchString: String) =
        formatListString(
            owners.filter { owner -> owner.name.contains(searchString, ignoreCase = true) }
        )

    fun formatListString(notesToFormat: List<Owner>): String =
        notesToFormat
            .joinToString(separator = "\n") { owner ->
                "        📌 " + owners.indexOf(owner).toString() + ": " + owner.toString()
            }

    @Throws(Exception::class)
    fun loadOwners() {
        owners = serializer.read() as ArrayList<Owner>
    }

    @Throws(Exception::class)
    fun saveOwners() {
        serializer.write(owners)
    }
}
