package api.limc.kr.blog.domain.post

import api.limc.kr.blog.domain.series.Series
import api.limc.kr.blog.domain.site.Site
import api.limc.kr.blog.domain.topic.Topic
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface PostRepository: JpaRepository<Post, Long> {
    fun findAllBySite(site: Site, page:Pageable): Page<Post>
    fun findAllByTopic(topic: Topic, page:Pageable): Page<Post>
    fun findAllBySeries(series: Series, page: Pageable): Page<Post>

    fun countBySite(site: Site): Int
    fun countByTopic(topic: Topic): Int
    fun countBySeries(series: Series): Int
    @Query(
        nativeQuery = true,
        value = """
            UPDATE 
                POST
            SET
                SITE_NAME = :siteName,
                TOPIC_ID = :topicId
            WHERE
                SERIES_ID = :seriesId
        """
    )
    fun updateFkForSeries(
        @Param("seriesId") seriesId:Long,
        @Param("siteName") siteName: String,
        @Param("topicId") topicId: Long
    ): Int
}