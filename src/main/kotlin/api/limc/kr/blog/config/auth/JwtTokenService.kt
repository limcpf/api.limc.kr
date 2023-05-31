package api.limc.kr.blog.config.auth

import api.limc.kr.blog.config.exception.LimcException
import api.limc.kr.blog.config.exception.enums.LimcResponseCode
import api.limc.kr.blog.domain.admin.Admin
import api.limc.kr.blog.domain.admin.AdminRepository
import api.limc.kr.blog.domain.admin.dto.AdminDto
import api.limc.kr.blog.domain.admin.dto.LoginDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.AuthenticationException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service


@Service
class JwtTokenService {
    @Autowired private lateinit var adminRepository:AdminRepository
    @Autowired private lateinit var jwtTokenManager: JwtTokenManager
    @Autowired private lateinit var authenticationManager: AuthenticationManager
    @Autowired private lateinit var bCryptPasswordEncoder: BCryptPasswordEncoder

    fun getLoginResponse(adminDto: AdminDto): LoginDto {
        val name: String = adminDto.name

        lateinit var admin:Admin

        try {
            admin = adminRepository.findAdminByName(name)
        } catch (e: Exception) {
            throw LimcException(LimcResponseCode.INVALID_ID_PARAMETER)
        }

        if (!bCryptPasswordEncoder.matches(adminDto.password, admin.password)) {
            throw LimcException(LimcResponseCode.INVALID_PASSWORD_PARAMETER)
        }

        val aDto: AdminDto = admin.toDto()
        val token = UsernamePasswordAuthenticationToken(adminDto.name, adminDto.password)
        try {
            authenticationManager.authenticate(token)
        } catch (e: AuthenticationException) {
            e.printStackTrace()
        }

        val accessToken = jwtTokenManager.getToken(aDto, 10 * 60)
        val refreshToken = jwtTokenManager.getToken(aDto, 24 * 60 * 60)

        return LoginDto(admin.name, accessToken, refreshToken, "ROLE_ADMIN")
    }

    fun logout(name: String): LoginDto {
        val admin: Admin = adminRepository.findAdminByName(name)
        return LoginDto(admin.name, "", "", "")
    }
}