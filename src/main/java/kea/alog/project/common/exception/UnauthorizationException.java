package kea.alog.project.common.exception;

public class UnauthorizationException extends BusinessException {

    public UnauthorizationException(int code, String message) {
        super(code, message);
    }
}
