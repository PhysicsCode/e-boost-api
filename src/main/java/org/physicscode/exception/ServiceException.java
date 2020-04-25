package org.physicscode.exception;

import lombok.Getter;
import org.springframework.core.NestedRuntimeException;

public class ServiceException extends NestedRuntimeException {

    @Getter
    private final ErrorCode errorCode;

    public ServiceException(String msg) {
        super(msg);
        this.errorCode = null;
    }

    public ServiceException(ErrorCode errorCode) {
        super(errorCode.getMsg());
        this.errorCode = errorCode;

    }
}
