import controllers.OwnerAPI
import controllers.PetAPI
import controllers.VetAPI
import persistence.XMLSerializer
import java.io.File

private val petAPI = PetAPI(XMLSerializer(File("pets.xml")))
private val vetAPI = VetAPI(XMLSerializer(File("vets.xml")))
private val ownerAPI = OwnerAPI(XMLSerializer(File("owners.xml")))

fun main(args: Array<String>) {
    println("models.Vet System V1.0")
    println("test")
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
