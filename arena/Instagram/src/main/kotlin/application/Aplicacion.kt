import org.unq.ui.bootstrap.getInstagramSystem
import org.uqbar.arena.Application
import org.uqbar.arena.windows.Window
import window.LoginWindow

class Aplicacion : Application() {
    override fun createMainWindow(): Window<*> {
        var system = getInstagramSystem()
        var model = LoginModel(system)
        return LoginWindow(this, model)
    }
}

fun main() {
    Aplicacion().start()
}
