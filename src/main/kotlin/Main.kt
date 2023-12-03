import controllers.OwnerAPI
import controllers.PetAPI
import controllers.VetAPI
import persistence.XMLSerializer
import utils.ScannerInput
import java.io.File
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
            /*   1 -> //addPet()
             2 -> deletePet()
              3 -> listAllPets()
              4 -> updatePet()
              5 -> numberOfPets()
              6 -> searchPet
              0 -> runMainMenu()*/
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
