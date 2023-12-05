package utils

import utils.ScannerInput.readNextLine
import java.time.LocalDate

/**
 * Object containing methods for validating various types of user inputs.
 */
object ValidateInput {
    /**
     * Reads and validates a date of birth (DOB) from user input.
     *
     * @param prompt The prompt displayed to the user
     * @return The validated LocalDate object representing the DOB
     */
    @JvmStatic
    fun readValidDOB(prompt: String?): LocalDate {
        print(prompt)
        var input: String?
        var DOB: LocalDate?

        do {
            input = readNextLine(prompt)
            DOB = parseDOB(input)
        } while (DOB == null)
        return DOB
    }

    /**
     * Parses a string representation of a date into a LocalDate object.
     *
     * @param date The date string in the format "YYYY-MM-DD"
     * @return The parsed LocalDate object, or null if parsing fails
     */
    @JvmStatic
    fun parseDOB(date: String): LocalDate? {
        val dobInput = date.split("-")
        try {
            return LocalDate.of(dobInput[0].toInt(), dobInput[1].toInt(), dobInput[2].toInt())
        } catch (e: Exception) {
            return null
        }
    }

    /**
     * Validates a Personal Public Service (PPS) number format.
     *
     * @param pps The PPS number string to be validated
     * @return True if the PPS number is valid, false otherwise
     */
    @JvmStatic
    fun isValidPPS(pps: String): Boolean {
        val regex = """^\d{7}[A-Za-z]{1,2}$""".toRegex()
        return regex.matches(pps)
    }

    /**
     * Validates an email address format.
     *
     * @param email The email address string to be validated
     * @return True if the email address is valid, false otherwise
     */
    @JvmStatic
    fun isEmailValid(email: String): Boolean {
        val containsAtSymbol = email.contains("@")
        val containsDot = email.contains(".")

        return containsAtSymbol && containsDot
    }
}
