package api.limc.kr.blog.config.exception.enums

import org.springframework.http.HttpStatus

enum class LimcResponseCode(override val status: HttpStatus, override val code: String, override val message: String): ResponseCode {
    NOT_FOUND(HttpStatus.NOT_FOUND, "NOT_FOUND", "존재하지 않습니다."),
    INVALID_NAME_PARAMETER(HttpStatus.BAD_REQUEST, "INVALID_NAME_PARAMETER", "올바르지 않은 이름입니다."),
    INVALID_ID_PARAMETER(HttpStatus.BAD_REQUEST, "INVALID_ID_PARAMETER", "올바르지 않은 id 입니다."),
    INVALID_TITLE_PARAMETER(HttpStatus.BAD_REQUEST, "INVALID_TITLE_PARAMETER", "올바르지 않은 제목입니다."),
}