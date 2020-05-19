package ar.com.unqui.api

data class LoginUser(val email: String, val password: String)
data class CreateTour( val title: String, val charge:Double, val amountOfPeople:Int, val city: String)