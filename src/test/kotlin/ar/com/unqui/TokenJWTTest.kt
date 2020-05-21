package ar.com.unqui

import ar.com.unqui.api.NotFoundToken
import ar.com.unqui.api.TokenJWT
import ar.com.unqui.model.User
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class TokenJWTTest {

    val user = User("u_2", "juan@gmail.com", "juan", "juan", false)

    @Test
    fun testGenerateToken() {
        val tokenJWT = TokenJWT()
        assertEquals(tokenJWT.generateToken(user), "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpZCI6InVfMiJ9.szOPi2BKieefm9rLmUQUcuvVJWoX9jc6kl55obH016I")
    }

    @Test
    fun testGenerateTokenWithAnotherUser() {
        val tokenJWT = TokenJWT()
        assertNotEquals(tokenJWT.generateToken(user), "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6InVfNDIifQ.rDbcOOadJCbMhHcqKmsypTOFHfQ8tcB59HRurJZp1xw")
    }

    @Test
    fun testValidateToken() {
        val tokenJWT = TokenJWT()
        assertEquals(tokenJWT.validate("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpZCI6InVfMiJ9.szOPi2BKieefm9rLmUQUcuvVJWoX9jc6kl55obH016I"), "u_2")
    }

    @Test(expected = NotFoundToken::class)
    fun testValidateTokenWithInvalidToken() {
        val tokenJWT = TokenJWT()
        tokenJWT.validate("eyJhbGciOiJIUzM4NCIsInR5cCI6IkpXVCJ9.eyJuYW1lIjoidV8yIn0.W0pTotEVrwg-RQGllSBw4Hew7ODcUtQmAPwOPigNptufgpXAK7WpqTulGyA7hqE0")
    }

}
