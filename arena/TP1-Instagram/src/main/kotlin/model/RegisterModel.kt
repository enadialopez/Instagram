package model

import org.unq.ui.model.InstagramSystem
import org.unq.ui.model.User
import org.uqbar.commons.model.annotations.Observable
import java.util.regex.Pattern

@Observable
class RegisterModel(val system: InstagramSystem): ParameterIsEmpty {
    var name : String = ""
    var email : String = ""
    var password : String = ""
    var passwordCheck : String = ""
    var image : String = ""

    fun register(name : String, email : String, pass : String,image : String) : User {
        return system.register(name, email, pass, image)
    }

    fun passwordAndPasswordCheckAreEqual(): Boolean {
        return (password == passwordCheck)
    }

    fun notValidEmail(): Boolean {
        return Pattern.compile(
            "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]|[\\w-]{2,}))@"
            + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
            + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
            + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
            + "[0-9]{1,2}|25[0-5]|2[0-4][0-9]))|"
            + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$"
        ).matcher(email).matches()
    }

    fun notValidUserNameLenght(): Boolean {
        return name.length < 3
    }
}
