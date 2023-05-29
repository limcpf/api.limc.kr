package api.limc.kr.blog.domain.site

import api.limc.kr.blog.domain.site.dto.SiteDto
import api.limc.kr.blog.shared.LimcTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

@LimcTest
class SitePrivateControllerTest {
    @Autowired lateinit var controller: SitePrivateController

    val name = "DEV"
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