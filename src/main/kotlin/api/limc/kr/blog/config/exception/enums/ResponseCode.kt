package api.limc.kr.blog.config.exception.enums

import org.springframework.http.HttpStatus

interface ResponseCode {
    val status: HttpStatus
    val code:String
    val message:String
}