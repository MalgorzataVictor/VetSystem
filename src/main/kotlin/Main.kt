
import com.github.ajalt.mordant.rendering.TextColors
import com.github.ajalt.mordant.rendering.TextStyles
import com.github.ajalt.mordant.terminal.Terminal
import controllers.GmailAPI
import controllers.OwnerAPI
import controllers.PetAPI
import controllers.VetAPI
import models.Owner
import models.Pet
import models.Vet
import mu.KotlinLogging
import persistence.XMLSerializer
import utils.ScannerInput.readNextDouble
import utils.ScannerInput.readNextInt
import utils.ScannerInput.readNextLine
import utils.Utilities
import utils.Utilities.loggerInfoSuccessful
import utils.Utilities.loggerInfoUnsuccessful
import java.io.File
import java.time.LocalDate
import kotlin.collections.ArrayList
import kotlin.system.exitProcess

private val petAPI = PetAPI(XMLSerializer(File("pets.xml")))
private val vetAPI = VetAPI(XMLSerializer(File("vets.xml")))
private val ownerAPI = OwnerAPI(XMLSerializer(File("owners.xml")))
private val logger = KotlinLogging.logger {}
private val GmailApi = GmailAPI
val t = Terminal()
val style = (TextStyles.bold + TextColors.red + TextColors.brightWhite.bg)
fun main(args: Array<String>) {
    logger.info { "Launching Vet System App..." }
    loadAll()
    runMainMenu()
}

fun mainMenu(): Int? {
    return t.prompt(
        style(
            """
                                       
 ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━ 
 ┃         🏥 Vet System  🏥         ┃ 
 ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━ 
 ┃                                   ┃ 
 ┃  1) 🐇 Pet Menu                   ┃ 
 ┃  2) 🥼️ Vet Menu                   ┃ 
 ┃  3) 🧑🏻 Owner Menu                 ┃ 
 ┃                                   ┃ 
 ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━ 
 ┃  0) ❌ Exit                       ┃ 
 ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━ 
                                       
   Enter option:                       
                                       """
        )
    )?.toInt()
}

fun runMainMenu() {
    do {
        when (mainMenu()) {
            1 -> runPetMenu()
            2 -> runVetMenu()
            3 -> runOwnerMenu()
            0 -> exitApp()
            else -> Utilities.logggerWarnFormat()
        }
    } while (true)
}

fun petMenu(): Int? {
    return t.prompt(
        style(
            """ 
                                       
 ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━ 
 ┃            🐇 Pet 🐇              ┃ 
  ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━ 
 ┃  1) ➕ Add Pet                    ┃ 
 ┃  2) 🗑️ Delete Pet                 ┃ 
 ┃  3) 📋 List Pet                   ┃ 
 ┃  4) 🖋 Update Pet                 ┃ 
 ┃  5) 🔟 Number Of Pets             ┃ 
 ┃  6) 🔍 Search Pet                 ┃ 
 ┃  7) 🔔 Sent notification          ┃ 
 ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━ 
 ┃  0) ❌ Exit                       ┃ 
 ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━ 
                                       
  Enter option:                        
                                       """
        )
    )?.toInt()
}

fun runPetMenu() {
    do {
        when (petMenu()) {
            1 -> addPet()
            2 -> deletePet()
            3 -> listAllPets()
            4 -> updatePet()
            5 -> numberOfPets()
            6 -> searchPet()
            7 -> sendNotification()
            0 -> runMainMenu()
            else -> Utilities.logggerWarnFormat()
        }
    } while (true)
}

fun vetMenu(): Int? {
    return t.prompt(
        style(
            """ 
                                       
 ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━ 
 ┃            🥼 Vet 🥼              ┃ 
  ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━ 
 ┃  1) ➕ Add Vet                    ┃ 
 ┃  2) 🗑️ Delete Vet                 ┃ 
 ┃  3) 📋 List Vet                   ┃ 
 ┃  4) 🖋 Update Vet                 ┃ 
 ┃  5) 🔟 Number Of Vets             ┃ 
 ┃  6) 🔍 Search Vet                 ┃ 
 ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━ 
 ┃  0) ❌ Exit                       ┃ 
 ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━ 
                                       
  Enter option:                        
                                       """
        )
    )?.toInt()
}

fun runVetMenu() {
    do {
        when (vetMenu()) {
            1 -> addVet()
            2 -> deleteVet()
            3 -> listAllVets()
            4 -> updateVet()
            5 -> numberOfVets()
            6 -> searchVet()
            0 -> runMainMenu()
            else -> Utilities.logggerWarnFormat()
        }
    } while (true)
}

fun ownerMenu(): Int? {
    return t.prompt(
        style(
            """ 
                                       
 ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━ 
 ┃           🧑🏻 Owner 🧑🏻             ┃ 
  ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━ 
 ┃  1) ➕ Add Owner                  ┃ 
 ┃  2) 🗑️ Delete Owner               ┃ 
 ┃  3) 📋 List Owner                 ┃ 
 ┃  4) 🖋 Update Owner               ┃ 
 ┃  5) 🔟 Number Of Owner            ┃ 
 ┃  6) 🔍 Search Owner               ┃ 
 ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━ 
 ┃  0) ❌ Exit                       ┃ 
 ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━ 
                                       
  Enter option:                        
                                       """
        )
    )?.toInt()
}

fun runOwnerMenu() {
    do {
        when (ownerMenu()) {
            1 -> addOwner()
            2 -> deleteOwner()
            3 -> listAllOwners()
            4 -> updateOwner()
            5 -> numberOfOwners()
            6 -> searchOwner()
            0 -> runMainMenu()
            else -> Utilities.logggerWarnFormat()
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
    val ownerPPSIndex = readNextInt("Enter index of Owner you want to assign: ")
    val ownerPPS = ownerAPI.findOwnerByIndex(ownerPPSIndex)?.PPS

    val pet = Pet(
        0,
        name,
        breed,
        DOB,
        false,
        vetID,
        ownerPPS!!
    )
    val isAdded = petAPI.addPet(pet)
    if (isAdded) {
        vetAPI.assignPetToVet(vetID, pet)
        ownerAPI.assignPetToOwner(ownerPPSIndex, pet)
        println()
        loggerInfoSuccessful()
    } else {
        println()
        loggerInfoUnsuccessful()
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
        loggerInfoSuccessful()
    } else {
        println()
        loggerInfoUnsuccessful()
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
        loggerInfoSuccessful()
    } else {
        println()
        loggerInfoUnsuccessful()
    }
}

fun deletePet() {
    listAllPets()
    if (petAPI.numberOfPets() > 0) {
        val indexToDelete = readNextInt("Enter the index of the Pet to delete: ")
        val petToDelete = petAPI.deletePet(indexToDelete)
        if (petToDelete != null) {
            println()
            loggerInfoSuccessful()
        } else {
            println()
            loggerInfoUnsuccessful()
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
            loggerInfoSuccessful()
        } else {
            println()
            loggerInfoUnsuccessful()
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
            loggerInfoSuccessful()
        } else {
            println()
            loggerInfoUnsuccessful()
        }
    }
}

fun sendNotification() {
    val sickPets = petAPI.getAllPets().filter { !it.isVaccinated }
    sickPets.forEach { pet ->

        val emailGroup = ownerAPI.findOwner(pet.ownerPPS)?.email
        GmailApi.sendEmail(
            "$emailGroup",
            "VACCINATION REMINDER \uD83D\uDC89 ❗",
            "We are reminding about vaccination for ${pet.name}.\n Please book an appointment with our Clinic \uD83D\uDC15"
        )
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
            val name = Utilities.capitalizeFirstLetter(readNextLine("Enter Pet Name: "))
            val breed = Utilities.capitalizeFirstLetter(readNextLine("Enter Pet Breed: "))
            val dobInput = readNextLine("Enter Pet DOB (YYYY-MM-DD format): ").split("-")
            val DOB = LocalDate.of(dobInput[0].toInt(), dobInput[1].toInt(), dobInput[2].toInt())
            if (petAPI.updatePet(
                    indexToUpdate,
                    Pet(0, name, breed, DOB, false, 0, 0)
                )
            ) {
                println()
                loggerInfoSuccessful()
            } else {
                println()
                loggerInfoUnsuccessful()
            }
        } else {
            println()
            println("no pets")
        }
    }
}

fun updateVet() {
    listAllVets()
    if (vetAPI.numberOfVets() > 0) {
        val indexToUpdate = readNextInt("Enter the index of the Vet to update: ")
        if (vetAPI.isValidIndex(indexToUpdate)) {
            val name = Utilities.capitalizeFirstLetter(readNextLine("Enter Vet Name: "))
            val dobInput = readNextLine("Enter Vet DOB (YYYY-MM-DD format): ").split("-")
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
            val salary = readNextDouble("Enter Vet Salary")
            val position = Utilities.capitalizeFirstLetter(readNextLine("Enter Vet Position: "))

            if (vetAPI.updateVet(
                    indexToUpdate,
                    Vet(0, name, dateQualified, specialisations, salary, position, mutableListOf())
                )
            ) {
                println()
                loggerInfoSuccessful()
            } else {
                println()
                loggerInfoUnsuccessful()
            }
        } else {
            println()
            println("no pets")
        }
    }
}

fun updateOwner() {
    listAllOwners()
    val indexToUpdate = readNextInt("Enter the index of the Owner to update: ")
    if (vetAPI.isValidIndex(indexToUpdate)) {
        val PPS = readNextInt("Enter Owner PPS: ")
        val name = Utilities.capitalizeFirstLetter(readNextLine("Enter Owner Name: "))
        val phoneNumber = Utilities.capitalizeFirstLetter(readNextLine("Enter Owner Phone Number: "))
        val email = Utilities.capitalizeFirstLetter(readNextLine("Enter Owner email: "))

        if (ownerAPI.updateOwner(
                indexToUpdate,
                Owner(PPS, name, phoneNumber, email, mutableListOf())
            )
        ) {
            println()
            loggerInfoSuccessful()
        } else {
            println()
            loggerInfoUnsuccessful()
        }
    } else {
        println()
        println("no pets")
    }
}

fun listAllPets() = println(petAPI.listAllPets())
fun listAllVets() {
    val vets1: ArrayList<Vet> = vetAPI.getAllVets()
    vets1.forEach {
        println(it)
        val patientsDetails = it.patientList.joinToString("\n") { p -> petAPI.findPet(p)?.toString() ?: "Pet details not found" }
        println(patientsDetails)
    }
}
fun listAllOwners() {
    val owners1: ArrayList<Owner> = ownerAPI.getAllOwners()
    owners1.forEach {
        println(it)
        val patientsDetails = it.petsList.joinToString("\n") { p -> petAPI.findPet(p)?.toString() ?: "Pet details not found" }
        println(patientsDetails)
    }
}
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
