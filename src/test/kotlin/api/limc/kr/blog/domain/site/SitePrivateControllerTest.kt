package api.limc.kr.blog.domain.site

import api.limc.kr.blog.domain.admin.AdminController
import api.limc.kr.blog.domain.site.dto.SiteDto
import api.limc.kr.blog.shared.LimcTest
import api.limc.kr.blog.shared.TestUtil
import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.MvcResult

@AutoConfigureMockMvc
@LimcTest
class SitePrivateControllerTest {
    /** env setting **/
    @Autowired lateinit var adminController: AdminController
    @Autowired lateinit var mockMvc: MockMvc

    val name = "DEV"
    var accessToken: String? = null

    @BeforeAll
    fun login() {
        accessToken = TestUtil.login(adminController)
    }

    @AfterAll
    fun init() {
        adminController.deleteAllForTest()
    }

    @Test
    @Order(0)
    fun save() {
        var result: SiteDto? = null
        val url = "/private/site"

        val siteDto = SiteDto(name)

        mockMvc
            .perform(TestUtil.post(url, siteDto, accessToken))
            .andExpect{ r: MvcResult -> result = TestUtil.getMvcResult2Obj(r, SiteDto::class.java)}

        Assertions.assertNotNull(result)
        Assertions.assertEquals(name, result?.name)
    }
    /** TODO : 추후 수정 */
    @Order(1)
    fun findAll() {
        var result: List<SiteDto>? = null
        val url = "/private/site"

        mockMvc
            .perform(TestUtil.get(url, accessToken))
            .andExpect{ r: MvcResult -> result = TestUtil.getMvcResult2Obj(r, ArrayList<SiteDto>()::class.java) }

        println(result)

        Assertions.assertNotNull(result)
        result?.let { Assertions.assertTrue(it.isNotEmpty()) }
    }
}