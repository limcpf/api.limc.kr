package api.limc.kr.blog.domain.admin

import api.limc.kr.blog.config.exception.LimcException
import api.limc.kr.blog.config.exception.enums.LimcResponseCode
import api.limc.kr.blog.domain.admin.dto.AdminDto
import org.springframework.stereotype.Service

@Service
class AdminService(private val repository: AdminRepository) {
    fun save(adminDto: AdminDto): AdminDto {
        if(repository.count() > 0) throw LimcException(LimcResponseCode.ACCOUNT_CREATION_LIMIT)

        var admin = Admin(adminDto).encodePassword()

        admin = repository.save(admin)

        return admin.toDto()
    }

    fun deleteAllForTest() {
        repository.deleteAll()
    }
}
