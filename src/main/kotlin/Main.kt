
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

/**
 * Entry point of the application.
 *
 * @param args Command line arguments passed to the application.
 */
fun main(args: Array<String>) {
    logger.info { "Launching Vet System App..." }
    loadAll()
    runMainMenu()
}

/**
 * Displays the main menu and prompts the user for input.
 *
 * @return The selected option from the main menu.
 */
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

/**
 * Handles navigation based on the selected option from the main menu.
 */
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

/**
 * Displays the pet menu and prompts the user for input.
 *
 * @return The selected option from the pet menu.
 */
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

/**
 * Handles navigation based on the selected option from the pet menu.
 */
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

/**
 * Displays the vet menu and prompts the user for input.
 *
 * @return The selected option from the vet menu.
 */
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

/**
 * Handles navigation based on the selected option from the vet menu.
 */
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

/**
 * Displays the owner menu and prompts the user for input.
 *
 * @return The selected option from the owner menu.
 */
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

/**
 * Handles navigation based on the selected option from the owner menu.
 */
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

/**
 * Adds a new pet to the system.
 *
 * @param name The name of the pet.
 * @param breed The breed of the pet.
 * @param dob The date of birth of the pet in "YYYY-MM-DD" format.
 * @param vetID The index of the vet to whom the pet will be assigned.
 * @param ownerPPSIndex The index of the owner to whom the pet will be assigned.
 */
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

/**
 * Adds a new vet to the system.
 *
 * @param name The name of the vet.
 * @param dateQualified The date when the vet got qualified in "YYYY-MM-DD" format.
 * @param specialisations The list of specializations the vet has.
 * @param salary The salary of the vet.
 * @param position The position of the vet (Junior/Senior).
 */
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
    do { position = Utilities.capitalizeFirstLetter(readNextLine("Enter Vet Position (Junior/Senior): ")) }
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

/**
 * Adds a new owner to the system.
 *
 * @param pps The Personal Public Service (PPS) number of the owner.
 * @param name The name of the owner.
 * @param phoneNumber The phone number of the owner.
 * @param email The email address of the owner.
 */
fun addOwner() {
    var pps: String
    do {
        pps = readNextLine("Enter Owner PPS: ")
    } while (!ValidateInput.isValidPPS(pps))
    val name = Utilities.capitalizeFirstLetter(readNextLine("Enter Owner Name: "))
    val phoneNumber = readNextLine("Enter Owner Phone Number: ")

    var email: String
    do {
        email = readNextLine("Enter Owner Email: ")
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

/**
 * Deletes a pet from the system.
 * Lists all pets and prompts the user to enter the index of the pet to delete.
 * Handles the deletion process based on user input.
 */
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

/**
 * Deletes a vet from the system.
 * Lists all vets and prompts the user to enter the index of the vet to delete.
 * Handles the deletion process based on user input.
 */
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

/**
 * Deletes an owner from the system.
 * Lists all owners and prompts the user to enter the index of the owner to delete.
 * Handles the deletion process based on user input.
 */
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

/**
 * Sends a vaccination reminder notification to owners for their pets.
 * Retrieves all pets that are not vaccinated and sends a notification to their respective owners via email.
 */
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

/**
 * Updates the vaccination status of a pet in the system.
 * Lists all pets and prompts the user to enter the index of the pet to update its vaccination.
 * Handles the update process based on user input.
 */
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

/**
 * Displays the number of pets in the system.
 * Retrieves the number of pets and prints the count.
 */
fun numberOfPets() {
    val petSize = petAPI.numberOfPets()
    println("Number of Pets in the system: $petSize pets")
}

/**
 * Displays the number of vets in the system.
 * Retrieves the number of vets and prints the count.
 */
fun numberOfVets() {
    val vetSize = vetAPI.numberOfVets()
    println("Number of Vets in the system: $vetSize vets")
}

/**
 * Displays the number of owners in the system.
 * Retrieves the number of owners and prints the count.
 */
fun numberOfOwners() {
    val ownerSize = ownerAPI.numberOfOwners()
    println("Number of Owners in the system: $ownerSize owners")
}

/**
 * Searches for a pet by its name.
 * Prompts the user to enter a pet name to search for and displays the search results.
 */
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

/**
 * Searches for a vet by their name.
 * Prompts the user to enter a vet name to search for and displays the search results.
 */
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

/**
 * Searches for an owner by their name.
 * Prompts the user to enter an owner name to search for and displays the search results.
 */
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

/**
 * Updates pet information in the system.
 * Lists all pets and prompts the user to enter the index of the pet to update.
 * Handles the update process based on user input.
 */
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

/**
 * Updates pet information in the system.
 * Lists all pets and prompts the user to enter the index of the pet to update.
 * Handles the update process based on user input.
 */
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
            do { position = Utilities.capitalizeFirstLetter(readNextLine("Enter Vet Position (Junior/Senior): ")) }
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

/**
 * Updates owner information in the system.
 * Lists all owners and prompts the user to enter the index of the owner to update.
 * Handles the update process based on user input.
 */
fun updateOwner() {
    listAllOwners()
    val indexToUpdate = readNextInt("Enter the index of the Owner to update: ")
    if (vetAPI.isValidIndex(indexToUpdate)) {
        var pps: String
        do {
            pps = readNextLine("Enter Owner PPS: ")
        } while (!ValidateInput.isValidPPS(pps))
        val name = Utilities.capitalizeFirstLetter(readNextLine("Enter Owner Name: "))
        val phoneNumber = readNextLine("Enter Owner Phone Number: ")
        var email: String
        do {
            email = readNextLine("Enter Owner Email: ")
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

/**
 * Sorts pets by age based on user selection.
 * Displays sorting options and performs the sorting accordingly.
 */
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
} /**
 * Sorts pets from youngest to oldest and displays the sorted list.
 */
fun sortPetsYoungestToOldest() = println(petAPI.sortPetsYoungestToOldest())

/**
 * Sorts pets from oldest to youngest and displays the sorted list.
 */
fun sortPetsOldestToYoungest() = println(petAPI.sortPetsOldestToYoungest())

/**
 * Lists all pets in the system.
 * Displays the details of all pets.
 */
fun listAllPets() = println(petAPI.listAllPets())

/**
 * Lists all vets in the system along with their assigned patients' details.
 * Displays the details of all vets and their respective patients.
 */
fun listAllVets() {
    val vets1: ArrayList<Vet> = vetAPI.getAllVets()
    vets1.forEach {
        println(it)
        val patientsDetails = it.patientList.joinToString("\n") { p -> petAPI.findPet(p)?.toString() ?: "❌ Pet details not found" }
        println(patientsDetails)
    }
} /**
 * Lists all owners in the system along with their pets' details.
 * Displays the details of all owners and their respective pets.
 */
fun listAllOwners() {
    val owners1: ArrayList<Owner> = ownerAPI.getAllOwners()
    owners1.forEach {
        println(it)
        val patientsDetails = it.petsList.joinToString("\n") { p -> petAPI.findPet(p)?.toString() ?: "❌ Pet details not found" }
        println(patientsDetails)
    }
}

/**
 * Filters vets based on the number of years of experience.
 * Prompts the user to enter the number of years of experience to filter vets.
 * Displays vets with at least the specified number of years of experience.
 */
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

/**
 * Searches for vets based on a specific specialization.
 * Prompts the user to enter a specialization to search for within vets.
 * Displays vets with the entered specialization.
 */
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

/**
 * Saves all data related to pets, vets, and owners to files.
 * Handles the process of saving data to files for persistence.
 */
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

/**
 * Loads all data related to pets, vets, and owners from files.
 * Handles the process of loading data from files for initialization.
 */
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

/**
 * Exits the application after saving all data and logging the exit action.
 * Saves data and displays an exit message before terminating the application.
 */
fun exitApp() {
    saveAll()
    println()
    logger.info { "Exiting..." }
    exitProcess(0)
}
