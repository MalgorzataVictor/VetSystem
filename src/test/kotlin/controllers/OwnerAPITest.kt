package controllers

import models.Owner
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

class OwnerAPITest {
    private var owner1: Owner? = null
    private var owner2: Owner? = null
    private var owner3: Owner? = null
    private var populatedOwners: OwnerAPI? = OwnerAPI()
    private var emptyOwners: OwnerAPI? = OwnerAPI()

    @BeforeEach
    fun setup() {
        val owner1 = Owner(
            123456789,
            "John Doe",
            "+1234567890",
            "john@example.com",
            mutableListOf(
                Pet(1, "Rex", "Dog", LocalDate.of(2019, 5, 12), false, 1, 12345),
                Pet(2, "Whiskers", "Cat", LocalDate.of(2020, 8, 20), true, 2, 54321)
            )
        )

        val owner2 = Owner(
            987654321,
            "Jane Smith",
            "+9876543210",
            "jane@example.com",
            mutableListOf(
                Pet(3, "Max", "Dog", LocalDate.of(2018, 3, 5), false, 1, 67890)
            )
        )

        val owner3 = Owner(
            246813579,
            "Alice Johnson",
            "+2468135790",
            "alice@example.com",
            mutableListOf(
                Pet(4, "Luna", "Cat", LocalDate.of(2021, 12, 10), true, 2, 13579),
                Pet(5, "Buddy", "Dog", LocalDate.of(2022, 6, 18), false, 3, 24680)
            )
        )

        populatedOwners!!.addOwner(owner1)
        populatedOwners!!.addOwner(owner2)
        populatedOwners!!.addOwner(owner3)
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
    inner class AddOwners {

        @Test
        fun `adding a Owner to a populated list adds to ArrayList`() {
            val newOwner = Owner(
                135792468,
                "Mark Davis",
                "+1357924680",
                "mark@example.com",
                mutableListOf(
                    Pet(6, "Coco", "Dog", LocalDate.of(2023, 3, 8), true, 4, 97531)
                )
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
                mutableListOf(
                    Pet(6, "Coco", "Dog", LocalDate.of(2023, 3, 8), true, 4, 97531)
                )
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
            assertNotNull(populatedOwners!!.findOwner(987654321))
            val number1 = populatedOwners!!.numberOfOwners()
            populatedOwners!!.deleteOwner(987654321)
            assertEquals(number1 - 1, populatedOwners!!.numberOfOwners())
            assertNull(populatedOwners!!.findOwner(987654321))
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
                        mutableListOf(
                            Pet(6, "Coco", "Dog", LocalDate.of(2023, 3, 8), true, 4, 97531)
                        )
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
                        mutableListOf(
                            Pet(6, "Coco", "Dog", LocalDate.of(2023, 3, 8), true, 4, 97531)
                        )
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
                        mutableListOf(
                            Pet(6, "Coco", "Dog", LocalDate.of(2023, 3, 8), true, 4, 97531)
                        )
                    )
                )
            )
        }

/* @Test
        fun `updating a owner  that exists returns true and updates`() {
            assertEquals(owner1, populatedOwners!!.findOwner(123456789,))
            assertEquals("Jane Smith", populatedOwners!!.findOwner(987654321,)!!.name)
             assertEquals("+9876543210", populatedOwners!!.findOwner(987654321)!!.phoneNumber)
             assertEquals(mutableListOf(
                 Pet(3, "Max", "Dog", LocalDate.of(2018, 3, 5), false, 1, 67890)
             ), populatedOwners!!.findOwner(987654321,)!!.petsList)


             assertTrue(
                 populatedOwners!!.updateOwner(
                     4,
                     Owner(
                         987654321,
                         "Mark Davis",
                         "+1357924680",
                         "mark@example.com",
                         mutableListOf(
                             Pet(6, "Coco", "Dog", LocalDate.of(2023, 3, 8), true, 4, 97531)
                         )
                     )
                 )
             )
             assertEquals("Mark Davis", populatedOwners!!.findOwner(987654321)!!.name)
             assertEquals("+1357924680", populatedOwners!!.findOwner(987654321)!!.phoneNumber)
             assertEquals(mutableListOf(
                 Pet(6, "Coco", "Dog", LocalDate.of(2023, 3, 8), true, 4, 97531)
             ), populatedOwners!!.findOwner(987654321)!!.petsList)
        }*/
    }
}
