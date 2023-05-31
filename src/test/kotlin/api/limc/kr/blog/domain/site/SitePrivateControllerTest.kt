package api.limc.kr.blog.domain.site

import api.limc.kr.blog.domain.admin.AdminController
import api.limc.kr.blog.domain.admin.dto.AdminDto
import api.limc.kr.blog.domain.site.dto.SiteDto
import api.limc.kr.blog.shared.LimcTest
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.http.MediaType
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.MvcResult
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders

@AutoConfigureMockMvc
@LimcTest
class SitePrivateControllerTest {
    /** env setting **/
    @Autowired lateinit var adminController: AdminController
    @Autowired lateinit var controller: SitePrivateController
    @Autowired lateinit var mockMvc: MockMvc
    val objectMapper: ObjectMapper = Jackson2ObjectMapperBuilder.json()
            .featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .modules(JavaTimeModule())
            .build()

    val name = "DEV"
    var accessToken: String? = null

    @BeforeAll
    fun login() {
        val adminDto = AdminDto(null, "id", "pw")
        adminController.save(adminDto)
        val response = adminController.login(adminDto)
        accessToken = "Bearer " + response.body?.accessToken
    }

    @Test
    fun testsave() {
        val url = "/private/site"

        val siteDto = SiteDto(name)
        val jsonAsString: String = objectMapper.writeValueAsString(siteDto)

        val request = MockMvcRequestBuilders.post(url)
            .content(jsonAsString)
            .contentType(MediaType.APPLICATION_JSON)
            .header("Authorization", accessToken)

        val callback = {r: MvcResult -> run {
            Assertions.assertTrue(r.response.errorMessage.isNullOrBlank())
            Assertions.assertTrue(r.response.contentAsString.isNotBlank())
        }}

        mockMvc
            .perform(request)
            .andExpect(callback)
    }

    @Test
    @Order(0)
    fun save() {
        val siteDto = SiteDto(name)

        val site = controller.save(siteDto)

        Assertions.assertNotNull(site)
    }

    @Test
    @Order(1)
    fun findAll() {
        val sites = controller.findAll()

        Assertions.assertTrue(sites.isNotEmpty())
    }
}