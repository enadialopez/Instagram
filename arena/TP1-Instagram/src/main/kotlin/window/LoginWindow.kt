package window

import LoginModel
import model.RegisterModel
import model.UserModel
import org.uqbar.arena.kotlin.extensions.*
import org.uqbar.arena.widgets.*
import org.uqbar.arena.windows.SimpleWindow
import org.uqbar.arena.windows.WindowOwner
import org.uqbar.commons.model.exceptions.UserException
import org.unq.ui.model.NotFound
import java.awt.Color

class LoginWindow : SimpleWindow<LoginModel> {
    constructor(owner: WindowOwner, model: LoginModel) : super(owner, model)

    fun textBox(panel: Panel, propiedad: String, widthP : Int){
        TextBox(panel) with {
            width = widthP
            bindTo(propiedad)
        }
    }

    fun labelText(panel:Panel ,texto: String ){
        Label(panel) with {
            text = texto
            alignLeft()
        }
    }

    fun passwordField(panel: Panel, pass : String){
        PasswordField(panel) with {
            bindTo(propertyName = pass)
        }
    }

    override fun addActions(mainPanel: Panel) {}

    override fun createFormPanel(mainPanel: Panel) {
        title = "Instagram"
        iconImage = "instagram.png"

        labelText(mainPanel,"Email")
        textBox(mainPanel,"email", 250)

        labelText(mainPanel,"Password")
        passwordField(mainPanel, "password")


        Button(mainPanel) with {
            caption = "Login"
            onClick {

                if ( modelObject.parameterIsEmpty(modelObject.password)) {
                    throw UserException(" The password field cannot be empty ")
                }
                if ( modelObject.parameterIsEmpty(modelObject.email)) {
                    throw UserException(" The email field cannot be empty ")
                }
                try {
                    var user = modelObject.login(modelObject.email,modelObject.password)
                    var model = UserModel(user, modelObject.system)
                    thisWindow.close() ; UserWindow(thisWindow, model).open()
                } catch (e : NotFound ) {
                    throw UserException("Email or password incorrect")
                }
            }
        }

        Label(mainPanel) with {
            text = "Don't have an account?"
            bgColor = Color.CYAN
        }
        Button(mainPanel) with {
            caption = "Sign Up"
            onClick {
                val model = RegisterModel(modelObject.system)
                RegisterWindow(thisWindow, model).open()
            }
        }
    }
}