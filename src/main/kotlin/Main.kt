
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
import utils.Utilities.loggerWarnFormat
import utils.ValidateInput
import java.io.File
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

fun mainMenu(): Int {
    t.println(
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
    )

    return readNextInt("")
}

fun runMainMenu() {
    do {
        when (mainMenu()) {
            1 -> runPetMenu()
            2 -> runVetMenu()
            3 -> runOwnerMenu()
            0 -> exitApp()
            else -> loggerWarnFormat()
        }
    } while (true)
}

fun petMenu(): Int {
    t.println(
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
 ┃  8) 💉 Update Vaccination         ┃ 
 ┃  9) 📅 Sort Pets by Age           ┃ 
 ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━ 
 ┃  0) ❌ Exit                       ┃ 
 ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━ 
                                       
  Enter option:                        
                                       """
        )
    )
    return readNextInt("")
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
            8 -> updateVaccination()
            9 -> sortPetsByAge()
            0 -> runMainMenu()
            else -> loggerWarnFormat()
        }
    } while (true)
}

fun vetMenu(): Int {
    t.println(
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
 ┃  7) 📈 Filter Vets by Experience  ┃ 
 ┃  8) 🔍 Search by specialisation   ┃ 
 ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━ 
 ┃  0) ❌ Exit                       ┃ 
 ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━ 
                                       
  Enter option:                        
                                       """
        )
    )
    return readNextInt("")
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
            7 -> filterVetsByExperience()
            8 -> searchBySpecialisation()
            0 -> runMainMenu()
            else -> loggerWarnFormat()
        }
    } while (true)
}

fun ownerMenu(): Int {
    t.println(
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
    )
    return readNextInt("")
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
            else -> loggerWarnFormat()
        }
    } while (true)
}

fun addPet() {
    val name = Utilities.capitalizeFirstLetter(readNextLine("Enter Pet Name: "))
    val breed = Utilities.capitalizeFirstLetter(readNextLine("Enter Pet Breed: "))
    val dob = ValidateInput.readValidDOB("Enter Pet DOB (YYYY-MM-DD format): ")
    println()
    listAllVets()
    var vetID: Int
    do { vetID = readNextInt("Enter index of Vet who you want to assign: ") }
    while (!Utilities.isValidListIndex(vetID, vetAPI.getAllVets()))
    listAllOwners()
    var ownerPPSIndex: Int
    do { ownerPPSIndex = readNextInt("Enter index of Owner you want to assign: ") }
    while (!Utilities.isValidListIndex(ownerPPSIndex, ownerAPI.getAllOwners()))
    val ownerPPS = ownerAPI.findOwnerByIndex(ownerPPSIndex)?.PPS

    val pet = Pet(
        0,
        name,
        breed,
        dob,
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
    val dateQualified = ValidateInput.readValidDOB("Enter Date Qualified (YYYY-MM-DD format): ")
    val specialisations: MutableList<String> = mutableListOf()
    var input: String
    do {
        input = Utilities.capitalizeFirstLetter(readNextLine("Enter specialisation, type 'F' to finish: "))
        if (input != "F") {
            specialisations.add(input)
        }
    }
    while (input != "F")
    val salary = readNextDouble("Enter Vet Salary: ")

    var position: String
    do{position = Utilities.capitalizeFirstLetter(readNextLine("Enter Vet Position (Junior/Senior): "))}
    while (!Utilities.isValidPosition(position))


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
    var pps: String
    do {pps = readNextLine("Enter Owner PPS: ")
    } while (!ValidateInput.isValidPPS(pps))
    val name = Utilities.capitalizeFirstLetter(readNextLine("Enter Owner Name: "))
    val phoneNumber = readNextLine("Enter Owner Phone Number: ")

    var email: String
    do {email = readNextLine("Enter Owner Email: ")
    } while (!ValidateInput.isEmailValid(email))

    val isAdded = ownerAPI.addOwner(
        Owner(
            pps,
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

fun updateVaccination() {
    listAllPets()
    if (petAPI.numberOfPets() > 0) {
        val index = readNextInt(" Enter the index of the pet to update it vaccination ")
        if (petAPI.isValidIndex(index)) {
            if (petAPI.updateVaccination(index)) {
                println()
                loggerInfoSuccessful()
            } else {
                println()
                loggerInfoUnsuccessful()
            }
        } else {
            println()
            logger.warn { " There are no pets with this index number or the pet is vaccinated." }
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
        println("❗ No pets found")
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
        println("❗ No pets found")
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
        println("❗ No owners found")
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
            val dob = ValidateInput.readValidDOB("Enter Pet DOB (YYYY-MM-DD format): ")
            if (petAPI.updatePet(
                    indexToUpdate,
                    Pet(0, name, breed, dob, false, 0, "0000000AB")
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
            val dateQualified = ValidateInput.readValidDOB("Enter Vet DOB (YYYY-MM-DD format): ")
            val specialisations: MutableList<String> = mutableListOf()
            var input: String
            do {
                input = Utilities.capitalizeFirstLetter(readNextLine("Enter specialisation, type 'F' to finish: "))
                if (input != "F") {
                    specialisations.add(input)
                }
            }
            while (input != "F")
            val salary = readNextDouble("Enter Vet Salary: ")
            var position: String
            do{position = Utilities.capitalizeFirstLetter(readNextLine("Enter Vet Position (Junior/Senior): "))}
            while (!Utilities.isValidPosition(position))

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
            println("no vets")
        }
    }
}

fun updateOwner() {
    listAllOwners()
    val indexToUpdate = readNextInt("Enter the index of the Owner to update: ")
    if (vetAPI.isValidIndex(indexToUpdate)) {
        var pps: String
        do {pps = readNextLine("Enter Owner PPS: ")
        } while (!ValidateInput.isValidPPS(pps))
        val name = Utilities.capitalizeFirstLetter(readNextLine("Enter Owner Name: "))
        val phoneNumber = readNextLine("Enter Owner Phone Number: ")
        var email: String
        do {email = readNextLine("Enter Owner Email: ")
        } while (!ValidateInput.isEmailValid(email))

        if (ownerAPI.updateOwner(
                indexToUpdate,
                Owner(pps, name, phoneNumber, email, mutableListOf())
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
        println("no owner")
    }
}

fun sortPetsByAge() {
    if (petAPI.numberOfPets() > 0) {
        t.println(
            style(
                """ 
                                               
 ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━ 
 ┃  1) Sort pets from youngest to oldest 📅  ┃ 
 ┃  2) Sort pets from oldest to youngest 📅  ┃ 
  ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━ 
                                               
  Enter option:                                
                                               """
            )
        )

        when (readNextInt("")) {
            1 -> sortPetsYoungestToOldest()
            2 -> sortPetsOldestToYoungest()
            else -> loggerWarnFormat()
        }
    } else {
        println()
        logger.warn { "❗ No pets found" }
    }
}
fun sortPetsYoungestToOldest() = println(petAPI.sortPetsYoungestToOldest())

fun sortPetsOldestToYoungest() = println(petAPI.sortPetsOldestToYoungest())

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

fun filterVetsByExperience() {
    val yearsOfExperience = readNextInt("Enter the number of years of experience: ")

    val filteredVets = vetAPI.filterVetsByExperience(yearsOfExperience)

    if (filteredVets.isEmpty()) {
        println("No vets found with at least $yearsOfExperience years of experience.")
    } else {
        println("Vets with at least $yearsOfExperience years of experience:")
        println(vetAPI.formatListString(filteredVets))
    }
}
fun searchBySpecialisation() {
    val userInput = Utilities.capitalizeFirstLetter(readNextLine("Enter the specialization you are looking for within vets: "))
    val filteredVets = vetAPI.searchVetSpecialisation(userInput)

    if (filteredVets.isNotEmpty()) {
        println("Filtered Vets with Specialization '$userInput':")
        println(vetAPI.formatListString(filteredVets))
    } else {
        println("No vets found with the specialization '$userInput'.")
    }
}

fun saveAll() {
    try {
        logger.info { "Saving data..." }
        petAPI.savePets()
        vetAPI.saveVets()
        ownerAPI.saveOwners()
    } catch (e: Exception) {
        System.err.println("Error writing to file: $e")
    }
}

fun loadAll() {
    try {
        logger.info { "Loading data..." }
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
    logger.info { "Exiting..." }
    exitProcess(0)
}
