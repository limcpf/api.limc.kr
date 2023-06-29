package api.limc.kr.blog.domain.topic

import api.limc.kr.blog.domain.site.Site
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TopicRepository: JpaRepository<Topic, Long> {
    fun findAllBySite(site:Site, page:Pageable): Page<Topic>
    fun findAllBySite(site:Site): ArrayList<Topic>
}