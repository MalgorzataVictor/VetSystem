import controllers.OwnerAPI
import controllers.PetAPI
import controllers.VetAPI
import models.Owner
import models.Pet
import models.Vet
import persistence.XMLSerializer
import utils.ScannerInput
import utils.ScannerInput.readNextDouble
import utils.ScannerInput.readNextInt
import utils.ScannerInput.readNextLine
import utils.Utilities
import java.io.File
import java.time.LocalDate
import java.util.*
import kotlin.system.exitProcess

private val petAPI = PetAPI(XMLSerializer(File("pets.xml")))
private val vetAPI = VetAPI(XMLSerializer(File("vets.xml")))
private val ownerAPI = OwnerAPI(XMLSerializer(File("owners.xml")))
fun main(args: Array<String>) {
    loadAll()
    runMainMenu()
}

fun mainMenu(): Int {
    return ScannerInput.readNextInt(
        """ 
             
        ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
        ┃            Vet System             ┃
        ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
        ┃  1) Pet Menu                      ┃
        ┃  2) Vet Menu                      ┃
        ┃  3) Owner Menu                    ┃
        ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
        ┃  0) Exit                          ┃
        ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
        Enter option️"""
    )
}

fun runMainMenu() {
    do {
        when (val option = mainMenu()) {
            1 -> runPetMenu()
            2 -> runVetMenu()
            3 -> runOwnerMenu()
            0 -> exitApp()
            else -> println("Invalid option entered: $option")
        }
    } while (true)
}

fun petMenu(): Int {
    return ScannerInput.readNextInt(
        """ 
             
        ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
        ┃            Pet                    ┃
        ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
        ┃  1) Add Pet                       ┃
        ┃  2) Delete Pet                    ┃
        ┃  3) List Pet                      ┃ 
        ┃  4) Update Pet                    ┃
        ┃  5) Number Of Pet                 ┃
        ┃  6) Search Pet                    
        ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
        ┃  0) Exit                          ┃
        ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
        Enter option️"""
    )
}

fun runPetMenu() {
    do {
        when (val option = petMenu()) {
            1 -> addPet()
            2 -> deletePet()
            3 -> listAllPets()
            4 -> updatePet()
            5 -> numberOfPets()
            6 -> searchPet()
            0 -> runMainMenu()
            else -> println("Invalid option entered: $option")
        }
    } while (true)
}

fun vetMenu(): Int {
    return ScannerInput.readNextInt(
        """ 
             
        ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
        ┃            Vet                    ┃
        ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
        ┃  1) Add Vet                       ┃
        ┃  2) Delete Vet                    ┃
        ┃  3) List Vet                      ┃ 
        ┃  4) Update Vet                    ┃
        ┃  5) Number Of Vet                 ┃
        ┃  6) Search Vet                    
        ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
        ┃  0) Exit                          ┃
        ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
        Enter option️"""
    )
}

fun runVetMenu() {
    do {
        when (val option = vetMenu()) {
            1 -> addVet()
            2 -> deleteVet()
            3 -> listAllVets()
            4 -> updateVet()
            5 -> numberOfVets()
            6 -> searchVet()
            0 -> runMainMenu()
            else -> println("Invalid option entered: $option")
        }
    } while (true)
}

fun ownerMenu(): Int {
    return ScannerInput.readNextInt(
        """ 
             
        ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
        ┃            Owner                    ┃
        ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
        ┃  1) Add Owner                       ┃
        ┃  2) Delete Owner                    ┃
        ┃  3) List Owner                      ┃ 
        ┃  4) Update Owner                    ┃
        ┃  5) Number Of Owner                 ┃
        ┃  6) Search Owner                    
        ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
        ┃  0) Exit                          ┃
        ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
        Enter option️"""
    )
}

fun runOwnerMenu() {
    do {
        when (val option = ownerMenu()) {
            1 -> addOwner()
            2 -> deleteOwner()
            3 -> listAllOwners()
            4 -> updateOwner()
            5 -> numberOfOwners()
            6 -> searchOwner()
            0 -> runMainMenu()
            else -> println("Invalid option entered: $option")
        }
    } while (true)
}

fun addPet() {
    val name = Utilities.capitalizeFirstLetter(readNextLine("Enter Pet Name: "))
    val breed = Utilities.capitalizeFirstLetter(readNextLine("Enter Pet Breed: "))
    val dobInput = readNextLine("Enter Pet DOB (YYYY-MM-DD format): ").split("-")
    val DOB = LocalDate.of(dobInput[0].toInt(), dobInput[1].toInt(), dobInput[2].toInt())
    println()
    listAllVets()
    val vetID = readNextInt("Enter index of Vet who you want to assign: ")
    listAllOwners()
    val ownerPPS = readNextInt("Enter index of Owner you want to assign: ")

    val pet = Pet(
        0,
        name,
        breed,
        DOB,
        false,
        vetID,
        ownerPPS
    )
    val isAdded = petAPI.addPet(pet)
    if (isAdded) {
        vetAPI.assignPetToVet(vetID, pet)
        ownerAPI.assignPetToOwner(ownerPPS, pet)
        println()
        println("✔ Added Successfully")
    } else {
        println()
        println("❌ Add Failed")
    }
}

fun addVet() {
    val name = Utilities.capitalizeFirstLetter(readNextLine("Enter Vet Name: "))
    val dobInput = readNextLine("Enter Date Qualified (YYYY-MM-DD format): ").split("-")
    val dateQualified = LocalDate.of(dobInput[0].toInt(), dobInput[1].toInt(), dobInput[2].toInt())
    val specialisations: MutableList<String> = mutableListOf()
    var input: String
    do {
        input = Utilities.capitalizeFirstLetter(readNextLine("Enter specialisation, type 'F' to finish"))
        if (input != "F") {
            specialisations.add(input)
        }
    }
    while (input != "F")
    val salary = readNextDouble("Enter Vet Salary: ")
    val position = Utilities.capitalizeFirstLetter(readNextLine("Enter Vet Position: "))

    val isAdded = vetAPI.addVet(
        Vet(
            0,
            name,
            dateQualified,
            specialisations,
            salary,
            position,
            mutableListOf()
        )
    )
    if (isAdded) {
        println()
        println("✔ Added Successfully")
    } else {
        println()
        println("❌ Add Failed")
    }
}

fun addOwner() {
    val PPS = readNextInt("Enter Owner PPS: ")
    val name = Utilities.capitalizeFirstLetter(readNextLine("Enter Owner Name: "))
    val phoneNumber = Utilities.capitalizeFirstLetter(readNextLine("Enter Owner Phone Number: "))
    val email = Utilities.capitalizeFirstLetter(readNextLine("Enter Owner Email: "))

    val isAdded = ownerAPI.addOwner(
        Owner(
            PPS,
            name,
            phoneNumber,
            email,
            mutableListOf()
        )
    )
    if (isAdded) {
        println()
        println("✔ Added Successfully")
    } else {
        println()
        println("❌ Add Failed")
    }
}

fun deletePet() {
    listAllPets()
    if (petAPI.numberOfPets() > 0) {
        val indexToDelete = readNextInt("Enter the index of the Pet to delete: ")
        val petToDelete = petAPI.deletePet(indexToDelete)
        if (petToDelete != null) {
            println()
            println("✔ Delete Successful! Deleted note: ${petToDelete.name}")
        } else {
            println()
            println("❌ Delete NOT Successful")
        }
    }
}

fun deleteVet() {
    listAllVets()
    if (vetAPI.numberOfVets() > 0) {
        val indexToDelete = readNextInt("Enter the index of the Vet to delete: ")
        val vetToDelete = vetAPI.deleteVet(indexToDelete)
        if (vetToDelete != null) {
            println()
            println("✔ Delete Successful! Deleted note: ${vetToDelete.name}")
        } else {
            println()
            println("❌ Delete NOT Successful")
        }
    }
}

fun deleteOwner() {
    listAllOwners()
    if (ownerAPI.numberOfOwners() > 0) {
        val indexToDelete = readNextInt("Enter the index of the Owner to delete: ")
        val ownerToDelete = ownerAPI.deleteOwner(indexToDelete)
        if (ownerToDelete != null) {
            println()
            println("✔ Delete Successful! Deleted note: ${ownerToDelete.name}")
        } else {
            println()
            println("❌ Delete NOT Successful")
        }
    }
}

fun numberOfPets() {
    val petSize = petAPI.numberOfPets()
    println("Number of Pets in the system: $petSize pets")
}

fun numberOfVets() {
    val vetSize = vetAPI.numberOfVets()
    println("Number of Vets in the system: $vetSize vets")
}

fun numberOfOwners() {
    val ownerSize = ownerAPI.numberOfOwners()
    println("Number of Owners in the system: $ownerSize owners")
}

fun searchPet() {
    val searchName = readNextLine("Enter the Pet Name to search by: ")
    val searchResults = petAPI.searchByName(searchName)
    if (searchResults.isEmpty()) {
        println()
        println("❗ No notes found")
    } else {
        println()
        println(searchResults)
    }
}

fun searchVet() {
    val searchName = readNextLine("Enter the Vet Name to search by: ")
    val searchResults = vetAPI.searchByName(searchName)
    if (searchResults.isEmpty()) {
        println()
        println("❗ No notes found")
    } else {
        println()
        println(searchResults)
    }
}

fun searchOwner() {
    val searchName = readNextLine("Enter the Owner Name to search by: ")
    val searchResults = ownerAPI.searchByName(searchName)
    if (searchResults.isEmpty()) {
        println()
        println("❗ No notes found")
    } else {
        println()
        println(searchResults)
    }
}

fun updatePet() {
    listAllPets()
    if (petAPI.numberOfPets() > 0) {
        val indexToUpdate = readNextInt("Enter the index of the Pet to update: ")
        if (petAPI.isValidIndex(indexToUpdate)) {
            val existingPet = petAPI.findPetByIndex(indexToUpdate) // Fetch the existing pet
            val name = Utilities.capitalizeFirstLetter(readNextLine("Enter Pet Name (${existingPet!!.name}): "))
            val breed = Utilities.capitalizeFirstLetter(readNextLine("Enter Pet Breed (${existingPet.breed}): "))
            val dobInput = readNextLine("Enter Pet DOB (YYYY-MM-DD format) (${existingPet.DOB}): ").split("-")
            val DOB = try {
                LocalDate.of(dobInput[0].toInt(), dobInput[1].toInt(), dobInput[2].toInt())
            } catch (e: Exception) {
                existingPet.DOB // Use the existing date if the input is invalid
            }

            // Update only the necessary fields
            val updatedPet = existingPet.copy(
                name = name,
                breed = breed,
                DOB = DOB
                // Other attributes you want to update...
            )

            if (petAPI.updatePet(indexToUpdate, updatedPet)) {
                println("✔ Update Successful")
            } else {
                println("❌ Update Failed")
            }
        } else {
            println("❗ No pet found with the given index")
        }
    }
}

fun updateVet() {
    listAllVets()
    if (vetAPI.numberOfVets() > 0) {
        val indexToUpdate = readNextInt("Enter the index of the Vet to update: ")
        if (vetAPI.isValidIndex(indexToUpdate)) {
            val currentVet = vetAPI.findVetByIndex(indexToUpdate)
            val name = Utilities.capitalizeFirstLetter(readNextLine("Enter Vet Name: "))
            val dobInput = readNextLine("Enter Date Qualified (YYYY-MM-DD format): ").split("-")
            val dateQualified = LocalDate.of(dobInput[0].toInt(), dobInput[1].toInt(), dobInput[2].toInt())

            // Display current specialisations and prompt for action (add or delete)
            println("Current Specialisations: ${currentVet!!.specialisation.joinToString(", ")}")
            val action = Utilities.capitalizeFirstLetter(readNextLine("Enter 'A' to add or 'D' to delete specialisation: "))
            if (action == "A") {
                val newSpecialisation = Utilities.capitalizeFirstLetter(readNextLine("Enter a specialisation to add: "))
                currentVet.specialisation.add(newSpecialisation)
            } else if (action == "D") {
                val indexToRemove = readNextInt("Enter the index of specialisation you want to delete: ")
                if (indexToRemove >= 0 && indexToRemove < currentVet.specialisation.size) {
                    currentVet.specialisation.removeAt(indexToRemove)
                } else {
                    println("Invalid index for deletion")
                    return
                }
            }

            val salary = readNextDouble("Enter Vet Salary: ")
            val position = Utilities.capitalizeFirstLetter(readNextLine("Enter Vet position: "))

            // Update the vet using the retrieved currentVet object
            if (vetAPI.updateVet(
                    indexToUpdate,
                    Vet(
                            currentVet.vetID,
                            name,
                            dateQualified,
                            currentVet.specialisation,
                            salary,
                            position,
                            currentVet.patientList
                        )
                )
            ) {
                println()
                println("✔ Update Successful")
            } else {
                println()
                println("❌ Update Failed")
            }
        } else {
            println()
            println("❗ No vet found with the given index")
        }
    }
}

fun updateOwner() {
    listAllOwners()
    if (ownerAPI.numberOfOwners() > 0) {
        val indexToUpdate = readNextInt("Enter the index of the Owner to update: ")
        if (ownerAPI.isValidIndex(indexToUpdate)) {
            val existingOwner = ownerAPI.findOwnerByIndex(indexToUpdate) // Fetch the existing owner

            val PPS = readNextInt("Enter Owner PPS (${existingOwner!!.PPS}): ")
            val name = Utilities.capitalizeFirstLetter(readNextLine("Enter Owner Name (${existingOwner.name}): "))
            val phoneNumber = Utilities.capitalizeFirstLetter(readNextLine("Enter Owner Phone Number (${existingOwner.phoneNumber}): "))
            val email = Utilities.capitalizeFirstLetter(readNextLine("Enter Owner Email (${existingOwner.email}): "))

            // Update only the necessary fields
            val updatedOwner = existingOwner.copy(
                PPS = PPS,
                name = name,
                phoneNumber = phoneNumber,
                email = email
                // Other attributes you want to update...
            )

            if (ownerAPI.updateOwner(indexToUpdate, updatedOwner)) {
                println("✔ Update Successful")
            } else {
                println("❌ Update Failed")
            }
        } else {
            println("❗ No owner found with the given index")
        }
    }
}

fun listAllPets() = println(petAPI.listAllPets())
fun listAllVets() = println(vetAPI.listAllVets())
fun listAllOwners() = println(ownerAPI.listAllOwners())
fun saveAll() {
    try {
        petAPI.savePets()
        vetAPI.saveVets()
        ownerAPI.saveOwners()
    } catch (e: Exception) {
        System.err.println("Error writing to file: $e")
    }
}

fun loadAll() {
    try {
        petAPI.loadPets()
        vetAPI.loadVets()
        ownerAPI.loadOwners()
    } catch (e: Exception) {
        System.err.println("Error reading from file: $e")
    }
}
fun exitApp() {
    saveAll()
    println()
    print("Exiting...")
    exitProcess(0)
}
