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

}