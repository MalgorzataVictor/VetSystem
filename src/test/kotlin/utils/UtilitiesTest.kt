package utils

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import utils.Utilities.validRange

/**
 * Test class for the Utilities class.
 */
class UtilitiesTest {

    @Nested
    inner class ValidRange {

        /**
         * Test method to validate validRange with positive test data.
         */
        @Test
        fun validRangeWorksWithPositiveTestData() {
            Assertions.assertTrue(validRange(1, 1, 1))
            Assertions.assertTrue(validRange(1, 1, 2))
            Assertions.assertTrue(validRange(1, 0, 1))
            Assertions.assertTrue(validRange(1, 0, 2))
            Assertions.assertTrue(validRange(-1, -2, -1))
        }

        /**
         * Test method to validate validRange with negative test data.
         */
        @Test
        fun validRangeWorksWithNegativeTestData() {
            Assertions.assertFalse(validRange(1, 0, 0))
            Assertions.assertFalse(validRange(1, 1, 0))
            Assertions.assertFalse(validRange(1, 2, 1))
            Assertions.assertFalse(validRange(-1, -1, -2))
        }
    }

    @Nested
    inner class CapitalizeLetters {
        /**
         * Test method to validate capitalizeFirstLetter.
         */
        @Test
        fun capitalizeFirstLetterWorks() {
            Assertions.assertEquals("Kotlin", Utilities.capitalizeFirstLetter("kotlin"))
            Assertions.assertEquals("Kotlin", Utilities.capitalizeFirstLetter("Kotlin"))
            Assertions.assertEquals("Kotlin", Utilities.capitalizeFirstLetter("kOtLiN"))
            Assertions.assertEquals("K", Utilities.capitalizeFirstLetter("k"))
            Assertions.assertEquals("K", Utilities.capitalizeFirstLetter("K"))
            Assertions.assertEquals("", Utilities.capitalizeFirstLetter(""))
        }


    }

    @Nested
    inner class IsValidPosition {

        @Test
        fun `isValidPosition returns true for valid positions`() {
            Assertions.assertTrue(Utilities.isValidPosition("Junior"))
            Assertions.assertTrue(Utilities.isValidPosition("Senior"))
        }

        @Test
        fun `isValidPosition returns false for invalid positions`() {
            Assertions.assertFalse(Utilities.isValidPosition("Manager"))
            Assertions.assertFalse(Utilities.isValidPosition("Intern"))
            Assertions.assertFalse(Utilities.isValidPosition(""))
            Assertions.assertFalse(Utilities.isValidPosition(null))
        }
    }


}