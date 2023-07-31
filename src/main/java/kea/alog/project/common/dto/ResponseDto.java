package kea.alog.project.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ResponseDto<T> {

    private final int code;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    public static ResponseDto success(int code) {
        return ResponseDto.builder()
                          .code(code)
                          .build();
    }

    public static <T> ResponseDto<T> success(int code, T data) {
        return ResponseDto.<T>builder()
                          .code(code)
                          .data(data)
                          .build();
    }

    public static ResponseDto fail(int code, String message) {
        return ResponseDto.builder()
                          .code(code)
                          .message(message)
                          .build();
    }

    @Builder
    public ResponseDto(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
}
