package api.limc.kr.blog.domain.util

import api.limc.kr.blog.domain.post.PostService
import api.limc.kr.blog.domain.post.dto.PostDto
import api.limc.kr.blog.domain.series.SeriesService
import api.limc.kr.blog.domain.series.dto.SeriesDto
import api.limc.kr.blog.domain.site.Site
import api.limc.kr.blog.domain.site.SiteService
import api.limc.kr.blog.domain.site.dto.SiteDto
import api.limc.kr.blog.domain.topic.TopicService
import api.limc.kr.blog.domain.topic.dto.TopicDto
import api.limc.kr.blog.shared.LimcTest
import api.limc.kr.blog.shared.enums.Domain
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

@LimcTest
class BlogFacadeTest() {
    @Autowired lateinit var blogFacade: BlogFacade
    @Autowired lateinit var siteService: SiteService
    @Autowired lateinit var topicService: TopicService
    @Autowired lateinit var seriesService: SeriesService
    @Autowired lateinit var postService: PostService

    @BeforeAll
    fun setup() {
        val siteDto = SiteDto("DEV")
        val site: SiteDto = siteService.save(siteDto)
        val topicDto = TopicDto(siteDto, "Test Topic")
        val topic: TopicDto = topicService.save(topicDto)
        val seriesDto = SeriesDto(null, topic, "dd")
        val series = seriesService.save(seriesDto)
        val postDto = PostDto(null, site, topic, series, "title", "body")
        postService.save(postDto)
    }

    @Test
    fun test() {
        val site:Site = siteService.getSite("DEV")
        val IntMap = blogFacade.getCntObjInSite(site)

        Assertions.assertEquals(1, IntMap[Domain.TOPIC])
        Assertions.assertEquals(1, IntMap[Domain.SERIES])
        Assertions.assertEquals(1, IntMap[Domain.POST])
    }

}