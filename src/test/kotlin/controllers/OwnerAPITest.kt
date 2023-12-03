package controllers

import models.Owner
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import persistence.XMLSerializer
import java.io.File
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
            123456789,
            "John Doe",
            "+1234567890",
            "john@example.com",
            mutableListOf(1, 2)
        )

        owner2 = Owner(
            987654321,
            "Jane Smith",
            "+9876543210",
            "jane@example.com",
            mutableListOf(3)
        )

        owner3 = Owner(
            246813579,
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
                assertEquals(saveOwners.findOwner(0), loadedPets.findOwner(0))
                assertEquals(saveOwners.findOwner(1), loadedPets.findOwner(1))
                assertEquals(saveOwners.findOwner(2), loadedPets.findOwner(2))
            }
        }
    }

    @Nested
    inner class AddOwners {

        @Test
        fun `adding a Owner to a populated list adds to ArrayList`() {
            val newOwner = Owner(
                135792468,
                "Mark Davis",
                "+1357924680",
                "mark@example.com",
                mutableListOf(6)
            )
            assertEquals(3, populatedOwners!!.numberOfOwners())
            assertTrue(populatedOwners!!.addOwner(newOwner))
            assertEquals(4, populatedOwners!!.numberOfOwners())
            assertEquals(newOwner, populatedOwners!!.findOwner(135792468))
        }

        @Test
        fun `adding a Owner to an empty list adds to ArrayList`() {
            val newOwner = Owner(
                135792468,
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
    inner class UpdateOwners {
        @Test
        fun `updating a owner that does not exist returns false`() {
            assertFalse(
                populatedOwners!!.updateOwner(
                    9,
                    Owner(
                        135792468,
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
                        135792468,
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
                        135792468,
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
            assertEquals(owner2, populatedOwners!!.findOwner(987654321))
            assertEquals("Jane Smith", populatedOwners!!.findOwner(987654321)!!.name)
            assertEquals("+9876543210", populatedOwners!!.findOwner(987654321)!!.phoneNumber)
            assertEquals(
                mutableListOf(3),
                populatedOwners!!.findOwner(987654321)!!.petsList
            )

            assertTrue(
                populatedOwners!!.updateOwner(
                    populatedOwners!!.findOwnerIndex(owner2!!),
                    Owner(
                        987654321,
                        "Mark Davis",
                        "+1357924680",
                        "mark@example.com",
                        mutableListOf()
                    )
                )
            )
            assertEquals("Mark Davis", populatedOwners!!.findOwner(987654321)!!.name)
            assertEquals("+1357924680", populatedOwners!!.findOwner(987654321)!!.phoneNumber)
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
