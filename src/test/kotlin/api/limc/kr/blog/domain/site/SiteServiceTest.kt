package api.limc.kr.blog.domain.site

import api.limc.kr.blog.config.exception.LimcException
import api.limc.kr.blog.config.exception.enums.LimcResponseCode
import api.limc.kr.blog.domain.site.dto.SiteDto
import api.limc.kr.blog.shared.LimcTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

@LimcTest
class SiteServiceTest(@Autowired val service: SiteService) {
    var name = "DEV"
    @BeforeAll
    fun setup() {
        service.save(SiteDto(name))
    }

    @Test
    fun findByIdTest() {
        val name = this.name

        val site = service.findByName(name)

        Assertions.assertNotNull(site)
    }

    @Test
    fun findByIdFailTest() {
        Assertions.assertThrowsExactly(
            LimcException(LimcResponseCode.NOT_FOUND)::class.java
        ) {
            service.findByName("")
        }
    }
}