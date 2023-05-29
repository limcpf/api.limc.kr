package api.limc.kr.blog.domain.post

import api.limc.kr.blog.config.exception.enums.ResponseCode
import org.springframework.http.HttpStatus

enum class PostResponseCode(override val status: HttpStatus, override val code: String, override val message: String): ResponseCode {
    SERIES_NOT_FOUND(HttpStatus.NOT_FOUND, code = "SERIES_NOT_FOUND", "존재하지 않는 시리즈입니다."),
    TOPIC_NOT_FOUND(HttpStatus.NOT_FOUND, code = "TOPIC_NOT_FOUND", "존재하지 않는 주제입니다."),
    NO_CHANGES_FOUND(HttpStatus.BAD_REQUEST, code = "NO_CHANGES_FOUND", "변경된 내용이 존재하지 않습니다."),
}