import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import utils.ValidateInput
import java.io.ByteArrayInputStream
import java.time.LocalDate
import kotlin.test.assertNull

class ValidateInputTest {

    @Test
    fun `readValidDOB - returns valid date of birth`() {
        val input = "1990-01-01"
        System.setIn(ByteArrayInputStream(input.toByteArray()))
        val dob = ValidateInput.readValidDOB("Enter your date of birth: ")
        assertEquals(LocalDate.of(1990, 1, 1), dob)
        System.setIn(System.`in`)
    }

    @Test
    fun `readValidDOB - prompts until valid date is entered`() {
        val invalidInputs = listOf("invalid", "2000-13-45", "2022-02-30")
        val validInput = "1990-01-01"

        for (input in invalidInputs) {
          assertNull(ValidateInput.parseDOB(input))
        }

        val dob = ValidateInput.parseDOB(validInput)
        assertEquals(LocalDate.of(1990, 1, 1), dob)


    }

    @Test
    fun `isValidPPS - returns true for valid PPS`() {
        val validPPS = "1234567AB"
        val isValid = ValidateInput.isValidPPS(validPPS)
        assertTrue(isValid)
    }

    @Test
    fun `isValidPPS - returns false for invalid PPS`() {
        val invalidPPS = "1234567"
        val isValid = ValidateInput.isValidPPS(invalidPPS)
        assertFalse(isValid)
    }

    @Test
    fun `isEmailValid - returns true for valid email`() {
        val validEmail = "example@example.com"
        val isValid = ValidateInput.isEmailValid(validEmail)
        assertTrue(isValid)
    }

    @Test
    fun `isEmailValid - returns false for invalid email`() {
        val invalidEmail = "example.com"
        val isValid = ValidateInput.isEmailValid(invalidEmail)
        assertFalse(isValid)
    }
}
