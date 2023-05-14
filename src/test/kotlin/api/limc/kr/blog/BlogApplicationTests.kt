package api.limc.kr.blog

import api.limc.kr.blog.config.exception.LimcException
import api.limc.kr.blog.config.exception.LimcExceptionAdvice
import api.limc.kr.blog.domain.site.SiteResponseCode
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class BlogApplicationTests {

	@Test
	fun contextLoads() {
	}

	@Test
	fun customExceptionTest() {
		try {
			throw LimcException(SiteResponseCode.INVALID_NAME_PARAMETER)
		} catch (e: LimcException) {

			val res = LimcExceptionAdvice().handleBadRequestException(e)

			Assertions.assertNotNull(res)

			Assertions.assertEquals(SiteResponseCode.INVALID_NAME_PARAMETER.code, e.code)
			Assertions.assertEquals(SiteResponseCode.INVALID_NAME_PARAMETER.message, e.message)
			Assertions.assertEquals(SiteResponseCode.INVALID_NAME_PARAMETER.status, e.status)
		}
	}
}
