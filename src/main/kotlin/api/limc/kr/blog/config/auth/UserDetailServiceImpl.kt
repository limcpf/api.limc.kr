package api.limc.kr.blog.config.auth

import api.limc.kr.blog.config.exception.LimcException
import api.limc.kr.blog.config.exception.enums.LimcResponseCode
import api.limc.kr.blog.domain.admin.Admin
import api.limc.kr.blog.domain.admin.AdminRepository
import api.limc.kr.blog.domain.admin.AdminResponseCode
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserDetailServiceImpl(private val repository: AdminRepository): UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        lateinit var authenticatedLoginId: String
        lateinit var authenticatedLoginPassword: String

        lateinit var grantedAuthority: SimpleGrantedAuthority

        if(username.isNotBlank()) {
            lateinit var admin:Admin
            try {
                admin = repository.findAdminByName(username)
            }catch (e: EmptyResultDataAccessException) {
                e.printStackTrace()
                throw LimcException(LimcResponseCode.INVALID_ID_PARAMETER)
            }
            authenticatedLoginId = admin.name
            authenticatedLoginPassword = admin.password
            grantedAuthority = SimpleGrantedAuthority("ROLE_ADMIN")

            return User(authenticatedLoginId, authenticatedLoginPassword, Collections.singleton(grantedAuthority))
        } else throw LimcException(AdminResponseCode.INVALID_NAME_PARAMETER)
    }
}