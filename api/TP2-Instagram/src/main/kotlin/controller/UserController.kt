package controller

import dto.*
import io.javalin.http.Context
import org.unq.ui.model.InstagramSystem
import org.unq.ui.model.NotFound
import org.unq.ui.model.UsedEmail
import token.TokenController
import java.time.LocalDateTime

class UserController(private val instagramSystem : InstagramSystem) {

    val tokenJWT = TokenController()

    private fun validateLoginUser(ctx: Context) {
        val user = ctx.bodyValidator<UserLoginDTO>()
                .check({ it.email.isNotEmpty() }, "Email cannot be empty")
                .check({
                    "^[a-zA-Z0-9.!#$%&'+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)$"
                            .toRegex()
                            .matches(it.email)
                }, "Invalid email address")
                .check({ it.password.isNotEmpty() }, "Password cannot by empty")
                .get()
    }

    private fun validateRegisterUser(ctx: Context) {
        val user = ctx.bodyValidator<UserRegisterDTO>()
                .check({ it.name.isNotEmpty() }, "Name cannot be empty")
                .check({ it.email.isNotEmpty() }, "Email cannot be empty")
                .check({
                    "^[a-zA-Z0-9.!#$%&'+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)$"
                            .toRegex()
                            .matches(it.email)
                }, "Invalid email address")
                .check({ it.password.isNotEmpty() }, "Password cannot be empty")
                .check({ it.image.isNotEmpty() }, "Image cannot be empty")
                .get()
    }


    fun login(ctx: Context) {
        val userLogin = ctx.body<UserLoginDTO>()
        try {
            validateLoginUser(ctx)
            val user = instagramSystem.login(userLogin.email, userLogin.password)
            ctx.header("Authorization", tokenJWT.generateToken(user))
            ctx.status(200)
            ctx.json(
                    mapOf("result" to "ok")
            )
        } catch (e: NotFound) {
            ctx.status(404)
            ctx.json(
                    mapOf("result" to "error",
                            "message" to e.message)
            )
        }
    }

    fun register(ctx: Context) {
        val userRegister = ctx.body<UserRegisterDTO>()
        try {
            validateRegisterUser(ctx)
            val user = instagramSystem.register(userRegister.name, userRegister.email, userRegister.password, userRegister.image)
            ctx.header("Authorization", tokenJWT.generateToken(user))
            ctx.status(201)
            ctx.json(
                    mapOf("result" to "ok")
            )
        } catch (e: UsedEmail) {
            ctx.status(404)
            ctx.json(
                    mapOf("result" to "error",
                            "message" to e.message)
            )
        }
    }


    fun getUserById(ctx: Context) {
        val userId = ctx.pathParam("id")
        try {
            val user = instagramSystem.getUser(userId)
            val posts = instagramSystem.searchByUserId(userId)
            var userPost = UserPostDTO(user.name, user.image)

            val followersUser = user.followers.map {
                UserPostDTO(it.name, it.image)
            }.toMutableList()
            val postsUser = posts.map {
                val likes = it.likes.map {
                    UserPostDTO(it.name, it.image)
                }.toMutableList()
                PostUserDTO(it.id, it.description, it.portrait, it.landscape, it.date, likes, userPost)
            }.toMutableList()

            ctx.json(
                    UserGetDTO(user.name, user.image, followersUser, postsUser)
            )
        } catch (e: NotFound) {
            ctx.status(404)
            ctx.json(
                    mapOf("result" to "Not found user with : $userId")
            )
        }
    }


    fun followerUser(ctx: Context) {
        val token = ctx.header("Authorization")
        val toUserId = tokenJWT.validateToken(token!!)
        val fromUser = ctx.pathParam("id")
        if (toUserId != fromUser) {
            try {
                instagramSystem.updateFollower(fromUser, toUserId)
                ctx.status(200)
                ctx.json(
                        mapOf(
                                "result" to "ok"
                        )
                )
            } catch (e: NotFound) {
                ctx.status(404)
                ctx.json(
                        mapOf("result" to "Not found user with : $fromUser")
                )
            }
        } else {
            ctx.json(
                    mapOf("result" to "Both ID are equals")
            )
        }
    }

    fun getUser(ctx: Context) {
        val token = ctx.header("Authorization")
        val userId = tokenJWT.validateToken(token!!)
        val user = instagramSystem.getUser(userId)
        val userTimeline = instagramSystem.timeline(userId).map {
            val likes = it.likes.map {
                UserPostDTO(it.name, it.image)
            }.toMutableList()
            PostUserDTO(it.id, it.description, it.portrait, it.landscape, it.date, likes, UserPostDTO(it.user.name, it.user.image))
        }.toMutableList()
        val followersUser = user.followers.map {
            UserPostDTO(it.name, it.image)
        }.toMutableList()

        ctx.status(200)
        ctx.json(
                UserTimelineDTO(userId, user.name, user.image, followersUser, userTimeline)
        )
    }
}
