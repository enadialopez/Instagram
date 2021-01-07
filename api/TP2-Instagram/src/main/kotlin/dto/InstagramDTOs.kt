package dto

import java.time.LocalDateTime

data class OkResponse(val status: String = "Ok")
data class ErrorResponse(val message: String)

data class UserLoginDTO(val email: String, val password: String)
data class UserRegisterDTO(val name:String,val email:String,val password: String,val image:String)

data class PostUserDTO(val id: String,
                       val description: String,
                       val portrait: String,
                       val landscape: String,
                       val date: LocalDateTime,
                       val likes: MutableList<UserPostDTO>,
                       val user: UserPostDTO)

data class UserGetDTO(val name:String,
                      val image:String,
                      val followers: MutableList<UserPostDTO>,
                      val posts: MutableList<PostUserDTO>)

data class UserTimelineDTO (val id: String,
                            val name: String,
                            val image: String,
                            val followers: MutableList<UserPostDTO>,
                            val timeline : MutableList<PostUserDTO>)

data class UserSearchDTO(val name : String, val image: String, val followers : MutableList<UserPostDTO>)
data class DescriptionPostDTO(val posts: MutableList<PostUserDTO>)
data class UserByNameDTO(val users: MutableList<UserSearchDTO>)

data class UserPostDTO(val name: String,
                       val image: String)

data class CommentDTO(val id: String,
                      val body: String,
                      val user: UserPostDTO)

data class PostDTO(val id: String,
                   val description: String,
                   val portrait: String,
                   val landscape: String,
                   val date: LocalDateTime,
                   val likes: MutableList<UserPostDTO>,
                   val user: UserPostDTO,
                   val comments: MutableList<CommentDTO>)