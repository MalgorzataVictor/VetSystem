package controllers

import models.Owner
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

class OwnerAPITest {
    private var owner1: Owner? = null
    private var owner2: Owner? = null
    private var owner3: Owner? = null
    private var populatedOwners: OwnerAPI? = OwnerAPI(XMLSerializer(File("owners.xml")))
    private var emptyOwners: OwnerAPI? = OwnerAPI(XMLSerializer(File("owners.xml")))

    @BeforeEach
    fun setup() {
        owner1 = Owner(
            "123456789a",
            "John Doe",
            "+1234567890",
            "john@example.com",
            mutableListOf(1, 2)
        )

        owner2 = Owner(
            "987654321b",
            "Jane Smith",
            "+9876543210",
            "jane@example.com",
            mutableListOf(3)
        )

        owner3 = Owner(
            "246813579c",
            "Alice Johnson",
            "+2468135790",
            "alice@example.com",
            mutableListOf(4, 5)
        )

        populatedOwners!!.addOwner(owner1!!)
        populatedOwners!!.addOwner(owner2!!)
        populatedOwners!!.addOwner(owner3!!)
    }

    @AfterEach
    fun tearDown() {
        owner1 = null
        owner2 = null
        owner3 = null
        populatedOwners = null
        emptyOwners = null
    }

    @Nested
    inner class PersistenceTests {
        @Nested
        inner class XMLPersistence {
            @Test
            fun `saving and loading an empty collection in XML doesn't crash app`() {
                val saveOwners = OwnerAPI(XMLSerializer(File("owners-test.xml")))
                saveOwners.saveOwners()

                val loadedPets = OwnerAPI(XMLSerializer(File("owners-test.xml")))
                loadedPets.loadOwners()

                assertEquals(0, saveOwners.numberOfOwners())
                assertEquals(0, loadedPets.numberOfOwners())
                assertEquals(saveOwners.numberOfOwners(), loadedPets.numberOfOwners())
            }

            @Test
            fun `saving and loading an loaded collection in XML doesn't loose data`() {
                val saveOwners = OwnerAPI(XMLSerializer(File("owners-test.xml")))
                saveOwners.addOwner(owner1!!)
                saveOwners.addOwner(owner2!!)
                saveOwners.addOwner(owner3!!)
                saveOwners.saveOwners()

                val loadedPets = OwnerAPI(XMLSerializer(File("owners-test.xml")))
                loadedPets.loadOwners()

                assertEquals(3, saveOwners.numberOfOwners())
                assertEquals(3, loadedPets.numberOfOwners())
                assertEquals(saveOwners.numberOfOwners(), loadedPets.numberOfOwners())
                assertEquals(saveOwners.findOwner("123456789a"), loadedPets.findOwner("123456789a"))
                assertEquals(saveOwners.findOwner("987654321b"), loadedPets.findOwner("987654321b"))
                assertEquals(saveOwners.findOwner("246813579c"), loadedPets.findOwner("246813579c"))
            }
        }
    }

    @Nested
    inner class AddOwners {

        @Test
        fun `adding a Owner to a populated list adds to ArrayList`() {
            val newOwner = Owner(
                "135792468d",
                "Mark Davis",
                "+1357924680",
                "mark@example.com",
                mutableListOf(6)
            )
            assertEquals(3, populatedOwners!!.numberOfOwners())
            assertTrue(populatedOwners!!.addOwner(newOwner))
            assertEquals(4, populatedOwners!!.numberOfOwners())
            assertEquals(newOwner, populatedOwners!!.findOwner("135792468d"))
        }

        @Test
        fun `adding a Owner to an empty list adds to ArrayList`() {
            val newOwner = Owner(
                "135792468d",
                "Mark Davis",
                "+1357924680",
                "mark@example.com",
                mutableListOf(6)
            )
            assertEquals(0, emptyOwners!!.numberOfOwners())
            assertTrue(emptyOwners!!.addOwner(newOwner))
            assertEquals(1, emptyOwners!!.numberOfOwners())
            assertEquals(newOwner, emptyOwners!!.findOwner(newOwner.PPS))
        }
    }

    @Nested
    inner class DeleteOwners {
        @Test
        fun `deleting a Vet that does not exist, returns null`() {
            val number1 = emptyOwners!!.numberOfOwners()
            val number2 = populatedOwners!!.numberOfOwners()
            emptyOwners!!.deleteOwner(0)
            populatedOwners!!.deleteOwner(-1)
            assertEquals(number1, emptyOwners!!.numberOfOwners())
            assertEquals(number2, populatedOwners!!.numberOfOwners())
        }

        @Test
        fun `deleting a Vet that exists delete and returns deleted object`() {
            assertEquals(3, populatedOwners!!.numberOfOwners())
            assertEquals(owner1, populatedOwners!!.deleteOwner(0))
            assertEquals(2, populatedOwners!!.numberOfOwners())
            assertEquals(owner3, populatedOwners!!.deleteOwner(1))
            assertEquals(1, populatedOwners!!.numberOfOwners())
        }
    }

    @Nested
    inner class GetAllOwners {
        @Test
        fun `getAllOwners returns all owners in the list`() {
            val ownersList = populatedOwners!!.getAllOwners()
            assertEquals(3, ownersList.size)
            assertTrue(ownersList.contains(owner1))
            assertTrue(ownersList.contains(owner2))
            assertTrue(ownersList.contains(owner3))
        }

        @Test
        fun `getAllOwners returns empty list for no owners`() {
            val ownersList = emptyOwners!!.getAllOwners()
            assertEquals(0, ownersList.size)
            assertFalse(ownersList.contains(owner1))
            assertFalse(ownersList.contains(owner2))
            assertFalse(ownersList.contains(owner3))
        }
    }

    @Nested
    inner class UpdateOwners {
        @Test
        fun `updating a owner that does not exist returns false`() {
            assertFalse(
                populatedOwners!!.updateOwner(
                    9,
                    Owner(
                        "135792468d",
                        "Mark Davis",
                        "+1357924680",
                        "mark@example.com",
                        mutableListOf(6)
                    )
                )
            )
            assertFalse(
                populatedOwners!!.updateOwner(
                    -1,
                    Owner(
                        "135792468d",
                        "Mark Davis",
                        "+1357924680",
                        "mark@example.com",
                        mutableListOf(6)
                    )
                )
            )
            assertFalse(
                emptyOwners!!.updateOwner(
                    0,
                    Owner(
                        "135792468d",
                        "Mark Davis",
                        "+1357924680",
                        "mark@example.com",
                        mutableListOf(6)
                    )
                )
            )
        }

        @Test
        fun `updating a owner  that exists returns true and updates`() {
            assertEquals(owner2, populatedOwners!!.findOwner("987654321b"))
            assertEquals("Jane Smith", populatedOwners!!.findOwner("987654321b")!!.name)
            assertEquals("+9876543210", populatedOwners!!.findOwner("987654321b")!!.phoneNumber)
            assertEquals(
                mutableListOf(3),
                populatedOwners!!.findOwner("987654321b")!!.petsList
            )

            assertTrue(
                populatedOwners!!.updateOwner(
                    populatedOwners!!.findOwnerIndex(owner2!!),
                    Owner(
                        "987654321b",
                        "Mark Davis",
                        "+1357924680",
                        "mark@example.com",
                        mutableListOf()
                    )
                )
            )
            assertEquals("Mark Davis", populatedOwners!!.findOwner("987654321b")!!.name)
            assertEquals("+1357924680", populatedOwners!!.findOwner("987654321b")!!.phoneNumber)
        }

        @Test
        fun `updating an owner with null returns false`() {
            assertFalse(populatedOwners!!.updateOwner(0, null))
            assertFalse(populatedOwners!!.updateOwner(-1, null))
            assertFalse(emptyOwners!!.updateOwner(0, null))
        }
    }

    @Nested
    inner class AssignPetToOwner {
        @Test
        fun `assignPetToOwner adds pet to owner's pets list`() {
            val petToAdd = Pet(6, "Buba", "Bunny", LocalDate.of(2020, 7, 6), false, 2, "54123e")
            val result = populatedOwners!!.assignPetToOwner(0, petToAdd)
            assertTrue(result!!)
            assertEquals(3, populatedOwners!!.findOwner("123456789a")!!.petsList.size)
            assertTrue(populatedOwners!!.findOwner("123456789a")!!.petsList.contains(6))
        }

        @Test
        fun `assignPetToOwner returns false if owner is not found`() {
            val petToAdd = Pet(4, "Buba", "Bunny", LocalDate.of(2020, 7, 6), false, 2, "54123e")
            val result: Boolean? = populatedOwners!!.assignPetToOwner(2, petToAdd)
            assertFalse(result ?: false)
        }
    }

    @Nested
    inner class SearchByNameTests {

        @Test
        fun `searchByName returns correct result when search string matches name`() {
            val result = populatedOwners!!.searchByName("Jane")
            assertEquals("1: \uD83D\uDD34 Name: Jane Smith, PPS: 987654321b, Phone No: +9876543210, Email: jane@example.com", result)
        }

        @Test
        fun `searchByName returns empty string when no match found`() {
            val result = populatedOwners!!.searchByName("Unknown")
            assertEquals("", result)
        }

        @Test
        fun `searchByName returns correct result with case-insensitive search`() {
            val result = populatedOwners!!.searchByName("ALICE")
            assertEquals("2: \uD83D\uDD34 Name: Alice Johnson, PPS: 246813579c, Phone No: +2468135790, Email: alice@example.com", result)
        }
    }

    @Nested
    inner class NumberOfOwners {
        @Test
        fun `NumberOfOwners returns the total number of owners in the ArrayList`() {
            assertEquals(3, populatedOwners!!.numberOfOwners())
            assertEquals(0, emptyOwners!!.numberOfOwners())
        }
    }
}
