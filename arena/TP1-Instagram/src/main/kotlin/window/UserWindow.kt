package window

import model.*
import org.uqbar.arena.kotlin.extensions.*
import org.uqbar.arena.layout.HorizontalLayout
import org.uqbar.arena.widgets.Button
import org.uqbar.arena.widgets.Label
import org.uqbar.arena.widgets.Panel
import org.uqbar.arena.widgets.TextBox
import org.uqbar.arena.windows.SimpleWindow
import org.uqbar.arena.windows.WindowOwner
import org.uqbar.commons.model.exceptions.UserException
import org.uqbar.lacar.ui.model.Action



class UserWindow : SimpleWindow<UserModel> {

    constructor(owner: WindowOwner, model: UserModel) : super(owner, model)

    fun textBox(panel: Panel, ancho: Int, propiedad: String) {
        TextBox(panel) with {
            width = ancho
            bindTo(propiedad)
        }
    }

    fun labelText(panel: Panel, texto: String) {
        Label(panel) with {
            text = texto
            alignLeft()
        }
    }

    fun labelBind(panel: Panel, texto: String) {
        Label(panel) with {
            bindTo(texto)
            alignLeft()
        }
    }

    fun postFieldCheck(post: DraftPostModel) {
        if (post.parameterIsEmpty(post.portrait))    throw UserException("The Portrait field cannot be empty")
        if (post.parameterIsEmpty(post.landscape))   throw UserException("The Landscape field cannot be empty")
        if (post.parameterIsEmpty(post.description)) throw UserException("The Description field cannot be empty")
    }

    fun userFieldCheck(user: EditUserModel) {
        if (user.parameterIsEmpty(user.name))          throw UserException(" The name field cannot be empty ")
        if (user.parameterIsEmpty(user.password))      throw UserException(" The password field cannot be empty ")
        if (user.parameterIsEmpty(user.image))         throw UserException(" The image field cannot be empty ")
        if (user.parameterIsEmpty(user.passwordCheck)) throw UserException(" The password check field cannot be empty ")
    }

    override fun addActions(actionPanel: Panel) {
        Button(actionPanel) with {
            caption = "Add Post"

            onClick {
                val post = DraftPostModel()
                val view = EditPostWindow(thisWindow, post)
                view.onAccept {
                    postFieldCheck(post)
                    modelObject.addPost(post)
                }
                view.open()
            }
        }

        Button(actionPanel) with {
            caption = "Edit Post"
            onClick {
                if (modelObject.selected == null) {
                    throw UserException("Please, select a post")
                }
                val post = DraftPostModel(modelObject.selected!!)
                val view = EditPostWindow(thisWindow, post)
                view.onAccept {
                    postFieldCheck(post)
                    modelObject.editPost(modelObject.selected!!.id, post)
                }
                view.open()
            }
        }

        Button(actionPanel) with {
            caption = "Remove Post"
            onClick {
                if (modelObject.selected == null) {
                    throw UserException("Please, select a post")
                }
                val deleteWindow = DeletePostWindow(this@UserWindow, modelObject.selected!!)
                deleteWindow.onAccept{
                    modelObject.removePost(modelObject.selected!!.id)
                }
                deleteWindow.open()
            }
        }
    }

    override fun createFormPanel(mainPanel: Panel) {
        title = "User View"
        iconImage = "instagram.png"

        val idPanel = Panel(mainPanel)
        idPanel.layout = HorizontalLayout()

        val namePanel = Panel(mainPanel)
        namePanel.layout = HorizontalLayout()

        val emailPanel = Panel(mainPanel)
        emailPanel.layout = HorizontalLayout()


        labelText(idPanel, " id : ")
        labelBind(idPanel, "id")


        labelText(namePanel, "name : ")
        labelBind(namePanel, "name")

        labelText(emailPanel, "email : ")
        labelBind(emailPanel, "email")


        Button(mainPanel) with {
            text = "Edit Profile"
            onClick {

                val user = EditUserModel(modelObject.name, modelObject.password, modelObject.passwordCheck, modelObject.image)
                val view = EditUserWindow(thisWindow,user)
                view.onAccept {
                    userFieldCheck(user)
                    modelObject.editUser(user)
                }
                view.open()
            }
        }

        labelText(mainPanel, "--------------------------------------------------------------------------------------------------------------------")

        val botonPanel = Panel(mainPanel)
        botonPanel.layout = HorizontalLayout()

        textBox(botonPanel, 250, "description")

        Button(botonPanel) with {
            caption = "Search"
            onClick(Action { modelObject.filterByDescription(modelObject.description) })
        }

        Button(botonPanel) with {
            caption = "Back"
            onClick(Action {
                modelObject.resetPost()
            })
        }

        table<PostModel>(mainPanel) {
            bindItemsTo("posts")
            bindSelectionTo("selected")
            visibleRows = 15

            column {
                title = "#"
                bindContentsTo("id")
                fixedSize = 50
            }
            column {
                title = "Description"
                bindContentsTo("description")
                fixedSize = 300
            }
            column {
                title = "Landscape"
                bindContentsTo("landscape")
                fixedSize = 100
            }
            column {
                title = "Portrait"
                bindContentsTo("portrait")
                fixedSize = 100
            }
        }
    }
}