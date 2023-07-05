package kea.alog.project.common.exception;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {

    private String code;

    public BusinessException(String code) {
        super(code);
        this.code = code;
    }
}
