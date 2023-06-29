package api.limc.kr.blog.domain.site

import api.limc.kr.blog.config.exception.LimcException
import api.limc.kr.blog.config.exception.enums.LimcResponseCode
import api.limc.kr.blog.shared.LimcTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

@LimcTest
class SiteTest {
    @Autowired lateinit var repository: SiteRepository
    var name = "DEV"


    @Test
    fun toStringTest() {
        Assertions.assertNotNull(Site(name).toString())
    }

    @Test
    fun validIdTest() {
        val isValidSite = Site(name)

        Assertions.assertNotNull(isValidSite)

        Assertions.assertThrowsExactly(
            LimcException(LimcResponseCode.INVALID_NAME_PARAMETER)::class.java
        ) {
            Site("")
        }

        Assertions.assertThrowsExactly(
            LimcException(LimcResponseCode.INVALID_NAME_PARAMETER)::class.java
        ) {
            Site(null)
        }

        Assertions.assertThrowsExactly(
            LimcException(LimcResponseCode.INVALID_NAME_PARAMETER)::class.java
        ) {
            val site = Site("LIM")
            site.name = ""
        }
    }

    @Test
    @Order(0)
    fun saveTest() {
        val name = this.name

        val site = repository.save(Site(name))

        Assertions.assertNotNull(site)
    }

    @Test
    @Order(0)
    fun saveExceptionTest() {
        val name: String? = null

        Assertions.assertThrowsExactly(
            LimcException(LimcResponseCode.INVALID_NAME_PARAMETER)::class.java
        ) {
            repository.save(Site(name))
        }
    }

    @Test
    @Order(1)
    fun findAllTest() {
        val sites = repository.findAll()

        Assertions.assertTrue(sites.isNotEmpty())
    }

    @Test
    @Order(1)
    fun findByIdTest() {
        val name = this.name

        val site = repository.findById(name).get()

        Assertions.assertNotNull(site)
        Assertions.assertEquals(this.name, site.name)
    }

    //TODO: 테스트 수정
    fun deleteTest() {

        val name = this.name

        repository.delete(Site(name))

        /** 조회가 안되어야 정상 */
        Assertions.assertThrowsExactly(
            NoSuchElementException::class.java
        ) {
            repository.findById(name).get()
        }
    }

}