package api.limc.kr.blog.domain.topic

import api.limc.kr.blog.config.exception.enums.ResponseCode
import org.springframework.http.HttpStatus

enum class TopicResponseCode(override val status: HttpStatus, override val code: String, override val message: String): ResponseCode {
    TOPIC_SITE_NOT_FOUND(HttpStatus.NOT_FOUND, code = "TOPIC_SITE_NOT_FOUND", "토픽 생성 시 존재하는 사이트명이 필요합니다."),
    NO_CHANGES_FOUND(HttpStatus.BAD_REQUEST, code = "NO_CHANGES_FOUND", "변경된 토픽 내용이 존재하지 않습니다."),
}