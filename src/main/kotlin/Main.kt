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
    val dobInput = readNextLine("Enter Pet DOB (DD-MM-yy format): ")
    val formatter = DateTimeFormatter.ofPattern("DD-MM-yy", Locale.ENGLISH)
    val DOB = LocalDate.parse(dobInput, formatter)
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
    val dobInput = readNextLine("Enter Date Qualified (DD-MM-yy format): ")
    val formatter = DateTimeFormatter.ofPattern("DD-MM-yy", Locale.ENGLISH)
    val dateQualified = LocalDate.parse(dobInput, formatter)
    val specialisations: MutableList<String> = mutableListOf()
    var input = ""
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
                        val vetID = readNextInt("Enter index of Vet who you want to assign: ")
                        val oldVetID = newPet.vetID
                        newPet.vetID = vetID
                        vetAPI.unAssignPetFromVet(oldVetID, vetID, newPet)
                        return
                    }

                    5 -> {
                        listAllOwners()
                        val ownerPPS = readNextInt("Enter PPS of Owner you want to assign: ")
                        val oldOwnerPPS = newPet.ownerPPS
                        newPet.ownerPPS = ownerPPS
                        ownerAPI.unAssignPetFromOwner(oldOwnerPPS, ownerPPS, newPet)
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
                println("✔ Update Successful")
            } else {
                println()
                println("❌ Update Failed")
            }
        } else {
            println()
            print("❗ No notes found")
        }
    }
}

fun updateVet() {
    listAllVets()
    if (vetAPI.numberOfVets() > 0) {
        val indexToUpdate = readNextInt("Enter the Index of the Vet you wish to update: ")
        if (vetAPI.isValidIndex(indexToUpdate)) {
            val vet1 = vetAPI.findVetByIndex(indexToUpdate)
            val newVet =
                Vet(
                    vet1!!.vetID,
                    vet1.name,
                    vet1.dateQualified,
                    vet1.specialisation,
                    vet1.salary,
                    vet1.position,
                    vet1.patientList
                )

            println(
                """ 
            
    ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    ┃        Vet        ┃
    ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    ┃   Update:                             ┃
    ┃   𝟭. Vet Name                       ┃
    ┃   𝟮. Vet Date Qualified                        ┃
    ┃   𝟯. Vet Specialisation                           ┃
    ┃   𝟰. Vet Salary                      ┃
    ┃   𝟱. Vet Position                  ┃
    ┃   𝟱. Vet Patient list                   ┃
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
                        newVet.name = Utilities.capitalizeFirstLetter(readNextLine("Enter Vet Name: "))
                        return
                    }

                    2 -> {
                        val dobInput = readNextLine("Enter Date Qualified (MM-YY format): ")
                        val formatter = DateTimeFormatter.ofPattern("MM-yy", Locale.ENGLISH)
                        val dateQualified = LocalDate.parse(dobInput, formatter)
                        newVet.dateQualified = dateQualified
                        return
                    }

                    3 -> {
                        val input = Utilities.capitalizeFirstLetter(readNextLine("Enter 'A' to or 'D' to delete specialisation: "))
                        println(newVet.formatListStringSpecialisation())
                        if (input == "A") {
                            val input2 = Utilities.capitalizeFirstLetter(readNextLine("Enter a specialisation: "))
                            newVet.specialisation.add(input2)
                        } else if (input == "D") {
                            val input2 = readNextInt("Enter an index of specialisation you want to delete: ")
                            newVet.specialisation.removeAt(input2)
                        } else {
                            return
                        }
                        return
                    }

                    4 -> {
                        newVet.salary = readNextDouble("Enter Vet Salary: ")
                        return
                    }

                    5 -> {
                        newVet.position = Utilities.capitalizeFirstLetter(readNextLine("Enter Vet position: "))
                        return
                    }

                    6 -> {
                        println(newVet.formatListStringPatientList())
                        val input2 = readNextInt("Enter an index of Pet you want to delete: ")
                        newVet.patientList.removeAt(input2)
                    }

                    else -> println("Invalid Value")
                }
            } while (choice != -1)

            if (vetAPI.updateVet(
                    indexToUpdate,
                    newVet
                )
            ) {
                println()
                println("✔ Update Successful")
            } else {
                println()
                println(" ❌ Update Failed")
            }
        } else {
            println()
            print("❗ No vets found")
        }
    }
}

fun updateOwner() {
    listAllOwners()
    if (ownerAPI.numberOfOwners() > 0) {
        val indexToUpdate = readNextInt("Enter the Index of the Owner you wish to update: ")
        if (ownerAPI.isValidIndex(indexToUpdate)) {
            val owner1 = ownerAPI.findOwnerByIndex(indexToUpdate)
            val newOwner =
                Owner(
                    owner1!!.PPS,
                    owner1.name,
                    owner1.phoneNumber,
                    owner1.email,
                    owner1.petsList

                )

            println(
                """ 
            
    ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    ┃        Owner        ┃
    ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    ┃   Update:                             ┃
    ┃   𝟭. Owner PPS                       ┃
    ┃   𝟮. Owner Name                       ┃
    ┃   𝟯. Owner Phone Number                           ┃
    ┃   𝟰. Owner Email                       ┃
    ┃   𝟱. Owner Pet List                  ┃
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
                        newOwner.PPS = readNextInt("Enter Owner PPS: ")
                        return
                    }

                    2 -> {
                        newOwner.name = Utilities.capitalizeFirstLetter(readNextLine("Enter Owner Name: "))
                        return
                    }

                    3 -> {
                        newOwner.phoneNumber = Utilities.capitalizeFirstLetter(readNextLine("Enter Owner Phone Number: "))
                        return
                    }

                    4 -> {
                        newOwner.email = Utilities.capitalizeFirstLetter(readNextLine("Enter Owner Email: "))
                        return
                    }

                    5 -> {
                        println(newOwner.formatListStringPatientList())
                        val input2 = readNextInt("Enter an index of Pet you want to delete: ")
                        newOwner.petsList.removeAt(input2)
                    }

                    else -> println("Invalid Value")
                }
            } while (choice != -1)

            if (ownerAPI.updateOwner(
                    indexToUpdate,
                    newOwner
                )
            ) {
                println()
                println("✔ Update Successful")
            } else {
                println()
                println(" ❌ Update Failed")
            }
        } else {
            println()
            print("❗ No vets found")
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
