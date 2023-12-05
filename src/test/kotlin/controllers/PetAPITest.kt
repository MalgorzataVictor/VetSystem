package controllers

import models.Pet
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import persistence.XMLSerializer
import java.io.File
import java.time.LocalDate
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class PetAPITest {
    private var dog1: Pet? = null
    private var dog2: Pet? = null
    private var cat1: Pet? = null
    private var cat2: Pet? = null
    private var bunny1: Pet? = null
    private var populatedPets: PetAPI? = PetAPI(XMLSerializer(File("pets.xml")))
    private var emptyPets: PetAPI? = PetAPI(XMLSerializer(File("pets.xml")))

    @BeforeEach
    fun setup() {
        dog1 = Pet(1, "Cupcake", "Dog", LocalDate.of(2022, 2, 24), false, 1, "12345a")
        dog2 = Pet(2, "Toothless", "Dog", LocalDate.of(2021, 5, 17), false, 2, "54321b")
        cat1 = Pet(3, "Speedy", "Cat", LocalDate.of(2020, 12, 12), true, 3, "12543c")
        cat2 = Pet(4, "Daisy", "Cat", LocalDate.of(2023, 11, 30), true, 2, "12345d")
        bunny1 = Pet(5, "Stefan", "Bunny", LocalDate.of(2022, 9, 9), false, 4, "54123e")

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
    inner class PersistenceTests {
        @Nested
        inner class XMLPersistence {
            @Test
            fun `saving and loading an empty collection in XML doesn't crash app`() {
                val storingPets = PetAPI(XMLSerializer(File("pets-test.xml")))
                storingPets.savePets()

                val loadedPets = PetAPI(XMLSerializer(File("pets-test.xml")))
                loadedPets.loadPets()

                assertEquals(0, storingPets.numberOfPets())
                assertEquals(0, loadedPets.numberOfPets())
                assertEquals(storingPets.numberOfPets(), loadedPets.numberOfPets())
            }

            @Test
            fun `saving and loading an loaded collection in XML doesn't loose data`() {
                val storingPets = PetAPI(XMLSerializer(File("pets-test.xml")))
                storingPets.addPet(dog1!!)
                storingPets.addPet(cat2!!)
                storingPets.addPet(bunny1!!)
                storingPets.savePets()

                val loadedPets = PetAPI(XMLSerializer(File("pets-test.xml")))
                loadedPets.loadPets()

                assertEquals(3, storingPets.numberOfPets())
                assertEquals(3, loadedPets.numberOfPets())
                assertEquals(storingPets.numberOfPets(), loadedPets.numberOfPets())
                assertEquals(storingPets.findPet(0), loadedPets.findPet(0))
                assertEquals(storingPets.findPet(1), loadedPets.findPet(1))
                assertEquals(storingPets.findPet(2), loadedPets.findPet(2))
            }
        }
    }

    @Nested
    inner class AddPets {

        @Test
        fun `adding a Pet to a populated list adds to ArrayList`() {
            val newPet = Pet(6, "Buba", "Bunny", LocalDate.of(2020, 7, 6), false, 2, "54123e")
            assertEquals(5, populatedPets!!.numberOfPets())
            assertTrue(populatedPets!!.addPet(newPet))
            assertEquals(6, populatedPets!!.numberOfPets())
            assertEquals(newPet, populatedPets!!.findPet(populatedPets!!.numberOfPets()))
        }

        @Test
        fun `adding a Pet to an empty list adds to ArrayList`() {
            val newPet = Pet(6, "Buba", "Bunny", LocalDate.of(2020, 7, 6), false, 2, "54123e")
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
            assertEquals(5, populatedPets!!.numberOfPets())
            assertEquals(bunny1, populatedPets!!.deletePet(4))
            assertEquals(4, populatedPets!!.numberOfPets())
            assertEquals(dog1, populatedPets!!.deletePet(0))
            assertEquals(3, populatedPets!!.numberOfPets())
        }
    }

    @Nested
    inner class SearchByName {

        @Test
        fun `search pets by name returns no pets when no pets with that name exist`() {
            assertEquals(5, populatedPets!!.numberOfPets())
            val searchResults = populatedPets!!.searchByName("no results expected")
            assertTrue(searchResults.isEmpty())

            // Searching an empty collection
            assertEquals(0, emptyPets!!.numberOfPets())
            assertTrue(emptyPets!!.searchByName("").isEmpty())
        }

        @Test
        fun `search notes by title returns notes when notes with that title exist`() {
            assertEquals(5, populatedPets!!.numberOfPets())

            // Searching a populated collection for a full name that exists (case matches exactly)
            var searchResults = populatedPets!!.searchByName("Cupcake")
            assertTrue(searchResults.contains("Cupcake"))
            assertFalse(searchResults.contains("Toothless"))

            // Searching a populated collection for a partial name that exists (case doesn't match)
            searchResults = populatedPets!!.searchByName("cUp")
            assertTrue(searchResults.contains("Cupcake"))
            assertFalse(searchResults.contains("Daisy"))
        }
    }

    @Nested
    inner class GetAllPets {

        @Test
        fun `getAllPets returns all pets`() {
            val expectedPets = listOf(
                dog1!!,
                dog2!!,
                cat1!!,
                cat2!!,
                bunny1!!
            )
            val allPets = populatedPets!!.getAllPets()

            assertEquals(expectedPets, allPets)
        }
    }

    @Nested
    inner class ListAllPets {

        @Test
        fun `listAllPets returns formatted pet list when pets are present`() {
            val expectedFormattedPets =
                "0: PetID: 1, Name: Cupcake, Breed: Dog, DOB: 2022-02-24, Vaccinated: false, VetID: 1, OwnerPPS: 12345a\n" +
                    "1: PetID: 2, Name: Toothless, Breed: Dog, DOB: 2021-05-17, Vaccinated: false, VetID: 2, OwnerPPS: 54321b\n" +
                    "2: PetID: 3, Name: Speedy, Breed: Cat, DOB: 2020-12-12, Vaccinated: true, VetID: 3, OwnerPPS: 12543c\n" +
                    "3: PetID: 4, Name: Daisy, Breed: Cat, DOB: 2023-11-30, Vaccinated: true, VetID: 2, OwnerPPS: 12345d\n" +
                    "4: PetID: 5, Name: Stefan, Breed: Bunny, DOB: 2022-09-09, Vaccinated: false, VetID: 4, OwnerPPS: 54123e"

            val formattedPets = populatedPets!!.listAllPets()

            assertEquals(expectedFormattedPets, formattedPets)
        }

        @Test
        fun `listAllPets returns 'No pets stored' when no pets are present`() {
            val formattedPets = emptyPets!!.listAllPets()

            assertEquals("No pets stored", formattedPets)
        }
    }

    @Nested
    inner class UpdatePets {
        @Test
        fun `updating a pet that does not exist returns false`() {
            assertFalse(
                populatedPets!!.updatePet(
                    6,
                    Pet(6, "Buba", "Bunny", LocalDate.of(2020, 7, 6), false, 2, "54123e")
                )
            )
            assertFalse(
                populatedPets!!.updatePet(
                    -1,
                    Pet(6, "Buba", "Bunny", LocalDate.of(2020, 7, 6), false, 2, "54123e")
                )
            )
            assertFalse(
                emptyPets!!.updatePet(
                    0,
                    Pet(6, "Buba", "Bunny", LocalDate.of(2020, 7, 6), false, 2, "54123e")
                )
            )
            assertFalse(emptyPets!!.updatePet(0, null))
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
                    populatedPets!!.findPetIndex(cat2!!),
                    Pet(4, "Update", "bunny", LocalDate.of(2023, 11, 30), false, 2, "12345a")
                )
            )
            assertEquals("Update", populatedPets!!.findPet(4)!!.name)
            assertEquals("bunny", populatedPets!!.findPet(4)!!.breed)
            assertEquals(false, populatedPets!!.findPet(4)!!.isVaccinated)
            assertFalse(emptyPets!!.updatePet(0, null))
        }

        @Test
        fun `updating a pet with null returns false`() {
            assertFalse(populatedPets!!.updatePet(0, null))
            assertFalse(populatedPets!!.updatePet(-1, null))
            assertFalse(emptyPets!!.updatePet(0, null))
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

    @Nested
    inner class UpdateVaccination {

        @Test
        fun `updating a vaccination in pet that does not exist returns false`() {
            assertFalse(populatedPets!!.updateVaccination(6))
            assertFalse(populatedPets!!.updateVaccination(-1))
            assertFalse(emptyPets!!.updateVaccination(0))
        }

        @Test
        fun `updating a vaccination in pet that already is vaccinated returns false`() {
            assertTrue(populatedPets!!.findPet(3)!!.isVaccinated)
            assertFalse(populatedPets!!.updateVaccination(3))
        }

        @Test
        fun `updating a vaccination in not vaccinated dog that exists returns true and makes it favourite`() {
            assertFalse(populatedPets!!.findPet(1)!!.isVaccinated)
            assertTrue(populatedPets!!.updateVaccination(0))
            assertTrue(populatedPets!!.findPet(1)!!.isVaccinated)
        }
    }

    @Nested
    inner class SortedPets {
        @Nested
        inner class SortedPetsYoungestToOldest {
            @Test
            fun `SortedPetsYoungestToOldest returns No pets stored for an empty list`() {
                val sortedPetsString = emptyPets!!.sortPetsYoungestToOldest()
                assertEquals("❗ No pets stored", sortedPetsString)
            }

            @Test
            fun `sortPetsYoungestToOldest returns sorted notes in descending order`() {
                val sortedPetsString = populatedPets!!.sortPetsYoungestToOldest()
                val expectedNotesOrder = listOf(cat2, bunny1, dog1, dog2, cat1)
                val nonNullableExpectedNotesOrder = expectedNotesOrder.filterNotNull()
                val expectedsortedPetsString = populatedPets!!.formatListString(nonNullableExpectedNotesOrder)

                assertEquals(expectedsortedPetsString, sortedPetsString)
            }
        }

        @Nested
        inner class SortedNotesOldestToNewest {
            @Test
            fun `SortedNotesOldestToNewest returns No notes stored for an empty list`() {
                val sortedPetsString = emptyPets!!.sortPetsOldestToYoungest()
                assertEquals("❗ No pets stored", sortedPetsString)
            }

            @Test
            fun `SortedNotesOldestToNewest returns sorted notes in ascending order`() {
                val sortedPetsString = populatedPets!!.sortPetsOldestToYoungest()
                val expectedNotesOrder = listOf(cat1, dog2, dog1, bunny1, cat2)
                val nonNullableExpectedNotesOrder = expectedNotesOrder.filterNotNull()
                val expectedsortedPetsString = populatedPets!!.formatListString(nonNullableExpectedNotesOrder)

                assertEquals(expectedsortedPetsString, sortedPetsString)
            }
        }
    }
}
