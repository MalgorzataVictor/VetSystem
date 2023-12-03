import controllers.OwnerAPI
import controllers.PetAPI
import controllers.VetAPI
import persistence.XMLSerializer
import utils.ScannerInput
import java.io.File

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
        Enter option➡️"""
    )
}

fun runMainMenu() {
    do {
        when (val option = mainMenu()) {
            1 -> petMenu()
            2 -> vetMenu()
            3 -> ownerMenu()
            0 -> exitApp()
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
