package ar.com.unqui

import ar.com.unqui.api.controllers.ContentController
import ar.com.unqui.api.controllers.UserController
import ar.com.unqui.model.*
import io.javalin.Javalin
import io.javalin.apibuilder.ApiBuilder.*

fun main(args: Array<String>) {
    val tourTravelAgency = getTourTravelAgency()

    val userController = UserController(tourTravelAgency)
    val contentController = ContentController(tourTravelAgency)

    val app = Javalin.create().start(7000)

    app.routes {
        path("/login") {
            post(userController::login)
        }
        path("/user") {
            path("/:id") {
                get(userController::getUser)
            }
        }
        path("/content") {
            get(contentController::getAll)
            post(contentController::createContent)
        }
    }

}

