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
    fun isValidPPS(pps: String): Boolean {
        val regex = """^\d{7}[A-Za-z]{1,2}$""".toRegex()
        return regex.matches(pps)
    }

    @JvmStatic
    fun isEmailValid(email: String): Boolean {
        val containsAtSymbol = email.contains("@")
        val containsDot = email.contains(".")

        return containsAtSymbol && containsDot
    }
}
