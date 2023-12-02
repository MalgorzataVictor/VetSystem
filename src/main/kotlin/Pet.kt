import java.time.LocalDate

data class Pet (
    var petID: Int,
    var name: String,
    var breed: String,
    var DOB: LocalDate,
    var isVaccinated: Boolean,
    var vetID: Int,
    var PPS: Int


)