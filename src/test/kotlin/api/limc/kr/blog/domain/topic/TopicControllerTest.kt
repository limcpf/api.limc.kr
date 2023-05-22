package api.limc.kr.blog.domain.topic

import api.limc.kr.blog.domain.site.SiteController
import api.limc.kr.blog.domain.site.dto.SiteDto
import api.limc.kr.blog.domain.topic.dto.TopicDto
import api.limc.kr.blog.shared.LimcTest
import api.limc.kr.blog.shared.enums.Pages
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest

@LimcTest
class TopicControllerTest {
    @Autowired lateinit var controller: TopicController
    @Autowired lateinit var siteController: SiteController

    lateinit var site: SiteDto
    lateinit var topic: TopicDto
    @BeforeAll
    fun setup() {
        val siteDto = SiteDto("dev")
        site = siteController.save(siteDto)
    }

    @Test
    @Order(0)
    fun saveTest() {
        val topicDto = TopicDto(site, "test Topic")
        topic = controller.save(topicDto)

        Assertions.assertNotNull(topic)
    }

    @Test
    @Order(1)
    fun findAllBySite() {
        val siteName = "DEV"

        /** topic 10개 생성 */
        for(i: Int in 1..10) {
            val topicDto = TopicDto(site, "test Topic${i}")
            controller.save(topicDto)
        }

        val topics = controller.findAll(siteName, PageRequest.of(0, Pages.TOPIC_PAGE_NUM.value))

        Assertions.assertTrue(!topics.isEmpty)
        Assertions.assertTrue(topics.size == 10) // topics 의 사이즈가 10 이어야함
    }

    @Test
    @Order(1)
    fun findById() {
        val id = this.topic.id ?: throw NoSuchElementException("topicDto 미생성")

        val topicDto = controller.findById(id)

        Assertions.assertNotNull(topicDto)
    }
}