package models
/**
 * Represents an Owner with associated information.
 *
 * @property PPS Personal Public Service number
 * @property name Owner's name
 * @property phoneNumber Owner's phone number
 * @property email Owner's email address
 * @property petsList List of pets associated with the owner
 * @constructor Creates an Owner with specified properties
 */

data class Owner(
    var PPS: String,
    var name: String,
    var phoneNumber: String,
    var email: String,
    var petsList: MutableList<Int> = mutableListOf()
) {
    /**
     * Provides a string representation of the Owner object.
     *
     * @return String representation of the Owner
     */
    override fun toString(): String {
        return "\uD83D\uDD34 Name: $name, PPS: $PPS, Phone No: $phoneNumber, Email: $email"
    }
}
