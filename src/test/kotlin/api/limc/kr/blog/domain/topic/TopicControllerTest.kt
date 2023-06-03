package api.limc.kr.blog.domain.topic

import api.limc.kr.blog.domain.admin.AdminController
import api.limc.kr.blog.domain.site.SitePrivateController
import api.limc.kr.blog.domain.site.dto.SiteDto
import api.limc.kr.blog.domain.topic.dto.TopicDto
import api.limc.kr.blog.shared.LimcTest
import api.limc.kr.blog.shared.TestUtil
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.MvcResult

@LimcTest
@AutoConfigureMockMvc
class TopicControllerTest {
    @Autowired lateinit var sitePrivateController: SitePrivateController
    @Autowired lateinit var adminController: AdminController
    @Autowired lateinit var mockMvc: MockMvc

    lateinit var site: SiteDto
    var topicDto: TopicDto? = null
    var accessToken: String? = null
    val basePrivateUrl = "/private/topic"
    val basePublicUrl = "/public/topic"
    @BeforeAll
    fun setup() {
        val siteDto = SiteDto("dev")
        site = sitePrivateController.save(siteDto)
        topicDto = TopicDto(site, "TestTopic")
        accessToken = TestUtil.login(adminController)
    }

    @Test
    @Order(0)
    fun saveTest() {
        val url = basePrivateUrl
        val dto = topicDto
        var result: TopicDto? = null

        mockMvc
            .perform(TestUtil.post(url, dto, accessToken))
            .andExpect{ r: MvcResult -> result = TestUtil.getMvcResult2Obj(r, TopicDto::class.java)}

        topicDto = result

        Assertions.assertNotNull(result)
        Assertions.assertEquals(result?.name, topicDto?.name)
    }

    @Test
    @Order(1)
    fun findAllBySite() {
        val siteName = "DEV"
        val url = "$basePublicUrl?site=$siteName&page=0"
        var result : Int? = null

        mockMvc
            .perform(TestUtil.get(url, ""))
            .andExpect { r:MvcResult -> result = r.response.status }

        Assertions.assertNotNull(result)
        Assertions.assertEquals(200, result)
    }

    @Test
    @Order(1)
    fun findById() {
        val id = topicDto?.id
        val url = "$basePublicUrl/$id"
        var result: TopicDto? = null

        mockMvc
            .perform(TestUtil.get(url, ""))
            .andExpect { r:MvcResult -> result = TestUtil.getMvcResult2Obj(r, TopicDto::class.java)}

        Assertions.assertNotNull(result)
        Assertions.assertEquals(topicDto?.id, result?.id)
    }

    @Test
    @Order(2)
    fun update() {
        val updatedName = "Updated"
        val dto = TopicDto(topicDto?.id, site, updatedName)
        val url = "$basePrivateUrl"
        var result: TopicDto? = null

        mockMvc
            .perform(TestUtil.patch(url, dto, accessToken))
            .andExpect { r:MvcResult -> result = TestUtil.getMvcResult2Obj(r, TopicDto::class.java) }

        Assertions.assertNotNull(result)
        Assertions.assertEquals(updatedName, result?.name)
    }

    @Test
    @Order(3)
    fun delete() {
        val id = topicDto?.id
        val url = "$basePrivateUrl/$id"
        var status: Int? = null

        mockMvc
            .perform(TestUtil.delete(url, accessToken))
            .andExpect { r:MvcResult -> status = r.response.status }

        Assertions.assertNotNull(status)
        Assertions.assertEquals(200, status)
    }
}