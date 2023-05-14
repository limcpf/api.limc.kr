package api.limc.kr.blog.config.exception.enums

import org.springframework.http.HttpStatus

enum class SiteResponseCode(override val status: HttpStatus, override val code: String, override val message: String) : ResponseCode {
    INVALID_NAME_PARAMETER(HttpStatus.BAD_REQUEST, "INVALID_NAME_PARAMETER", "올바르지 않은 이름입니다.")
}