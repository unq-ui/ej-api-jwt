package ar.com.unqui.model

data class User(val id: String, val email: String, val name: String, val password: String, val isAdmin: Boolean)
data class Tour(val id: String, val title: String, val charge:Double, val amountOfPeople:Int, val city: String)

class TourTravelAgency(val users: MutableList<User> = mutableListOf(), val tours: MutableList<Tour> = mutableListOf()) {

    fun addTour(tour: Tour) {
        val none = tours.none { it.title == tour.title }
        if (none) tours.add(tour) else throw DuplicateItemException("Tour exist")
    }

    fun login(email: String, password: String): User {
        return users.find { it.email == email && it.password == password } ?: throw NotFoundUser()
    }

    fun getUserById(id: String): User {
        return users.find { it.id == id } ?: throw NotFoundUser()
    }

}

fun getTourTravelAgency(): TourTravelAgency {
    return TourTravelAgency(
        mutableListOf(
            User("u_1", "lean@gmail.com", "lean", "lean", true),
            User("u_2", "juan@gmail.com", "juan", "juan", false),
            User("u_3", "facu@gmail.com", "facu", "facu", false)
        ),
        mutableListOf(
            Tour("t_1", "Tour Gratis de Barcelona", 0.0, 15, "Barcelona"),
            Tour("t_2", "Tour sobre Gaudí y el modernismo", 1200.0, 15, "Barcelona"),
            Tour("t_3", "Tour en bici por Barcelona", 1800.0, 10, "Barcelona"),
            Tour("t_4", "Ruta de tapas por Barcelona", 2900.0, 25, "Barcelona"),
            Tour("t_5", "Tour de la Barcelona Prohibida", 2000.0, 15, "Barcelona")
        )
    )

}