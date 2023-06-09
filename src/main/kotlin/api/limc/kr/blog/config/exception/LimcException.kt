package api.limc.kr.blog.config.exception

import api.limc.kr.blog.config.exception.enums.ResponseCode
import org.springframework.http.HttpStatus

class LimcException(responseCode: ResponseCode) : RuntimeException() {

    val status: HttpStatus
    val code:String
    override val message:String

    init {
        status = responseCode.status
        code = responseCode.code
        message = responseCode.message
    }
}