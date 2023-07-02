package api.limc.kr.blog.domain.site

import api.limc.kr.blog.config.exception.enums.ResponseCode
import org.springframework.http.HttpStatus

enum class SiteResponseCode(override val status: HttpStatus, override val code: String, override val message: String): ResponseCode {
    DUPLICATE_NAME(HttpStatus.BAD_REQUEST, code = "DUPLICATE_NAME", "중복된 이름입니다."),
}