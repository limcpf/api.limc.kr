package api.limc.kr.blog.config.auth

import api.limc.kr.blog.config.exception.LimcException
import api.limc.kr.blog.config.exception.enums.LimcResponseCode
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Service
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException


@Service
class JwtAuthenticationFilter:OncePerRequestFilter() {
    @Autowired
    private lateinit var jwtTokenManager: JwtTokenManager
    @Autowired
    private lateinit var userDetailService: UserDetailServiceImpl
    private val AUTHORIZATION_HEADER = "Authorization"

    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        try {
            val token = getTokenFromRequest(request)
            if (token.isNotBlank() && jwtTokenManager.validateToken(token)) {
                val name: String = jwtTokenManager.getUserNameFromToken(token)
                val userDetails: UserDetails = userDetailService.loadUserByUsername(name)
                val authenticationToken = UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)
                authenticationToken.details = WebAuthenticationDetailsSource().buildDetails(request)
                SecurityContextHolder.getContext().authentication = authenticationToken
            }
        } catch (e: Exception) {
            e.printStackTrace()
            throw LimcException(LimcResponseCode.NOT_FOUND)
        }
        filterChain.doFilter(request, response)
    }

    private fun getTokenFromRequest(request: HttpServletRequest): String {
        val bearerToken = request.getHeader(AUTHORIZATION_HEADER)
        return if (!bearerToken.isNullOrBlank() && bearerToken.startsWith("Bearer ")) {
            bearerToken.substring(7)
        } else ""
    }
}