package kea.alog.project.common.exception;

import kea.alog.project.common.dto.ResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /*
     * @Valid 붙어있는 곳 (@RequestBody..) 오류
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ResponseDto> handleMethodArgumentNotValidException(
        MethodArgumentNotValidException e
    ) {
        log.error(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                             .body(ResponseDto.fail(400, "INVALID_INPUT"));
    }

    /*
     * @RequestParam(required=true)에서 값 누락
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    protected ResponseEntity<ResponseDto> handleMissingServletRequestParameterException(
        MissingServletRequestParameterException e) {
        log.error(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                             .body(ResponseDto.fail(400, "PROPERTY_REQUIRED"));
    }

    /*
     * enum type 불일치
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected ResponseEntity<ResponseDto> handleMethodArgumentTypeMismatchException(
        MethodArgumentTypeMismatchException e
    ) {
        log.error(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                             .body(ResponseDto.fail(400, "INVALID_PROPERTY"));
    }

    @ExceptionHandler(BusinessException.class)
    protected ResponseEntity<ResponseDto> handleBusinessException(
        BusinessException e
    ) {
        log.error(e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
            ResponseDto.fail(e.getCode(), e.getMessage())
        );
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ResponseDto> handleException(
        Exception e
    ) {
        log.error(e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
            ResponseDto.fail(500, "INTERNAL_SERVER_ERR")
        );
    }
}

