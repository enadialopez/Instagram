package window

import model.EditUserModel
import org.uqbar.arena.kotlin.extensions.*
import org.uqbar.arena.widgets.*
import org.uqbar.arena.windows.Dialog
import org.uqbar.arena.windows.WindowOwner
import org.uqbar.commons.model.exceptions.UserException

class EditUserWindow(owner: WindowOwner, model: EditUserModel) : Dialog<EditUserModel>(owner,model) {

    fun textBox(panel: Panel, propiedad: String){
        TextBox(panel) with {
            bindTo(propiedad)
        }
    }

    fun labelText(panel:Panel ,texto: String ){
        Label(panel) with {
            text = texto
        }
    }

    fun passwordField(panel: Panel, propiedad : String){
        PasswordField(panel) with {
            bindTo(propertyName = propiedad)
        }
    }

    override fun createFormPanel(mainPanel: Panel) {
        title = "Edit Profile"
        iconImage = "instagram.png"

        labelText(mainPanel,"Name")
        textBox(mainPanel,"name")

        labelText(mainPanel,"Password")
        passwordField(mainPanel,"password")

        labelText(mainPanel,"Password confirmation")
        passwordField(mainPanel,"passwordCheck")

        labelText(mainPanel,"Image")
        textBox(mainPanel,"image")

        Button(mainPanel) with {
            text = "Accept"
            onClick {
                if ( modelObject.passwordsDontMatch()) {
                    throw UserException("Passwords do not match")
                }
                accept()
            }
        }

        Button(mainPanel) with {
            text = "Cancel"
            onClick {
                cancel()
            }
        }
    }
}