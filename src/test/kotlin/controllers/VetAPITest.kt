package controllers

import models.Pet
import models.Vet
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import java.time.LocalDate
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
            "Surgeon",
            mutableListOf() // Empty patient list
        )
        vet2 = Vet(
            2,
            "Dr. Johnson",
            LocalDate.of(2020, 5, 10),
            mutableListOf(), // Empty specializations list
            75000.0,
            "Dentist",
            mutableListOf(Pet(1, "PetName", "PetType", LocalDate.of(2022, 2, 2), false, 1, 12345)) // Patient list with one pet
        )
        vet3 = Vet(
            3,
            "Dr. Brown",
            LocalDate.of(2019, 9, 20),
            mutableListOf("General Checkups"), // Specializations with some values
            70000.0,
            "General Practitioner",
            mutableListOf(Pet(2, "AnotherPet", "AnotherType", LocalDate.of(2023, 3, 3), true, 2, 54321)) // Patient list with one pet
        )
        vet4 = Vet(
            4,
            "Dr. Lee",
            LocalDate.of(2021, 3, 8),
            mutableListOf("Orthopedics", "Neurology"), // Specializations with some values
            85000.0,
            "Orthopedic Surgeon",
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
}
