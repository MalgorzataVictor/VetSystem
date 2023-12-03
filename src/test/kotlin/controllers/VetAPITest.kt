package controllers

import models.Pet
import models.Vet
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.time.LocalDate
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue

class VetAPITest {
    private var vet1: Vet? = null
    private var vet2: Vet? = null
    private var vet3: Vet? = null
    private var vet4: Vet? = null
    private var populatedVets: VetAPI? = VetAPI()
    private var emptyVets: VetAPI? = VetAPI()

    @BeforeEach
    fun setup() {
        vet1 = Vet(
            1,
            "Dr. Smith",
            LocalDate.of(2018, 7, 15),
            mutableListOf("Surgery", "Dentistry"), // Specializations with some values
            80000.0,
            "Senior",
            mutableListOf() // Empty patient list
        )
        vet2 = Vet(
            2,
            "Dr. Johnson",
            LocalDate.of(2020, 5, 10),
            mutableListOf(), // Empty specializations list
            75000.0,
            "Junior",
            mutableListOf(Pet(1, "PetName", "PetType", LocalDate.of(2022, 2, 2), false, 1, 12345)) // Patient list with one pet
        )
        vet3 = Vet(
            3,
            "Dr. Brown",
            LocalDate.of(2019, 9, 20),
            mutableListOf("General Checkups"), // Specializations with some values
            70000.0,
            "Senior",
            mutableListOf(Pet(2, "AnotherPet", "AnotherType", LocalDate.of(2023, 3, 3), true, 2, 54321)) // Patient list with one pet
        )
        vet4 = Vet(
            4,
            "Dr. Lee",
            LocalDate.of(2021, 3, 8),
            mutableListOf("Orthopedics", "Neurology"), // Specializations with some values
            85000.0,
            "Junior",
            mutableListOf() // Empty patient list
        )

        populatedVets!!.addVet(vet1!!)
        populatedVets!!.addVet(vet2!!)
        populatedVets!!.addVet(vet3!!)
        populatedVets!!.addVet(vet4!!)
    }

    @AfterEach
    fun tearDown() {
        vet1 = null
        vet2 = null
        vet3 = null
        vet4 = null
        populatedVets = null
        emptyVets = null
    }

    @Nested
    inner class AddVets {

        @Test
        fun `adding a Vet to a populated list adds to ArrayList`() {
            val newVet = Vet(
                6,
                "Lee Chan",
                LocalDate.of(2022, 7, 1),
                mutableListOf("Orthopedics", "Cardiology"), // Specializations with some values
                7634.0,
                "Junior",
                mutableListOf() // Empty patient list
            )
            assertEquals(4, populatedVets!!.numberOfVets())
            assertTrue(populatedVets!!.addVet(newVet))
            assertEquals(5, populatedVets!!.numberOfVets())
            assertEquals(newVet, populatedVets!!.findVet(6))
        }

        @Test
        fun `adding a Vet to an empty list adds to ArrayList`() {
            val newVet = Vet(
                6,
                "Lee Chan",
                LocalDate.of(2022, 7, 1),
                mutableListOf("Orthopedics", "Cardiology"), // Specializations with some values
                7634.0,
                "Junior",
                mutableListOf() // Empty patient list
            )
            assertEquals(0, emptyVets!!.numberOfVets())
            assertTrue(emptyVets!!.addVet(newVet))
            assertEquals(1, emptyVets!!.numberOfVets())
            assertEquals(newVet, emptyVets!!.findVet(newVet.vetID))
        }
    }

    @Nested
    inner class deleteVets {
        @Test
        fun `deleting a Vet that does not exist, returns null`() {
            val number1 = emptyVets!!.numberOfVets()
            val number2 = populatedVets!!.numberOfVets()
            emptyVets!!.deleteVet(0)
            populatedVets!!.deleteVet(-1)
            assertEquals(number1, emptyVets!!.numberOfVets())
            assertEquals(number2, populatedVets!!.numberOfVets())
        }

        @Test
        fun `deleting a Vet that exists delete and returns deleted object`() {
            assertNotNull(populatedVets!!.findVet(3))
            val number1 = populatedVets!!.numberOfVets()
            populatedVets!!.deleteVet(3)
            assertEquals(number1 - 1, populatedVets!!.numberOfVets())
            assertNull(populatedVets!!.findVet(3))
        }
    }
}
