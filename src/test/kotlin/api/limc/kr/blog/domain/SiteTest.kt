package api.limc.kr.blog.domain

import api.limc.kr.blog.shared.LimcTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test

@LimcTest
class SiteTest {

    lateinit var site:Site

    @Test
    @Order(0)
    fun siteTest() {
        Assertions.assertTrue(false)
    }
}