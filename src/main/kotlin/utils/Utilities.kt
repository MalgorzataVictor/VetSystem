package utils

import mu.KotlinLogging
import java.util.*
private val logger = KotlinLogging.logger {}

/**
 * Utility object containing functions for general-purpose operations and validations.
 */
object Utilities {
    // Set of valid positions
    @JvmStatic
    val positions = setOf("Junior", "Senior")

    /**
     * Checks if a given number falls within a specified range.
     *
     * @param numberToCheck The number to be checked
     * @param min The minimum value of the range
     * @param max The maximum value of the range
     * @return True if the number is within the specified range, false otherwise
     */
    @JvmStatic
    fun validRange(numberToCheck: Int, min: Int, max: Int): Boolean {
        return numberToCheck in min..max
    }

    /**
     * Checks if the provided index is valid for a given list.
     *
     * @param index The index to be checked
     * @param list The list to check against
     * @return True if the index is within the list bounds, false otherwise
     */
    @JvmStatic
    fun isValidListIndex(index: Int, list: List<Any>): Boolean {
        return (index >= 0 && index < list.size)
    }

    /**
     * Capitalizes the first letter of a given string.
     *
     * @param input The input string
     * @return The input string with its first letter capitalized
     */
    @JvmStatic
    fun capitalizeFirstLetter(input: String): String {
        return input.ifEmpty { return input }
            .substring(0, 1).uppercase(Locale.getDefault()) + input.substring(1).lowercase(Locale.getDefault())
    }

    /**
     * Checks if a provided position is valid.
     *
     * @param positionToCheck The position to be checked
     * @return True if the position is valid, false otherwise
     */
    @JvmStatic
    fun isValidPosition(positionToCheck: String?): Boolean {
        for (position in positions) {
            if (position.equals(positionToCheck, ignoreCase = true)) {
                return true
            }
        }
        return false
    }

    /**
     * Logs a warning message indicating an invalid option was entered.
     */
    @JvmStatic
    fun loggerWarnFormat() {
        logger.warn { "❗ Invalid option entered" }
    }

    /**
     * Logs an info message indicating a successful process.
     */
    @JvmStatic
    fun loggerInfoSuccessful() {
        logger.info { "✔ Process Successful" }
    }

    /**
     * Logs an info message indicating an unsuccessful process.
     */
    fun loggerInfoUnsuccessful() {
        logger.info { " ❌ Process Unsuccessful" }
    }
}
