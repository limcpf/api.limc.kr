package api.limc.kr.blog.config.auth

import api.limc.kr.blog.domain.admin.dto.AdminDto
import io.jsonwebtoken.*
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.*

@Component
class JwtTokenManager {
    @Value("\${JWT_SECRET_KEY}")
    private lateinit var JWT_SECRET_KEY: String

    fun getToken(adminDto: AdminDto, tokenType: Long): String = generateToken(adminDto.name, tokenType)

    private fun generateToken(sub: String, dueSec: Long): String {
        val claims: Claims = Jwts.claims().setSubject(sub)
        val currentTimeMillis = System.currentTimeMillis()

        return Jwts.builder()
            .setClaims(claims)
            .setIssuer("limckr")
            .setIssuedAt(Date(currentTimeMillis))
            .setExpiration(Date(currentTimeMillis + dueSec * 10000 * 400))
            .signWith(SignatureAlgorithm.HS256, JWT_SECRET_KEY)
            .compact()
    }

    fun validateToken(authToken: String): Boolean {
        return try {
            getAllClaimsFromToken(authToken)
            true
        } catch (exception: ExpiredJwtException) {
            exception.printStackTrace()
            false
        } catch (exception: JwtException) {
            exception.printStackTrace()
            false
        } catch (exception: NullPointerException) {
            exception.printStackTrace()
            false
        }
    }

    fun getUserNameFromToken(token: String): String = getAllClaimsFromToken(token).subject

    private fun getAllClaimsFromToken(token: String): Claims = Jwts.parser().setSigningKey(JWT_SECRET_KEY).parseClaimsJws(token).body

    fun isTokenExpired(token: String): Boolean = getExpirationDateFromToken(token).before(Date())

    private fun getExpirationDateFromToken(token: String): Date = getAllClaimsFromToken(token).expiration
}