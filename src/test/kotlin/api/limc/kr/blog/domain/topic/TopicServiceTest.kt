package api.limc.kr.blog.domain.topic

import api.limc.kr.blog.domain.site.SiteService
import api.limc.kr.blog.domain.site.dto.SiteDto
import api.limc.kr.blog.domain.topic.dto.TopicDto
import api.limc.kr.blog.shared.LimcTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

@LimcTest
class TopicServiceTest {
    @Autowired lateinit var service: TopicService
    @Autowired lateinit var siteService: SiteService

    val site1: String = "DEV"
    val site2: String = "LIFE"
    lateinit var topic: TopicDto
    @BeforeAll
    fun setup() {
        val siteDto = SiteDto(site1)
        siteService.save(siteDto)
        siteService.save(SiteDto(site2))
        val topicDto = TopicDto(siteDto, "test Topic")
        topic = service.save(topicDto)
    }

    @Test
    fun updateSiteTest() {
        var topicDto = TopicDto(topic.id, SiteDto(site2), "update Topic")

        topicDto = service.update(topicDto)

        Assertions.assertEquals(topicDto.site.name, site2)
        Assertions.assertEquals(topicDto.name, "update Topic")
    }
}