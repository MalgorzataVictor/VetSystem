package controllers

import models.Pet
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.time.LocalDate
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue

class PetAPITest {
    private var dog1: Pet? = null
    private var dog2: Pet? = null
    private var cat1: Pet? = null
    private var cat2: Pet? = null
    private var bunny1: Pet? = null
    private var populatedPets: PetAPI? = PetAPI()
    private var emptyPets: PetAPI? = PetAPI()

    @BeforeEach
    fun setup() {
        dog1 = Pet(1, "Cupcake", "Dog", LocalDate.of(2022, 2, 24), false, 1, 12345)
        dog2 = Pet(2, "Toothless", "Dog", LocalDate.of(2021, 5, 17), false, 2, 54321)
        cat1 = Pet(3, "Speedy", "Cat", LocalDate.of(2020, 12, 12), true, 3, 12543)
        cat2 = Pet(4, "Daisy", "Cat", LocalDate.of(2023, 11, 30), true, 2, 12345)
        bunny1 = Pet(5, "Stefan", "Bunny", LocalDate.of(2022, 9, 9), false, 4, 54123)

        populatedPets!!.addPet(dog1!!)
        populatedPets!!.addPet(dog2!!)
        populatedPets!!.addPet(cat1!!)
        populatedPets!!.addPet(cat2!!)
        populatedPets!!.addPet(bunny1!!)
    }

    @AfterEach
    fun tearDown() {
        dog1 = null
        dog2 = null
        cat1 = null
        cat2 = null
        bunny1 = null
        populatedPets = null
        emptyPets = null
    }

    @Nested
    inner class AddPets {

        @Test
        fun `adding a Pet to a populated list adds to ArrayList`() {
            val newPet = Pet(6, "Buba", "Bunny", LocalDate.of(2020, 7, 6), false, 2, 54123)
            assertEquals(5, populatedPets!!.numberOfPets())
            assertTrue(populatedPets!!.addPet(newPet))
            assertEquals(6, populatedPets!!.numberOfPets())
            assertEquals(newPet, populatedPets!!.findPet(populatedPets!!.numberOfPets()))
        }

        @Test
        fun `adding a Pet to an empty list adds to ArrayList`() {
            val newPet = Pet(6, "Buba", "Bunny", LocalDate.of(2020, 7, 6), false, 2, 54123)
            assertEquals(0, emptyPets!!.numberOfPets())
            assertTrue(emptyPets!!.addPet(newPet))
            assertEquals(1, emptyPets!!.numberOfPets())
            assertEquals(newPet, emptyPets!!.findPet(newPet.petID))
        }
    }

    @Nested
    inner class DeletePets {
        @Test
        fun `deleting a Pet that does not exist, returns null`() {
            val number1 = emptyPets!!.numberOfPets()
            val number2 = populatedPets!!.numberOfPets()
            emptyPets!!.deletePet(0)
            populatedPets!!.deletePet(-1)
            assertEquals(number1, emptyPets!!.numberOfPets())
            assertEquals(number2, populatedPets!!.numberOfPets())
        }

        @Test
        fun `deleting a Pet that exists delete and returns deleted object`() {
            assertNotNull(populatedPets!!.findPet(3))
            val number1 = populatedPets!!.numberOfPets()
            populatedPets!!.deletePet(3)
            assertEquals(number1 - 1, populatedPets!!.numberOfPets())
            assertNull(populatedPets!!.findPet(3))
        }
    }

    @Nested
    inner class UpdatePets {
        @Test
        fun `updating a pet that does not exist returns false`() {
            assertFalse(
                populatedPets!!.updatePet(
                    6,
                    Pet(6, "Buba", "Bunny", LocalDate.of(2020, 7, 6), false, 2, 54123)
                )
            )
            assertFalse(
                populatedPets!!.updatePet(
                    -1,
                    Pet(6, "Buba", "Bunny", LocalDate.of(2020, 7, 6), false, 2, 54123)
                )
            )
            assertFalse(
                emptyPets!!.updatePet(
                    0,
                    Pet(6, "Buba", "Bunny", LocalDate.of(2020, 7, 6), false, 2, 54123)
                )
            )
        }

        @Test
        fun `updating a pet that exists returns true and updates`() {
            assertEquals(cat2, populatedPets!!.findPet(4))
            assertEquals("Daisy", populatedPets!!.findPet(4)!!.name)
            assertEquals(4, populatedPets!!.findPet(4)!!.petID)
            assertEquals("Cat", populatedPets!!.findPet(4)!!.breed)
            assertEquals(2, populatedPets!!.findPet(4)!!.vetID)
            assertEquals(true, populatedPets!!.findPet(4)!!.isVaccinated)

            assertTrue(
                populatedPets!!.updatePet(
                    4,
                    Pet(4, "Update", "bunny", LocalDate.of(2023, 11, 30), false, 2, 12345)
                )
            )
            assertEquals("Update", populatedPets!!.findPet(4)!!.name)
            assertEquals("bunny", populatedPets!!.findPet(4)!!.breed)
            assertEquals(false, populatedPets!!.findPet(4)!!.isVaccinated)
        }
    }

    @Nested
    inner class NumberOfPets {
        @Test
        fun `numberOfPets returns the total number of pets in the ArrayList`() {
            assertEquals(5, populatedPets!!.numberOfPets())
            assertEquals(0, emptyPets!!.numberOfPets())
        }
    }
}
