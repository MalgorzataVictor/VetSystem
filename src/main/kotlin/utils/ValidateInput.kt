package utils

import java.time.LocalDate
import java.util.Scanner

object ValidateInput {

    @JvmStatic
    fun readValidDOB(prompt: String?): LocalDate {
        print(prompt)
        var input = Scanner(System.`in`).nextLine()
        do {
            if (isValidDOBFormat(input)) {
                val dobInput = input.split("-")
                try {
                    val DOB = LocalDate.of(dobInput[0].toInt(), dobInput[1].toInt(), dobInput[2].toInt())
                    return DOB
                } catch (e: Exception) {
                    print("❗ Invalid date format or values. Please try again: ")
                    input = Scanner(System.`in`).nextLine()
                }
            } else {
                print("❗ Invalid date format. Please enter DOB in YYYY-MM-DD format: ")
                input = Scanner(System.`in`).nextLine()
            }
        } while (true)
    }

    private fun isValidDOBFormat(input: String): Boolean {
        val regex = """^\d{4}-\d{2}-\d{2}$""".toRegex()
        return regex.matches(input)
    }

    @JvmStatic
    fun readValidPosition(prompt: String?): String {
        print(prompt)
        var input = Scanner(System.`in`).nextLine()
        do {
            if (Utilities.isValidPosition(input))
                return input
            else {
                print("❗ Invalid position $input.  Please try again: ")
                input = Scanner(System.`in`).nextLine()
            }
        } while (true)
    }
}
