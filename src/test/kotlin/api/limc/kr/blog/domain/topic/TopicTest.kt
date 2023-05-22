package api.limc.kr.blog.domain.topic

import api.limc.kr.blog.config.exception.LimcException
import api.limc.kr.blog.config.exception.enums.LimcResponseCode
import api.limc.kr.blog.domain.site.Site
import api.limc.kr.blog.domain.site.SiteRepository
import api.limc.kr.blog.shared.LimcTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

@LimcTest
class TopicTest {
    @Autowired lateinit var siteRepository: SiteRepository
    lateinit var site:Site
    @BeforeAll
    fun setup() {
        site = siteRepository.save(Site("DEV"))
    }

    @Test
    @Order(0)
    fun setNameTest() {
        val topic = Topic(1, site, "test Topic")

        Assertions.assertNotNull(topic)
    }

    @Test
    @Order(0)
    fun setNameFailTest() {
        val name = ""
        val name2 = " "

        Assertions.assertThrowsExactly(
            LimcException(LimcResponseCode.INVALID_NAME_PARAMETER)::class.java
        ) {
            Topic(1, site, name)
        }

        Assertions.assertThrowsExactly(
            LimcException(LimcResponseCode.INVALID_NAME_PARAMETER)::class.java
        ) {
            Topic(1, site, name2)
        }
        Assertions.assertThrowsExactly(
            LimcException(LimcResponseCode.INVALID_NAME_PARAMETER)::class.java
        ) {
            val topic = Topic(1, site, "TestTopic")
            topic.name = ""
        }
    }
}
