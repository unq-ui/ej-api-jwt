package ar.com.unqui

import ar.com.unqui.api.JWTAccessManager
import ar.com.unqui.api.TokenJWT
import ar.com.unqui.api.controllers.ContentController
import ar.com.unqui.api.controllers.UserController
import ar.com.unqui.model.*
import io.javalin.Javalin
import io.javalin.apibuilder.ApiBuilder.*
import io.javalin.core.security.Role

enum class Roles : Role {
    ANYONE, USER, ADMIN
}

fun main(args: Array<String>) {
    val tourTravelAgency = getTourTravelAgency()

    val tokenJWT = TokenJWT()

    val userController = UserController(tourTravelAgency, tokenJWT)
    val contentController = ContentController(tourTravelAgency)

    val jwtAccessManager = JWTAccessManager(tokenJWT, tourTravelAgency)

    val app = Javalin.create {
        it.defaultContentType = "application/json"
        it.accessManager(jwtAccessManager)
    }.start(7000)

    app.routes {
        path("/login") {
            post(userController::login, mutableSetOf<Role>(Roles.ANYONE))
        }
        path("/user") {
            path("/:id") {
                get(userController::getUser, mutableSetOf<Role>(Roles.USER, Roles.ADMIN))
            }
        }
        path("/content") {
            get(contentController::getAll, mutableSetOf<Role>(Roles.USER, Roles.ADMIN))
            post(contentController::createContent, mutableSetOf<Role>(Roles.ADMIN))
        }
    }

}

