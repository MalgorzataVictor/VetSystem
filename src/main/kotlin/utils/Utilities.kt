package utils

import mu.KotlinLogging
import java.util.*
private val logger = KotlinLogging.logger {}

/**
 * Utility object containing functions for general-purpose operations and validations.
 */
object Utilities {

    @JvmStatic
    fun validRange(numberToCheck: Int, min: Int, max: Int): Boolean {
        return numberToCheck in min..max
    }

    @JvmStatic
    fun isValidListIndex(index: Int, list: List<Any>): Boolean {
        return (index >= 0 && index < list.size)
    }

    @JvmStatic
    fun capitalizeFirstLetter(input: String): String {
        return input.ifEmpty { return input }
            .substring(0, 1).uppercase(Locale.getDefault()) + input.substring(1).lowercase(Locale.getDefault())
    }

    @JvmStatic
    fun logggerWarnFormat() {
        logger.warn { "❗ Invalid option entered" }
    }

    @JvmStatic
    fun loggerInfoSuccessful() {
        logger.info { "✔ Process Successful" }
    }
}
