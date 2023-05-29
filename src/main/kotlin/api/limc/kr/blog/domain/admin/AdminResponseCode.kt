package api.limc.kr.blog.domain.admin

import api.limc.kr.blog.config.exception.enums.ResponseCode
import org.springframework.http.HttpStatus

enum class AdminResponseCode(override val status: HttpStatus, override val code: String, override val message: String): ResponseCode {
    INVALID_NAME_PARAMETER(HttpStatus.BAD_REQUEST, "INVALID_NAME_PARAMETER", "존재하지 않는 계정입니다."),
}