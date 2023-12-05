package controllers

import models.Pet
import models.Vet
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import persistence.XMLSerializer
import java.io.File
import java.time.LocalDate
import java.time.Period
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class VetAPITest {
    private var vet1: Vet? = null
    private var vet2: Vet? = null
    private var vet3: Vet? = null
    private var vet4: Vet? = null
    private var populatedVets: VetAPI? = VetAPI(XMLSerializer(File("vets.xml")))
    private var emptyVets: VetAPI? = VetAPI(XMLSerializer(File("vets.xml")))

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
            mutableListOf(1) // Patient list with one pet
        )
        vet3 = Vet(
            3,
            "Dr. Brown",
            LocalDate.of(2019, 9, 20),
            mutableListOf("General Checkups"), // Specializations with some values
            70000.0,
            "Senior",
            mutableListOf(2) // Patient list with one pet
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
    inner class PersistenceTests {
        @Nested
        inner class XMLPersistence {
            @Test
            fun `saving and loading an empty collection in XML doesn't crash app`() {
                val storingVets = VetAPI(XMLSerializer(File("vets-test.xml")))
                storingVets.saveVets()

                val loadedPets = VetAPI(XMLSerializer(File("vets-test.xml")))
                loadedPets.loadVets()

                assertEquals(0, storingVets.numberOfVets())
                assertEquals(0, loadedPets.numberOfVets())
                assertEquals(storingVets.numberOfVets(), loadedPets.numberOfVets())
            }

            @Test
            fun `saving and loading an loaded collection in XML doesn't loose data`() {
                val storingVets = VetAPI(XMLSerializer(File("vets-test.xml")))
                storingVets.addVet(vet1!!)
                storingVets.addVet(vet2!!)
                storingVets.addVet(vet3!!)
                storingVets.saveVets()

                val loadedPets = VetAPI(XMLSerializer(File("vets-test.xml")))
                loadedPets.loadVets()

                assertEquals(3, storingVets.numberOfVets())
                assertEquals(3, loadedPets.numberOfVets())
                assertEquals(storingVets.numberOfVets(), loadedPets.numberOfVets())
                assertEquals(storingVets.findVet(0), loadedPets.findVet(0))
                assertEquals(storingVets.findVet(1), loadedPets.findVet(1))
                assertEquals(storingVets.findVet(2), loadedPets.findVet(2))
            }
        }
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
            assertEquals(newVet, populatedVets!!.findVet(5))
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
    inner class DeleteVets {
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
            assertEquals(4, populatedVets!!.numberOfVets())
            assertEquals(vet1, populatedVets!!.deleteVet(0))
            assertEquals(3, populatedVets!!.numberOfVets())
            assertEquals(vet4, populatedVets!!.deleteVet(2))
            assertEquals(2, populatedVets!!.numberOfVets())
        }
    }

    @Nested
    inner class UpdateVets {
        @Test
        fun `updating a vet that does not exist returns false`() {
            assertFalse(
                populatedVets!!.updateVet(
                    9,
                    Vet(
                        6,
                        "Lee Chan",
                        LocalDate.of(2022, 7, 1),
                        mutableListOf("Orthopedics", "Cardiology"), // Specializations with some values
                        7634.0,
                        "Junior",
                        mutableListOf() // Empty patient list
                    )
                )
            )
            assertFalse(
                populatedVets!!.updateVet(
                    -1,
                    Vet(
                        6,
                        "Lee Chan",
                        LocalDate.of(2022, 7, 1),
                        mutableListOf("Orthopedics", "Cardiology"), // Specializations with some values
                        7634.0,
                        "Junior",
                        mutableListOf() // Empty patient list
                    )
                )
            )
            assertFalse(
                emptyVets!!.updateVet(
                    0,
                    Vet(
                        6,
                        "Lee Chan",
                        LocalDate.of(2022, 7, 1),
                        mutableListOf("Orthopedics", "Cardiology"), // Specializations with some values
                        7634.0,
                        "Junior",
                        mutableListOf() // Empty patient list
                    )
                )
            )
        }

        @Test
        fun `updating a vet  that exists returns true and updates`() {
            assertEquals(vet2, populatedVets!!.findVet(2))
            assertEquals("Dr. Johnson", populatedVets!!.findVet(2)!!.name)
            assertEquals(LocalDate.of(2020, 5, 10), populatedVets!!.findVet(2)!!.dateQualified)
            assertEquals(75000.0, populatedVets!!.findVet(2)!!.salary)
            assertEquals(mutableListOf(1), populatedVets!!.findVet(2)!!.patientList)
            assertEquals("Junior", populatedVets!!.findVet(2)!!.position)

            assertTrue(
                populatedVets!!.updateVet(
                    populatedVets!!.findVetIndex(vet4!!),
                    Vet(
                        2,
                        "Dr. Tina",
                        LocalDate.of(1999, 5, 10),
                        mutableListOf(),
                        90000.0,
                        "Senior",
                        mutableListOf(0) // Patient list with one pet
                    )
                )
            )
            assertEquals("Dr. Tina", populatedVets!!.findVet(4)!!.name)
            assertEquals(90000.0, populatedVets!!.findVet(4)!!.salary)
            assertEquals("Senior", populatedVets!!.findVet(4)!!.position)
        }

        @Test
        fun `updating a vet with null returns false`() {
            assertFalse(populatedVets!!.updateVet(0, null))
            assertFalse(populatedVets!!.updateVet(-1, null))
            assertFalse(emptyVets!!.updateVet(0, null))
        }
    }

    @Nested
    inner class NumberOfVets {
        @Test
        fun `numberOfVets returns the total number of vets in the ArrayList`() {
            assertEquals(4, populatedVets!!.numberOfVets())
            assertEquals(0, emptyVets!!.numberOfVets())
        }
    }

    @Nested
    inner class FilterVetsByExperience {
        @Test
        fun `filter vets by experience - should return vets with at least 2 years of experience`() {
            val filteredVets = populatedVets!!.filterVetsByExperience(2)
            val currentDate = LocalDate.now()

            val expectedVets = listOf(
                vet1,
                vet2,
                vet3,
                vet4
            ).filter {
                val years = Period.between(it!!.dateQualified, currentDate).years
                years >= 2
            }

            println("Expected vets: $expectedVets")
            println("Filtered vets: $filteredVets")

            assertEquals(expectedVets.size, filteredVets.size)
            assertEquals(expectedVets, filteredVets)
        }

        @Test
        fun `filter vets by experience - should return vets with at least 1 year of experience`() {
            val filteredVets = populatedVets!!.filterVetsByExperience(1)
            val currentDate = LocalDate.now()

            val expectedVets = listOf(
                vet1,
                vet2,
                vet3,
                vet4
            ).filter {
                val years = Period.between(it!!.dateQualified, currentDate).years
                years >= 1
            }

            assertEquals(expectedVets.size, filteredVets.size)
            assertEquals(expectedVets, filteredVets)
        }

        @Test
        fun `filter vets by experience - should return empty list if no vets have 5 years of experience`() {
            val filteredVets = populatedVets!!.filterVetsByExperience(5)
            val currentDate = LocalDate.now()

            val expectedVets = listOf(
                vet1,
                vet2,
                vet3,
                vet4
            ).filter {
                val years = Period.between(it!!.dateQualified, currentDate).years
                years >= 5
            }

            assertEquals(expectedVets.size, filteredVets.size)
            assertEquals(expectedVets, filteredVets)
        }
    }

    @Nested
    inner class GetAllVets {
        @Test
        fun `getAllVets returns all vets in the list`() {
            val vetsList = populatedVets!!.getAllVets()
            assertEquals(4, vetsList.size) // Assuming there are 4 vets in the populatedVets
            assertTrue(vetsList.contains(vet1))
            assertTrue(vetsList.contains(vet2))
            assertTrue(vetsList.contains(vet3))
            assertTrue(vetsList.contains(vet4))
        }

        @Test
        fun `getAllVets returns empty list for no vets`() {
            val emptyVetsList = emptyVets!!.getAllVets()
            assertEquals(0, emptyVetsList.size)
            assertFalse(emptyVetsList.contains(vet1))
            assertFalse(emptyVetsList.contains(vet2))
            assertFalse(emptyVetsList.contains(vet3))
            assertFalse(emptyVetsList.contains(vet4))
        }
    }

    @Nested
    inner class IsValidIndex {
        @Test
        fun `isValidIndex with valid index returns true`() {
            assertTrue(populatedVets!!.isValidIndex(0))
            assertTrue(populatedVets!!.isValidIndex(2))
            assertTrue(populatedVets!!.isValidIndex(3))
        }

        @Test
        fun `isValidIndex with negative index returns false`() {
            assertFalse(populatedVets!!.isValidIndex(-1))
            assertFalse(populatedVets!!.isValidIndex(-5))
        }

        @Test
        fun `isValidIndex with index beyond list size returns false`() {
            assertFalse(populatedVets!!.isValidIndex(6))
            assertFalse(populatedVets!!.isValidIndex(10))
        }
    }

    @Nested
    inner class SearchVetSpecialisationTests {

        @Test
        fun `searchVetSpecialisation - should return vets with given specialisation`() {
            val searchedVets: List<Vet?>? = populatedVets?.searchVetSpecialisation("Surgery")

            val expectedVets = listOf(
                vet1
            )

            assertEquals(expectedVets, searchedVets)
            assertTrue(searchedVets?.all { vet -> vet?.specialisation?.contains("Surgery") == true } ?: false)
        }

        @Test
        fun `searchVetSpecialisation - should return empty list if no vets with given specialisation`() {
            val searchedVets: List<Vet?>? = populatedVets?.searchVetSpecialisation("Oncology")

            val expectedVets = emptyList<Vet>()

            assertEquals(expectedVets, searchedVets)
        }
    }

    @Nested
    inner class SearchByNameTests {

        @Test
        fun `searchByName returns correct result when search string matches name`() {
            val result = populatedVets!!.searchByName("Dr. Smith")
            assertTrue(result.contains("Dr. Smith"))
        }

        @Test
        fun `searchByName returns empty string when no match found`() {
            val result = populatedVets!!.searchByName("Unknown")
            assertEquals("", result)
        }

        @Test
        fun `searchByName returns correct result with case-insensitive search`() {
            val result = populatedVets!!.searchByName("DR. JOHNSON")
            assertTrue(result.contains("Dr. Johnson"))
        }
    }

    @Nested
    inner class AssignPetToVet {
        @Test
        fun `assignPetToVet adds pet to vets's pets list`() {
            val petToAdd = Pet(6, "Buba", "Bunny", LocalDate.of(2020, 7, 6), false, 2, "54123e")
            val result = populatedVets!!.assignPetToVet(0, petToAdd)
            assertTrue(result!!)
            assertEquals(1, populatedVets!!.findVet(1)!!.patientList.size)
            assertTrue(populatedVets!!.findVet(1)!!.patientList.contains(6))
        }

        @Test
        fun `assignPetToVet returns false if vet is not found`() {
            val petToAdd = Pet(4, "Buba", "Bunny", LocalDate.of(2020, 7, 6), false, 2, "54123e")
            val result: Boolean? = populatedVets!!.assignPetToVet(6, petToAdd)
            assertFalse(result ?: false)
        }
    }
}
