package api.limc.kr.blog.config.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.time.LocalDateTime

@RestControllerAdvice
class LimcExceptionAdvice {
    @ExceptionHandler(LimcException::class)
    fun handleBadRequestException(e: LimcException): ResponseEntity<Any> {
        val body:MutableMap<String,Any> = LinkedHashMap()
        body["errorCode"] = e.code
        body["message"] = e.message
        body["timestamp"] = LocalDateTime.now().toString()

        return ResponseEntity(body, HttpStatus.valueOf(e.status.value()))
    }
}