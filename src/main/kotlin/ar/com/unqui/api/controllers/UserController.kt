package ar.com.unqui.api.controllers

import ar.com.unqui.api.LoginUser
import ar.com.unqui.model.NotFoundUser
import ar.com.unqui.model.TourTravelAgency
import io.javalin.http.Context
import io.javalin.http.NotFoundResponse

class UserController(val tourTravelAgency: TourTravelAgency) {

    fun login(ctx: Context) {
        val loginUser = ctx.bodyAsClass(LoginUser::class.java)
        try {
            val user = tourTravelAgency.login(loginUser.email, loginUser.password)
            ctx.json(user)
        } catch (e: NotFoundUser) {
            throw NotFoundResponse("Wrong email or password")
        }
    }

    fun getUser(ctx: Context) {
        try {
            val userId = ctx.pathParam("id")
            val user = tourTravelAgency.getUserById(userId)
            ctx.json(user)
        } catch (e: NotFoundUser) {
            throw NotFoundResponse("User not found")
        }
    }
}
