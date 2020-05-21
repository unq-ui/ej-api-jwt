package ar.com.unqui.api

import ar.com.unqui.Roles
import ar.com.unqui.model.NotFoundUser
import ar.com.unqui.model.TourTravelAgency
import ar.com.unqui.model.User
import io.javalin.core.security.AccessManager
import io.javalin.core.security.Role
import io.javalin.http.Context
import io.javalin.http.Handler
import io.javalin.http.UnauthorizedResponse

class JWTAccessManager(val tokenJWT: TokenJWT, val tourTravelAgency: TourTravelAgency): AccessManager {

    fun getUser(token: String): User {
        try {
            val userId = tokenJWT.validate(token)
            return tourTravelAgency.getUserById(userId)
        } catch (e: NotFoundToken) {
            throw UnauthorizedResponse("Token not found")
        } catch (e: NotFoundUser) {
            throw UnauthorizedResponse("Invalid Token")
        }
    }

    override fun manage(handler: Handler, ctx: Context, roles: MutableSet<Role>) {
        val token = ctx.header("Authorization")
        when {
            token == null && roles.contains(Roles.ANYONE) -> handler.handle(ctx)
            token == null -> throw UnauthorizedResponse("Token not found")
            roles.contains(Roles.ANYONE) -> handler.handle(ctx)
            roles.contains(Roles.USER) -> {
                getUser(token)
                handler.handle(ctx)
            }
            roles.contains(Roles.ADMIN) -> {
                val user = getUser(token)
                if (!user.isAdmin) throw UnauthorizedResponse("Invalid Token")
                handler.handle(ctx)
            }
        }
    }
}