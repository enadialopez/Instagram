package model

import org.unq.ui.model.DraftPost
import org.unq.ui.model.InstagramSystem
import org.unq.ui.model.User
import org.uqbar.commons.model.annotations.Observable

@Observable
class UserModel(user : User,val system : InstagramSystem) {
    var id : String = user.id
    var name : String = user.name
    var email : String = user.email
    var posts = allPost()
    var postDuplicate = allPost()
    var selected : PostModel? = null
    var description : String = ""
    var image : String = user.image
    var password : String = user.password
    var passwordCheck: String = user.password

    fun filterByDescription(description : String) {
        this.posts = posts.filter { it.description.contains(description) }
    }

    fun resetPost() {
        this.posts = postDuplicate
    }

    fun allPost() : List<PostModel> {
        var posts = system.searchByUserId(id).map { PostModel(it.id, it.description, it.landscape, it.portrait) }
        return posts
    }

    fun editPost(postId : String, post : DraftPostModel) {
        system.editPost(postId, DraftPost(post.portrait, post.landscape, post.description))
        posts = allPost()
    }

    fun editUser(user : EditUserModel) {
        system.editProfile(id,user.name,user.password,user.image)
        this.name = user.name
        this.password = user.password
        this.passwordCheck = user.passwordCheck
        this.image = user.image
    }


    fun removePost(postId: String){
        system.deletePost(postId)
        posts = allPost()
    }

    fun addPost(post: DraftPostModel) {
        system.addPost (id, DraftPost(post.portrait, post.landscape, post.description))
        posts= allPost()

    }
}