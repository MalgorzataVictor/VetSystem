import controllers.OwnerAPI
import controllers.PetAPI
import controllers.VetAPI
import models.Pet
import persistence.XMLSerializer
import utils.ScannerInput
import utils.ScannerInput.readNextInt
import utils.ScannerInput.readNextLine
import utils.Utilities
import java.io.File
import java.time.LocalDate
import java.time.format.DateTimeFormatter
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
            /*   1 -> //addVet()
             2 -> deleteVet()
              3 -> listAllVets()
              4 -> updateVet()
              5 -> numberOfVets()
              6 -> searchVet
              0 -> runMainMenu()*/
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
            /*   1 -> //addOwner()
             2 -> deleteOwner()
              3 -> listAllOwners()
              4 -> updateOwner()
              5 -> numberOfOwners()
              6 -> searchOwner
              0 -> runMainMenu()*/
            else -> println("Invalid option entered: $option")
        }
    } while (true)
}

fun addPet() {
    val name = Utilities.capitalizeFirstLetter(readNextLine("Enter Pet Name: "))
    val breed = Utilities.capitalizeFirstLetter(readNextLine("Enter Pet Breed: "))
    val dobInput = readNextLine("Enter Pet DOB (MM-YY format): ")
    val formatter = DateTimeFormatter.ofPattern("MM-yy", Locale.ENGLISH)
    val DOB = LocalDate.parse(dobInput, formatter)
    println()
    listAllVets()
    val vetID = readNextInt("Enter ID of Vet who you want to assign: ")
    listAllOwners()
    val ownerPPS = readNextInt("Enter PPS of Owner you want to assign: ")

    val isAdded = petAPI.addPet(
        Pet(
            0,
            name,
            breed,
            DOB,
            false,
            vetID,
            ownerPPS
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

fun numberOfPets() {
    val petSize = petAPI.numberOfPets()
    println("Number of Pets in the system: $petSize pets")
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

fun updatePet() {
    listAllPets()
    if (petAPI.numberOfPets() > 0) {
        val indexToUpdate = readNextInt("Enter the Index of the Pet you wish to update: ")
        if (petAPI.isValidIndex(indexToUpdate)) {
            val pet1 = petAPI.findPetByIndex(indexToUpdate)
            val newPet =
                Pet(
                    pet1!!.petID,
                    pet1.name,
                    pet1.breed,
                    pet1.DOB,
                    pet1.isVaccinated,
                    pet1.vetID,
                    pet1.ownerPPS
                )

            println(
                """ 
            
    ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    ┃        PET        ┃
    ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    ┃   Update:                             ┃
    ┃   𝟭. Pet Name                       ┃
    ┃   𝟮. Pet Breed                        ┃
    ┃   𝟯. Pet DOB                           ┃
    ┃   𝟰. PET vetID                      ┃
    ┃   𝟱. PEt OwnerID                  ┃
    ┃                                       ┃
    ┃  -𝟭. Exit                             ┃
    ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
         
    Enter Option ➡️ """

            )

            var choice: Int

            do {
                choice = readLine()!!.toInt()
                when (choice) {
                    1 -> {
                        newPet.name = Utilities.capitalizeFirstLetter(readNextLine("Enter Pet Name: "))
                        return
                    }

                    2 -> {
                        newPet.breed = Utilities.capitalizeFirstLetter(readNextLine("Enter Pet Breed: "))
                        return
                    }

                    3 -> {
                        val dobInput = readNextLine("Enter Pet DOB (MM-YY format): ")
                        val formatter = DateTimeFormatter.ofPattern("MM-yy", Locale.ENGLISH)
                        val DOB = LocalDate.parse(dobInput, formatter)
                        newPet.DOB = DOB
                        return
                    }

                    4 -> {
                        listAllVets()
                        val vetID = readNextInt("Enter ID of Vet who you want to assign: ")
                        newPet.vetID = vetID
                        return
                    }

                    5 -> {
                        listAllOwners()
                        val ownerPPS = readNextInt("Enter PPS of Owner you want to assign: ")
                        newPet.ownerPPS = ownerPPS
                        return
                    }

                    else -> println("Invalid Value")
                }
            } while (choice != -1)

            if (petAPI.updatePet(
                    indexToUpdate,
                    newPet
                )
            ) {
                println()
                println("        ✔ Update Successful")
            } else {
                println()
                println("        ❌ Update Failed")
            }
        } else {
            println()
            print("❗ No notes found")
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
