package controllers

import models.Owner
import persistence.Serializer
class OwnerAPI(serializerType: Serializer) {
    private var serializer: Serializer = serializerType
    private var owners = ArrayList<Owner>()

    fun addOwner(owner: Owner): Boolean {
        return owners.add(owner)
    }

    fun deleteOwner(indexToDelete: Int) {
        owners.removeIf { owner -> owner.PPS == indexToDelete }
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
