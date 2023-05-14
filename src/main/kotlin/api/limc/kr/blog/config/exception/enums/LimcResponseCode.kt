package api.limc.kr.blog.config.exception.enums

import org.springframework.http.HttpStatus

enum class LimcResponseCode(override val status: HttpStatus, override val code: String, override val message: String): ResponseCode {
    NOT_FOUND(HttpStatus.NOT_FOUND, "NOT_FOUND", "존재하지 않습니다."),
}