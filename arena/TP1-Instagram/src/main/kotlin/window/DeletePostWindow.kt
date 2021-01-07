package window

import model.PostModel
import org.uqbar.arena.kotlin.extensions.*
import org.uqbar.arena.widgets.Button
import org.uqbar.arena.widgets.Label
import org.uqbar.arena.widgets.Panel
import org.uqbar.arena.windows.Dialog
import org.uqbar.arena.windows.WindowOwner

class DeletePostWindow(owner: WindowOwner, model: PostModel): Dialog<PostModel>(owner, model){
    override fun createFormPanel(mainPanel: Panel?) {
        Label(mainPanel) with {
            text = "Are you shure you want to delete this post with id: ${modelObject.id}?"
        }

        Button(mainPanel) with {
            caption = "Accept"
            onClick{
                accept()
            }
        }
        Button(mainPanel) with {
            caption = "Cancel"
            onClick{
                cancel()
            }
        }
    }

}