package api.limc.kr.blog.respository

import api.limc.kr.blog.domain.Site
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SiteRepository: JpaRepository<Site, String>