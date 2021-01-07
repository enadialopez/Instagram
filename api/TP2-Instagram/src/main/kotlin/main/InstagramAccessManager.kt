package main

import io.javalin.core.security.AccessManager
import io.javalin.core.security.Role
import io.javalin.http.Context
import io.javalin.http.Handler
import io.javalin.http.NotFoundResponse
import io.javalin.http.UnauthorizedResponse
import org.unq.ui.model.InstagramSystem
import org.unq.ui.model.NotFound
import token.NotValidToken
import token.TokenController

class InstagramAccessManager(val igSys: InstagramSystem) : AccessManager {

    val tokenController = TokenController()

    override fun manage(handler: Handler, ctx: Context, roles: MutableSet<Role>) {
        val token = ctx.header("Authorization")
        when{
            roles.contains(IgRoles.ANYONE) -> handler.handle(ctx)
            token === null -> throw UnauthorizedResponse()
            roles.contains(IgRoles.USER) ->{
                try{
                    val userId = tokenController.validateToken(token)
                    igSys.getUser(userId)
                    ctx.attribute("userId", userId)
                    handler.handle(ctx)
                } catch(e: NotValidToken){
                    throw  UnauthorizedResponse("Not valid Token")
                } catch(e: NotFound){

                    throw UnauthorizedResponse("Not valid User")
                }
            }
        }
    }

}

enum class IgRoles: Role {
    ANYONE,
    USER
}