package utils

import utils.ScannerInput.readNextLine
import java.time.LocalDate

object ValidateInput {

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

    @JvmStatic
    fun parseDOB(date: String): LocalDate? {
        val dobInput = date.split("-")
        try {
            return LocalDate.of(dobInput[0].toInt(), dobInput[1].toInt(), dobInput[2].toInt())
        } catch (e: Exception) {
            return null
        }
    }

    @JvmStatic
    private fun isValidDOBFormat(input: String): Boolean {
        val regex = """^\d{4}-\d{2}-\d{2}$""".toRegex()
        return regex.matches(input)
    }

    @JvmStatic
    fun readValidPosition(prompt: String?): String {
        print(prompt)
        var input = readNextLine(prompt)
        do {
            if (Utilities.isValidPosition(input)) {
                return input
            } else {
                print("❗ Invalid position $input.  Please try again: ")
                input = readNextLine(prompt)
            }
        } while (true)
    }

    @JvmStatic
    fun validatePPSInput(prompt: String): String {
        var validPPS = false
        var pps = ""

        do {
            val input = readNextLine(prompt)
            if (isValidPPS(input)) {
                pps = input
                validPPS = true
            } else {
                println("Invalid PPS format. PPS Number should be 7 numbers followed by either one or two letters.")
            }
        } while (!validPPS)

        return pps
    }

    @JvmStatic
    fun isValidPPS(pps: String): Boolean {
        val regex = """^\d{7}[A-Za-z]{1,2}$""".toRegex()
        return regex.matches(pps)
    }

    @JvmStatic
    fun getEmailFromUser(prompt: String): String {
        var email: String
        var isEmailValid: Boolean

        do {
            email = readNextLine(prompt)
            isEmailValid = isEmailValid(email)

            if (!isEmailValid) {
                println("Please enter a valid email address.")
            }
        } while (!isEmailValid)

        return email
    }

    @JvmStatic
    fun isEmailValid(email: String): Boolean {
        val containsAtSymbol = email.contains("@")
        val containsDot = email.contains(".")

        return containsAtSymbol && containsDot
    }
}
