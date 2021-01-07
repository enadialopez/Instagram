package window

import model.DraftPostModel
import model.PostModel
import org.uqbar.arena.kotlin.extensions.*
import org.uqbar.arena.layout.HorizontalLayout
import org.uqbar.arena.widgets.Button
import org.uqbar.arena.widgets.Label
import org.uqbar.arena.widgets.Panel
import org.uqbar.arena.widgets.TextBox
import org.uqbar.arena.windows.Dialog
import org.uqbar.arena.windows.WindowOwner

class EditPostWindow(owner: WindowOwner, model: DraftPostModel) : Dialog<DraftPostModel>(owner,model) {

    fun textBox(panel: Panel, propiedad: String, ancho: Int){
        TextBox(panel) with {
            bindTo(propiedad)
            width = ancho
        }
    }

    fun labelText(panel:Panel ,texto: String ){
        Label(panel) with {
            text = texto
        }
    }

    override fun createFormPanel(mainPanel: Panel) {
        title = "Edit Post"
        iconImage = "instagram.png"

        labelText(mainPanel,"Portrait")
        textBox(mainPanel,"portrait",250)

        labelText(mainPanel,"Landscape")
        textBox(mainPanel,"landscape", 250)

        labelText(mainPanel,"Description")
        textBox(mainPanel,"description", 250)

        Button(mainPanel) with {
            text = "Accept"
            onClick {
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