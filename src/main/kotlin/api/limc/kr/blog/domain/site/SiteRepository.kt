package api.limc.kr.blog.domain.site

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SiteRepository: JpaRepository<Site, String> {
    fun countByName(name: String): Int
}