package api.limc.kr.blog.config.exception.enums

import org.springframework.http.HttpStatus

enum class LimcResponseCode(override val status: HttpStatus, override val code: String, override val message: String): ResponseCode {
    NOT_FOUND(HttpStatus.NOT_FOUND, "NOT_FOUND", "존재하지 않습니다."),
    INVALID_NAME_PARAMETER(HttpStatus.BAD_REQUEST, "INVALID_NAME_PARAMETER", "올바르지 않은 이름입니다."),
    INVALID_ID_PARAMETER(HttpStatus.BAD_REQUEST, "INVALID_ID_PARAMETER", "올바르지 않은 id 입니다."),
    INVALID_PASSWORD_PARAMETER(HttpStatus.BAD_REQUEST, "INVALID_PASSWORD_PARAMETER", "올바르지 않은 pw 입니다."),
    INVALID_TITLE_PARAMETER(HttpStatus.BAD_REQUEST, "INVALID_TITLE_PARAMETER", "올바르지 않은 제목입니다."),
    ACCOUNT_CREATION_LIMIT(HttpStatus.FORBIDDEN, "ACCOUNT_CREATION_LIMIT", "계정은 현재 생성할 수 없는 상태입니다."),
    INVALID_TOKEN(HttpStatus.FORBIDDEN, "INVALID_TOKEN", "토큰이 존재하지 않거나 올바른 토큰이 아닙니다."),
}