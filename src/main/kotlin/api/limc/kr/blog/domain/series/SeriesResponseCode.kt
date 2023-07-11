package api.limc.kr.blog.domain.series

import api.limc.kr.blog.config.exception.enums.ResponseCode
import org.springframework.http.HttpStatus

enum class SeriesResponseCode(override val status: HttpStatus, override val code: String, override val message: String): ResponseCode {
    SERIES_NOT_FOUND(HttpStatus.NOT_FOUND, code = "SERIES_NOT_FOUND", "존재하지 않는 시리즈입니다."),
    NO_CHANGES_FOUND(HttpStatus.BAD_REQUEST, code = "NO_CHANGES_FOUND", "변경된 내용이 존재하지 않습니다."),
    INVALID_FK_UPDATE_POST(HttpStatus.INTERNAL_SERVER_ERROR, code = "INVALID_FK_UPDATE_POST", "하위 포스트 FK 수정 중 오류가 발생했습니다."),
}