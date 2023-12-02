package controllers

import models.Owner
import models.Vet

class OwnerAPI {
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
}