package controllers

import models.Owner
import models.Vet

class OwnerAPI {
    private var owners = ArrayList<Owner>()

    fun addOwner(owner: Owner): Boolean {
        return owners.add(owner)
    }
}