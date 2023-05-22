package api.limc.kr.blog

import api.limc.kr.blog.config.exception.LimcException
import api.limc.kr.blog.config.exception.LimcExceptionAdvice
import api.limc.kr.blog.config.exception.enums.LimcResponseCode
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class BlogApplicationTests {

	@Test
	fun contextLoads() { println("wow") }


	@Test
	fun customExceptionTest() {
		try {
			throw LimcException(LimcResponseCode.INVALID_NAME_PARAMETER)
		} catch (e: LimcException) {

			val res = LimcExceptionAdvice().handleBadRequestException(e)

			Assertions.assertNotNull(res)

			Assertions.assertEquals(LimcResponseCode.INVALID_NAME_PARAMETER.code, e.code)
			Assertions.assertEquals(LimcResponseCode.INVALID_NAME_PARAMETER.message, e.message)
			Assertions.assertEquals(LimcResponseCode.INVALID_NAME_PARAMETER.status, e.status)
		}
	}
}
