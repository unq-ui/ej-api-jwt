package ar.com.unqui.api.controllers

import ar.com.unqui.api.CreateTour
import ar.com.unqui.model.DuplicateItemException
import ar.com.unqui.model.Tour
import ar.com.unqui.model.TourTravelAgency
import io.javalin.http.BadRequestResponse
import io.javalin.http.Context

class ContentController(val tourTravelAgency: TourTravelAgency) {

    fun getAll(ctx: Context) {
        ctx.json(tourTravelAgency.tours)
    }

    fun createContent(ctx: Context) {
        val createTour = ctx.bodyAsClass(CreateTour::class.java)
        val tour = Tour("t_${tourTravelAgency.tours.size + 1}", createTour.title, createTour.charge, createTour.amountOfPeople, createTour.city)
        try {
            tourTravelAgency.addTour(tour)
        } catch (e: DuplicateItemException) {
            throw BadRequestResponse("Another tour with the same name")
        }
    }
}