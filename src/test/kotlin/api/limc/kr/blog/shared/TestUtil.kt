package api.limc.kr.blog.shared

import api.limc.kr.blog.domain.admin.AdminController
import api.limc.kr.blog.domain.admin.dto.AdminDto
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import org.springframework.http.MediaType
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder
import org.springframework.test.web.servlet.MvcResult
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders

object TestUtil {
    private val objectMapper: ObjectMapper = Jackson2ObjectMapperBuilder.json()
        .featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
        .modules(JavaTimeModule())
        .build()
    fun login(ac: AdminController): String {
        val adminDto = AdminDto(null, "id", "pw")
        ac.save(adminDto)
        val response = ac.login(adminDto)
        return "Bearer " + response.body?.accessToken
    }

    fun <T> getMvcResult2Obj(result: MvcResult, to: Class<T>): T? {
        val r = result.response

        if (r.status != 200 || r.contentAsString.isBlank()) return null

        return objectMapper.readValue(result.response.contentAsString, to)
    }

    fun get(url:String, token: String?): MockHttpServletRequestBuilder {

        var request= MockMvcRequestBuilders.get(url)
            .contentType(MediaType.APPLICATION_JSON)

        if(token != null) request = request.header("Authorization", token)

        return request
    }

    fun post(url:String, body: Any?, token: String?): MockHttpServletRequestBuilder
        = MockMvcRequestBuilders.post(url)
            .content(objectMapper.writeValueAsString(body))
            .contentType(MediaType.APPLICATION_JSON)
            .header("Authorization", token)

    fun patch(url:String, body: Any?, token: String?): MockHttpServletRequestBuilder
       = MockMvcRequestBuilders.patch(url)
           .content(objectMapper.writeValueAsString(body))
           .contentType(MediaType.APPLICATION_JSON)
           .header("Authorization", token)

    fun delete(url:String, token: String?): MockHttpServletRequestBuilder
        = MockMvcRequestBuilders.delete(url)
            .contentType(MediaType.APPLICATION_JSON)
            .header("Authorization", token)
}
