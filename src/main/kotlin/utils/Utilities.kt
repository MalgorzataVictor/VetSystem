package utils

/**
 * Utility object containing functions for general-purpose operations and validations.
 */
object Utilities {

    @JvmStatic
    fun validRange(numberToCheck: Int, min: Int, max: Int): Boolean {
        return numberToCheck in min..max
    }
}
