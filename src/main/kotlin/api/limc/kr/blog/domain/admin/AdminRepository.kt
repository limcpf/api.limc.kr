package api.limc.kr.blog.domain.admin

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AdminRepository: JpaRepository<Admin, Long> {
    fun findAdminByName(name: String): Admin
}