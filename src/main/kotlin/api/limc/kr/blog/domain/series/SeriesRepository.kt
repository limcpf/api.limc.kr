package api.limc.kr.blog.domain.series

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SeriesRepository:JpaRepository<Series, Long> {
    fun findAllByTopic(id: Long, page:Pageable): Page<Series>
}