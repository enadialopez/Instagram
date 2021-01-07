import controller.InstagramController
import controller.PostController
import controller.UserController
import io.javalin.Javalin
import io.javalin.apibuilder.ApiBuilder.*
import io.javalin.core.util.RouteOverviewPlugin
import main.IgRoles
import main.InstagramAccessManager
import org.unq.ui.bootstrap.getInstagramSystem


class InstagramApi {

    fun start() {
        val instagramSystem = getInstagramSystem()
        val postController = PostController(instagramSystem)
        val userController = UserController(instagramSystem)
        val instagramController = InstagramController(instagramSystem)

        val app = Javalin.create() {
            it.defaultContentType = "application/json"
            it.registerPlugin(RouteOverviewPlugin("/routes"))
            it.accessManager(InstagramAccessManager(instagramSystem))
            it.enableCorsForAllOrigins()
        }

        app.before {
            it.header("Access-Control-Expose-Headers","*")
        }

        app.start(7000)

        app.routes {
            path("post") {
                path(":id") {
                    get(postController::getPostById, setOf(IgRoles.USER))
                        path("like") {
                            put(postController::likePost, setOf(IgRoles.USER))
                        }
                        path ("comment"){
                            post(postController::commentPost, setOf(IgRoles.USER))
                        }
                    }
            }
            path("user") {
                get(userController::getUser, setOf(IgRoles.USER))
                    path(":id") {
                        get(userController::getUserById, setOf(IgRoles.USER))
                            path("follow") {
                                put(userController::followerUser, setOf(IgRoles.USER))
                            }
                     }
            }
            path("search") {
                get(instagramController::getSearchContent, setOf(IgRoles.USER))
            }

            path("login") {
                post(userController::login, setOf(IgRoles.ANYONE))

            }
            path("register") {
                post(userController::register, setOf(IgRoles.ANYONE))
            }

        }
    }
}



fun main(args: Array<String>) {
    InstagramApi().start()
}
