package token

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTCreator
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import javalinjwt.JWTGenerator
import javalinjwt.JWTProvider
import org.unq.ui.model.User


class NotValidToken : Exception("not valid token")

class TokenController {

    val algorithm: Algorithm = Algorithm.HMAC256("pass")

    var generator: JWTGenerator<User> = JWTGenerator<User> { user: User, alg: Algorithm? ->
        val token: JWTCreator.Builder = JWT.create()
            .withClaim("id", user.id)
        token.sign(alg)
    }

    var verifier: JWTVerifier = JWT.require(algorithm).build()

    var provider = JWTProvider(algorithm, generator, verifier)

    fun generateToken(usr: User): String {
        return provider.generateToken(usr)
    }

    fun validateToken(tkn: String): String {
        val decodeJWT = provider.validateToken(tkn)
        if (decodeJWT.isPresent() && decodeJWT.get().claims.contains("id")) {
            return decodeJWT.get().getClaim("id").asString()
        }
        throw NotValidToken()
    }
}




