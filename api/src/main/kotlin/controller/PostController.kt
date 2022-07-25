package controller

import dto.*
import io.javalin.http.BadRequestResponse
import org.unq.ui.model.InstagramSystem
import io.javalin.http.Context
import org.unq.ui.model.DraftComment
import org.unq.ui.model.NotFound
import token.TokenController

class PostController(private val instagramSystem : InstagramSystem) {

    val tokenJwt = TokenController()

    private fun getUserId(ctx: Context): String {
        return ctx.attribute<String>("userId") ?: throw BadRequestResponse("Not found user")
    }

    private fun validateComment(ctx : Context) {
        val comment = ctx.bodyValidator<DraftComment>()
        .check({it.body.isNotEmpty()}, "Comment cannot be empty")
        .get()
    }

    fun getPostById(ctx: Context) {
        val postId = ctx.pathParam("id")
        try {
            val post = instagramSystem.getPost(postId)
            var likesPost = post.likes.map {
                UserPostDTO(it.name, it.image) }.toMutableList()
            var commentPost = post.comments.map {
                CommentDTO(it.id, it.body, UserPostDTO(it.user.name,it.user.image))
            }.toMutableList()
            var userPost = UserPostDTO(post.user.name,post.user.image)

            ctx.json(
                PostDTO(postId,post.description,post.portrait,post.landscape,post.date,likesPost,userPost,commentPost)
            )
        } catch (e: NotFound ) {
            ctx.status(404)
            ctx.json(
                    mapOf("result" to "Not found post with : $postId")
            )
        }

    }


    fun likePost(ctx: Context) {
        val userId = getUserId(ctx)
        val postId = ctx.pathParam("id")
        try {
            instagramSystem.updateLike(postId, userId)
            ctx.json(OkResponse())
        } catch (e: NotFound) {
            ctx.status(404).json(ErrorResponse("not found post with id $postId"))
        }
    }


    fun commentPost(ctx: Context) {
        val userId = getUserId(ctx)
        val postId = ctx.pathParam("id")
        val comment = ctx.body<DraftComment>()
        validateComment(ctx) ;

        try {
            instagramSystem.addComment(postId, userId, comment)
            ctx.status(200)
            ctx.json(OkResponse())
        } catch (e: NotFound) {
            ctx.status(404).json(ErrorResponse("not found post with id $postId"))
        }
    }
}