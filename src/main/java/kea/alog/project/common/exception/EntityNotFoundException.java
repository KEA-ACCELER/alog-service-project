package kea.alog.project.common.exception;

public class EntityNotFoundException extends BusinessException {

    public EntityNotFoundException(int code, String message) {
        super(code, message);
    }
}
