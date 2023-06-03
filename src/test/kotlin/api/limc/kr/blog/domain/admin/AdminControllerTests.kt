package api.limc.kr.blog.domain.admin

import api.limc.kr.blog.config.exception.LimcException
import api.limc.kr.blog.config.exception.enums.LimcResponseCode
import api.limc.kr.blog.domain.admin.dto.AdminDto
import api.limc.kr.blog.shared.LimcTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

@LimcTest
class AdminControllerTests {
    @Autowired private lateinit var adminController: AdminController
    val password = "testCode"

    @Test
    @Order(0)
    fun saveTest() {
        val reqDto = AdminDto(null, "test", password)

        val resDto:AdminDto = adminController.save(reqDto)

        Assertions.assertNotNull(resDto.id)
        Assertions.assertNotEquals(resDto.password, reqDto.password)
    }

    @Test
    @Order(1)
    fun saveFailTest() {
        val reqDto = AdminDto(null, "aaa", password)

        Assertions.assertThrowsExactly(
            LimcException(LimcResponseCode.ACCOUNT_CREATION_LIMIT)::class.java
        ) {
            adminController.save(reqDto)
        }
    }

    @Test
    @Order(1)
    fun loginTest() {
        val reqDto = AdminDto(null, "test", password)

        val loginDto = adminController.login(reqDto)

        println(loginDto)

        Assertions.assertNotNull(loginDto)
        loginDto.body?.accessToken?.isNotBlank()?.let { Assertions.assertTrue(it) }
    }

    @Test
    @Order(1)
    fun logoutTest() {
        val reqDto = AdminDto(null, "test", "")
        val loginDto = adminController.logout(reqDto)

        Assertions.assertNotNull(loginDto)
        loginDto.body?.accessToken.isNullOrBlank().let { Assertions.assertTrue(it) }
    }

    @Test
    @Order(2)
    fun loginPwFailTest() {
        val reqDto = AdminDto(null, "test", "fail")

        Assertions.assertThrowsExactly(
            LimcException(LimcResponseCode.INVALID_PASSWORD_PARAMETER)::class.java
        ) {
            adminController.login(reqDto)
        }
    }
    @Test
    @Order(2)
    fun loginIdFailTest() {
        val reqDto = AdminDto(null, "test2", "fail")

        Assertions.assertThrowsExactly(
            LimcException(LimcResponseCode.INVALID_ID_PARAMETER)::class.java
        ) {
            adminController.login(reqDto)
        }
    }
}