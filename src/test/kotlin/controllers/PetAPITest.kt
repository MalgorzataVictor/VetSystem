package controllers

import models.Pet
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import java.time.LocalDate

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
}
