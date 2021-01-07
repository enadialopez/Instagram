import model.ParameterIsEmpty
import org.unq.ui.model.InstagramSystem
import org.unq.ui.model.User
import org.uqbar.commons.model.annotations.Observable

@Observable
class LoginModel (val system: InstagramSystem): ParameterIsEmpty {
    var email : String = ""
    var password : String = ""

    fun login(email : String,password : String) : User {
        return system.login(email,password)
    }
}