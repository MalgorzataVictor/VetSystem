import java.time.LocalDate

class Vet (
    var vetID: Int,
    var name: String,
    var dateQualified: LocalDate,
    var specialisation: MutableList<Pet> = mutableListOf(),
    var salary: Double,
    var position: String,
    var patientList: MutableList<Pet> = mutableListOf()
        )
